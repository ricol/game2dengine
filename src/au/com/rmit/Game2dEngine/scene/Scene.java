/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.scene;

import au.com.rmit.Game2dEngine.common.Game2dEngineShared;
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

    public BufferedImage theImageBackground;
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private Color theBackgroundColor = new Color(red, green, blue);

    protected Random theRandom = new Random();
    public boolean bPaused;
    static long INTERVAL = 500;
    static long DELAY = 5;
    static long LEFT_TEXT = 25;
    static long TOP_TEXT = 30;
    static long GAP_TEXT = 20;

    public boolean bShowMemoryUsage = true;

    long number = 0;
    long lastTime = System.currentTimeMillis();
    long fps = 0;
    float timeEllapsed = 0;
    long actionCount = 0;
    String strFreeMemory = "";
    String strAllocatedMemory = "";
    String strMaxMemory = "";
    String strTotalFreeMemory = "";

    HashMap<Integer, Layer> layers = new HashMap();
    ArrayList<Sprite> allNodes = new ArrayList();
    ArrayList<Sprite> allNodesRequestCollisionDetect = new ArrayList<>();

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

    public void updateState()
    {

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

        if (!bPaused)
        {
            number++;

            double currentTime = System.currentTimeMillis();
            for (int i = MIN_LAYER; i <= MAX_LAYER; i++)
            {
                Layer aLayer = layers.get(i);
                if (aLayer == null)
                {
                    continue;
                }

                //remove all dead sprites
                for (Sprite aSprite : aLayer.DeadObjects)
                {
                    aSprite.theScene = null;
                }

                aLayer.AllObjects.removeAll(aLayer.DeadObjects);
                aLayer.DeadObjects.clear();

                actionCount = 0;
                //update sprites states
                for (Sprite aSprite : aLayer.AllObjects)
                {
                    aSprite.updateState(currentTime);

                    if (aSprite instanceof Sprite)
                    {
                        actionCount += ((Sprite) aSprite).getActionCount();
                    }

                    if (!aSprite.isAlive())
                    {
                        aLayer.DeadObjects.add(aSprite);
                        aSprite.onRemovedFromLayer(aLayer);
                    }
                }

                aLayer.AllObjects.addAll(aLayer.NewObjects);
                aLayer.NewObjects.clear();
            }

            updateState();

            collisionDetect();
        }

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

            for (int i = MIN_LAYER; i <= MAX_LAYER; i++)
            {
                Layer aLayer = layers.get(i);
                if (aLayer == null)
                {
                    continue;
                }

                for (Sprite aSprite : aLayer.AllObjects)
                {
                    aSprite.updateGUI(theGraphics2D);
                }
            }

            long time = System.currentTimeMillis();
            long delta = time - lastTime;
            if (delta > INTERVAL)
            {
                fps = (long) ((number / (delta * 1.0)) * 1000);
                number = 0;
                lastTime = time;
            }

            //draw fps
            String text = "FPS: " + fps;
            theGraphics2D.setColor(Color.RED);
            theGraphics2D.drawString(text, LEFT_TEXT, TOP_TEXT);

            //draw sprites count
            int totalNodes = 0;
            int totalLayers = 0;

            for (int i = MIN_LAYER; i <= MAX_LAYER; i++)
            {
                Layer aLayer = layers.get(i);
                if (aLayer == null)
                {
                    continue;
                }

                totalLayers++;
                totalNodes += aLayer.AllObjects.size();
            }

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
            {
                theGraphics2D.drawString(strFreeMemory, LEFT_TEXT, this.getHeight() - TOP_TEXT * 2);
                theGraphics2D.drawString(strAllocatedMemory, LEFT_TEXT, this.getHeight() - TOP_TEXT * 3);
                theGraphics2D.drawString(strMaxMemory, LEFT_TEXT, this.getHeight() - TOP_TEXT * 4);
                theGraphics2D.drawString(strTotalFreeMemory, LEFT_TEXT, this.getHeight() - TOP_TEXT * 5);
            }
        }
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

    private void collectMemoryInfo()
    {
        Runtime runtime = Runtime.getRuntime();

        NumberFormat format = NumberFormat.getInstance();

        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        strFreeMemory = "Free Memory: " + format.format(freeMemory / (1024 * 1024)) + " MB";
        strAllocatedMemory = "Allocated Memory: " + format.format(allocatedMemory / (1024 * 1024)) + " MB";
        strMaxMemory = "Max Memory: " + format.format(maxMemory / (1024 * 1024)) + " MB";
        strTotalFreeMemory = "Total Free Memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / (1024 * 1024)) + " MB";
    }

