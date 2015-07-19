/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.scene;

import au.com.rmit.Game2dEngine.node.MovingSprite;
import au.com.rmit.Game2dEngine.node.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
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
    public BufferedImage theImageBackground;
    public Color theColorBackground = Color.black;
    protected Random theRandom = new Random();
    public boolean bPaused;
    static long INTERVAL = 500;
    static long DELAY = 5;
    static long LEFT_TEXT = 25;
    static long TOP_TEXT = 30;
    static long GAP_TEXT = 20;
    static int MAX_LAYERS = 10;

    long number = 0;
    long lastTime = System.currentTimeMillis();
    long fps = 0;
    float timeEllapsed = 0;
    long actionCount = 0;

    HashMap<Integer, Layer> layers = new HashMap();

    Timer theTimer = new Timer(10, new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            timeEllapsed += 0.01;
        }
    });

    BufferedImage theImage;
    Graphics2D theGraphics2D;

    public Scene()
    {
        theTimer.start();

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

    public void addSprite(Sprite aSprite, int zOrder)
    {
        if (zOrder < 0 || zOrder > MAX_LAYERS)
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
        addSprite(aSprite, aSprite.layer);
    }

    private void Loop()
    {

        if (!bPaused)
        {
            number++;

            double currentTime = System.currentTimeMillis();
            for (int i = 0; i <= MAX_LAYERS; i++)
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

                    if (aSprite instanceof MovingSprite)
                    {
                        actionCount += ((MovingSprite) aSprite).getActionCount();
                    }

                    if (!aSprite.isAlive())
                    {
                        aLayer.DeadObjects.add(aSprite);
                    }
                }

                aLayer.AllObjects.addAll(aLayer.NewObjects);
                aLayer.NewObjects.clear();

                updateState();
            }
        }

        //update GUI
        if (theGraphics2D != null)
        {
//            theGraphics2D.setColor(Color.BLACK);
//            theGraphics2D.fillRect(0, 0, this.size().width, this.size().height);
//            Color blackTransparent = new Color(0, 0, 0, 0);
//            theGraphics2D.setColor(blackTransparent);
//            theGraphics2D.fillRect(0, 0, this.size().width, this.size().height);
            if (theImageBackground != null)
            {
                theGraphics2D.drawImage(theImageBackground, 0, 0, this.getWidth(), this.getHeight(), null);
            }else
            {
                theGraphics2D.setColor(theColorBackground);
                theGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            
            

            for (int i = 0; i <= MAX_LAYERS; i++)
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

            for (int i = 0; i <= MAX_LAYERS; i++)
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
        }
    }
}
