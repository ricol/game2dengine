/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.scene;

import au.com.rmit.Game2dEngine.physics.collision.PhysicsCollisionProcess;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class Scene extends JPanel
{

    public static int MIN_LAYER = 0;
    public static int MAX_LAYER = 9;
    public boolean bShowMemoryUsage = true;
    public BufferedImage theImageBackground;
    private boolean bPaused;

    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private Color theBackgroundColor = new Color(red, green, blue);
    private boolean bEnableCollisionDetect = false;
    static long DELAY = 5;
    static long LEFT_TEXT = 30;
    static long TOP_TEXT = 45;
    static long GAP_TEXT = 20;
    long lastTime = System.currentTimeMillis();
    long lastFPSTime = System.currentTimeMillis();
    long fps = 0;
    float timeEllapsed = 0;
    long actionCount = 0;
    long FPS_INTERVAL = 500;
    long FPS = 100;
    String strMemoryUsage = "";

    protected Random theRandom = new Random();

    HashMap<Integer, Layer> layers = new HashMap();
    ArrayList<Sprite> allNodes = new ArrayList();
    ArrayList<Sprite> allInLoop = new ArrayList();

    Timer theTimer = new Timer(10, new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            timeEllapsed += 0.01;
        }
    });

    Timer theTimerForMemory = new Timer(500, new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            collectMemoryInfo();
        }
    });

    BufferedImage theImage;
    Graphics2D theGraphics2D;

    public Scene()
    {
        collectMemoryInfo();

        theTimer.start();
        theTimerForMemory.start();

        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent evt)
            {
                if (theImage == null)
                {
                    return;
                }

                theImage = null;
                theGraphics2D = null;

                theImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                theGraphics2D = theImage.createGraphics();
            }
        });
    }

    public void start()
    {
        if (getWidth() > 0 && getHeight() > 0)
        {
            if (theImage == null)
            {
                theImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                theGraphics2D = theImage.createGraphics();
            }

            bPaused = false;
        }
    }

    public void pause()
    {
        bPaused = !bPaused;
    }

    public void stop()
    {
        theImage = null;
        theGraphics2D = null;

        bPaused = true;
    }

    @Override
    public void paint(Graphics g)
    {
        Loop();

        if (theImage != null)
        {
            g.drawImage(theImage, 0, 0, null);
        }

        try
        {
            Thread.sleep(DELAY);
            repaint();
        } catch (InterruptedException ex)
        {

        }
    }

    private void addSprite(Sprite aSprite, int zOrder)
    {
        if (zOrder < MIN_LAYER || zOrder > MAX_LAYER)
        {
            return;
        }

        Layer theLayer = layers.get(zOrder);

        if (theLayer == null)
        {
            theLayer = new Layer(zOrder, this);
            theLayer.addSprite(aSprite);
            layers.put(zOrder, theLayer);
        } else
        {
            theLayer.addSprite(aSprite);
        }
    }

    public void addSprite(Sprite aSprite)
    {
        addSprite(aSprite, aSprite.getLayer());
    }

    private void Loop()
    {
        double currentTime = System.currentTimeMillis();
        
        long delta = (long) (currentTime - lastTime);
        if (delta < 1000.0 / FPS) { return; }

        allInLoop.clear();

        int totalLayers = 0;

        for (int i = MIN_LAYER; i <= MAX_LAYER; i++)
        {
            Layer aLayer = layers.get(i);
            if (aLayer == null)
                continue;

            totalLayers++;
            allInLoop.addAll(aLayer.AllObjects);
        }

        for (int i = MIN_LAYER; i <= MAX_LAYER; i++)
        {
            Layer aLayer = layers.get(i);
            if (aLayer == null)
                continue;

            //remove all dead sprites
            for (Sprite aSprite : aLayer.DeadObjects)
                aSprite.theScene = null;

            aLayer.AllObjects.removeAll(aLayer.DeadObjects);
            aLayer.DeadObjects.clear();

            actionCount = 0;
            //update sprites states
            for (Sprite aSprite : aLayer.AllObjects)
            {
                aSprite.willUpdateState();
                aSprite.updateState(currentTime);
                aSprite.didUpdateState();

                if (aSprite instanceof Sprite)
                    actionCount += ((Sprite) aSprite).getActionCount();

                if (!aSprite.isAlive())
                {
                    aLayer.DeadObjects.add(aSprite);
                    aSprite.onRemovedFromLayer(aLayer);
                }
            }

            aLayer.AllObjects.addAll(aLayer.NewObjects);
            aLayer.NewObjects.clear();
        }

        if (this.bEnableCollisionDetect)
        {
            collisionProcess();

            for (Sprite aSprite : allInLoop)
                aSprite.didCollisionProcess();
        }

        for (Sprite aSprite : allInLoop)
            aSprite.didFinishUpdateState();

        //update GUI
        if (theGraphics2D != null)
        {
            if (theImageBackground != null)
            {
                theGraphics2D.drawImage(theImageBackground, 0, 0, this.getWidth(), this.getHeight(), null);
            } else
            {
                theGraphics2D.setColor(theBackgroundColor);
                theGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
            }

            for (Sprite aSprite : allInLoop)
            {
                aSprite.willUpdateGUI();
                aSprite.updateGUI(theGraphics2D);
                aSprite.didUpdateGUI();
            }

            if (currentTime - lastFPSTime >= FPS_INTERVAL)
            {
                fps = (long) (1000.0 / delta);
                lastFPSTime = (long) currentTime;
            }
            
            lastTime = (long) currentTime;

            //draw fps
            String text = "FPS: " + fps;
            theGraphics2D.setColor(Color.RED);
            theGraphics2D.drawString(text, LEFT_TEXT, TOP_TEXT);

            //draw sprites count
            int totalNodes = allInLoop.size();

            text = "NODES: " + totalNodes;
            theGraphics2D.drawString(text, LEFT_TEXT, TOP_TEXT + GAP_TEXT);

            //draw total actions
            text = "ACTIONS: " + actionCount;
            theGraphics2D.drawString(text, LEFT_TEXT, TOP_TEXT + GAP_TEXT * 2);

            //draw total layers
            text = "LAYERS: " + totalLayers;
            theGraphics2D.drawString(text, LEFT_TEXT, TOP_TEXT + GAP_TEXT * 3);

            //draw current time ellapsed
            text = String.format("TIME: %.2f", timeEllapsed);
            theGraphics2D.drawString(text, LEFT_TEXT, this.getHeight() - TOP_TEXT);

            if (bShowMemoryUsage)
                theGraphics2D.drawString(strMemoryUsage, LEFT_TEXT, this.getHeight() - TOP_TEXT - GAP_TEXT);
        }

        allInLoop.clear();
    }

    public void setRed(int value)
    {
        if (value >= 0 && value <= 255)
        {
            this.red = value;
            this.theBackgroundColor = new Color(red, green, blue);
        }
    }

    public void setGreen(int value)
    {
        if (value >= 0 && value <= 255)
        {
            this.green = value;
            this.theBackgroundColor = new Color(red, green, blue);
        }
    }

    public void setBlue(int value)
    {
        if (value >= 0 && value <= 255)
        {
            this.blue = value;
            this.theBackgroundColor = new Color(red, green, blue);
        }
    }

    public int getRed()
    {
        return this.red;
    }

    public int getGreen()
    {
        return this.green;
    }

    public int getBlue()
    {
        return this.blue;
    }

    public boolean isScenePaused()
    {
        return this.bPaused;
    }

    public boolean collisionDetectEnabled()
    {
        return this.bEnableCollisionDetect;
    }

    public void enableCollisionDetect()
    {
        this.bEnableCollisionDetect = true;
    }

    public void disableCollisionDetect()
    {
        this.bEnableCollisionDetect = false;
    }

    public ArrayList<Sprite> getAllSprites()
    {
        ArrayList<Sprite> all = new ArrayList<>();
        for (int i = MIN_LAYER; i <= MAX_LAYER; i++)
        {
            Layer aLayer = layers.get(i);
            if (aLayer == null)
            {
                continue;
            }

            all.addAll(aLayer.AllObjects);
        }

        return all;
    }

    public ArrayList<Sprite> getAllSprites(int layer)
    {
        ArrayList<Sprite> all = new ArrayList<>();

        Layer aLayer = layers.get(layer);
        if (aLayer != null)
        {
            all.addAll(aLayer.AllObjects);
        }

        return all;
    }

    private void collectMemoryInfo()
    {
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();
        long allocatedMemory = runtime.totalMemory();

        strMemoryUsage = "MEM: " + format.format(allocatedMemory / (1024 * 1024)) + " MB";
    }

    private void collisionProcess()
    {
        this.allNodes.clear();

        for (int i = MIN_LAYER; i <= MAX_LAYER; i++)
        {
            Layer aLayer = layers.get(i);
            if (aLayer == null)
            {
                continue;
            }

            this.allNodes.addAll(aLayer.AllObjects);
        }

        PhysicsCollisionProcess.collisionDetectionBasedOnCategory(this.allNodes);

        PhysicsCollisionProcess.collisionDetectionArbitrary(this.allNodes);

        this.allNodes.clear();
    }

}