//    private void collisionDetect()
//    {
//        this.allNodes.clear();
//        this.allNodesRequestCollisionDetect.clear();
//
//        for (int i = MIN_LAYER; i <= MAX_LAYER; i++)
//        {
//            Layer aLayer = layers.get(i);
//            if (aLayer == null)
//            {
//                continue;
//            }
//
//            this.allNodes.addAll(aLayer.AllObjects);
//        }
//
//        for (Sprite aSprite : this.allNodes)
//        {
//            if (aSprite.getCollisionTargetCategory() > 0 && aSprite.bCollisionDetect)
//            {
//                this.allNodesRequestCollisionDetect.add(aSprite);
//            }
//        }
//
//        while (this.allNodesRequestCollisionDetect.size() > 0)
//        {
//            Sprite aSprite = this.allNodesRequestCollisionDetect.get(0);
//
//            this.allNodesRequestCollisionDetect.remove(0);
//
//            Sprite aCopy = (Sprite) aSprite.getACopy();
//            Game2dEngineShared.TypeCollisionDetection value;
//
//            int index = -1;
//
//            for (Sprite aTargetSprite : this.allNodes)
//            {
//                if (aSprite.equals(aTargetSprite))
//                {
//                    index = this.allNodes.indexOf(aTargetSprite);
//                    continue;
//                }
//                //the target allows to be detected
//                if (!aTargetSprite.bCollisionDetect)
//                {
//                    continue;
//                }
//
//                //the target belongs to the group
//                if (!aSprite.isInTheTargetCollisionCategory(aTargetSprite.getCollisionCategory()))
//                {
//                    continue;
//                }
//
//                //collide with this sprite or not.
//                if (aSprite.collideWith(aTargetSprite))
//                {
//                    //collide
//                    value = Game2dEngineShared.TypeCollisionDetection.COLLIDED;
//                    if (aSprite.hashCollision.get(aTargetSprite) != value)
//                    {
//                        aSprite.onCollideWith(aTargetSprite);
//                        aSprite.hashCollision.put(aTargetSprite, value);
//                        //the state of aSprite may change
//                    }
//
//                    if (aTargetSprite.hashCollision.get(aSprite) != value)
//                    {
//                        if (aCopy != null)
//                        {
//                            aTargetSprite.onCollideWith(aCopy);
//                        }
//                        aTargetSprite.hashCollision.put(aSprite, value);
//                    }
//                } else
//                {
//                    //uncollide
//                    value = Game2dEngineShared.TypeCollisionDetection.UNCOLLIDED;
//                    if (aSprite.hashCollision.get(aTargetSprite) != value)
//                    {
//                        aSprite.onNotCollideWith(aTargetSprite);
//                        aSprite.hashCollision.put(aTargetSprite, value);
//                    }
//
//                    if (aTargetSprite.hashCollision.get(aSprite) != value)
//                    {
//                        if (aCopy != null)
//                        {
//                            aTargetSprite.onNotCollideWith(aCopy);
//                        }
//                        aTargetSprite.hashCollision.put(aSprite, value);
//                    }
//                }
//            }
//
//            if (index >= 0)
//            {
//                this.allNodes.remove(index);
//            }
//        }
//
//        this.allNodes.clear();
//        this.allNodesRequestCollisionDetect.clear();
//    }
    
    private void collisionDetect()
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

        for (Sprite aSprite : this.allNodes)
        {
            if (!aSprite.bCollisionDetect) continue;
            
            if (aSprite.getCollisionTargetCategory() <= 0) continue;
            
            Sprite aCopy = (Sprite) aSprite.getACopy();
            Game2dEngineShared.TypeCollisionDetection value;

            for (Sprite aTargetSprite : this.allNodes)
            {
                if (aSprite.equals(aTargetSprite)) continue;
                
                //the target allows to be detected
                if (!aTargetSprite.bCollisionDetect) continue;

                //the target belongs to the group
                if (!aSprite.isInTheTargetCollisionCategory(aTargetSprite.getCollisionCategory())) continue;

                //collide with this sprite or not.
                if (aSprite.collideWith(aTargetSprite))
                {
                    //collide
                    value = Game2dEngineShared.TypeCollisionDetection.COLLIDED;
                    if (aSprite.hashCollision.get(aTargetSprite) != value)
                    {
                        aSprite.onCollideWith(aTargetSprite);
                        aSprite.hashCollision.put(aTargetSprite, value);
                        //the state of aSprite may change
                    }

                    if (aTargetSprite.hashCollision.get(aSprite) != value)
                    {
                        if (aCopy != null)
                        {
                            aTargetSprite.onCollideWith(aCopy);
                        }
                        aTargetSprite.hashCollision.put(aSprite, value);
                    }
                } else
                {
                    //uncollide
                    value = Game2dEngineShared.TypeCollisionDetection.UNCOLLIDED;
                    if (aSprite.hashCollision.get(aTargetSprite) != value)
                    {
                        aSprite.onNotCollideWith(aTargetSprite);
                        aSprite.hashCollision.put(aTargetSprite, value);
                    }

                    if (aTargetSprite.hashCollision.get(aSprite) != value)
                    {
                        if (aCopy != null)
                        {
                            aTargetSprite.onNotCollideWith(aCopy);
                        }
                        aTargetSprite.hashCollision.put(aSprite, value);
                    }
                }
            }

        }

        this.allNodes.clear();
    }

}
