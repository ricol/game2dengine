/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.scene;

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
    Graphics2D theGraphics;

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
                theGraphics = null;

                theImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                theGraphics = theImage.createGraphics();
            }
        });
    }

    public void start()
    {
        if (this.getWidth() > 0 && this.getHeight() > 0)
        {
            if (this.theImage == null)
            {
                this.theImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
                this.theGraphics = this.theImage.createGraphics();
            }

            this.bPaused = false;
        }
    }

    public void pause()
    {
        this.bPaused = !this.bPaused;
    }

    public void stop()
    {
        this.theImage = null;
        this.theGraphics = null;

        this.bPaused = true;
    }

    public void updateState()
    {

    }

    @Override
    public void paint(Graphics g)
    {
        this.Loop();

        if (this.theImage != null)
        {
            g.drawImage(this.theImage, 0, 0, null);
        }

        try
        {
            Thread.sleep(DELAY);
            this.repaint();
        } catch (InterruptedException ex)
        {

        }
    }

    private void Loop()
    {

        this.number++;

        double currentTime = System.currentTimeMillis();

        if (!bPaused)
        {
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

                //update sprites states
                for (Sprite aSprite : aLayer.AllObjects)
                {
                    aSprite.updateState(currentTime);

                    if (!aSprite.isAlive())
                    {
                        aLayer.DeadObjects.add(aSprite);
                    }
                }

                aLayer.AllObjects.addAll(aLayer.NewObjects);
                aLayer.NewObjects.clear();

                this.updateState();
            }
        }

        //update GUI
        if (theGraphics != null)
        {
            theGraphics.setColor(Color.BLACK);
            theGraphics.fillRect(0, 0, this.size().width, this.size().height);

            for (int i = 0; i <= MAX_LAYERS; i++)
            {
                Layer aLayer = layers.get(i);
                if (aLayer == null)
                {
                    continue;
                }

                for (Sprite aSprite : aLayer.AllObjects)
                {
                    aSprite.updateGUI(theGraphics);
                }
            }

            long time = System.currentTimeMillis();
            long delta = time - this.lastTime;
            if (delta > INTERVAL)
            {
                this.fps = (long) ((this.number / (delta * 1.0)) * 1000);
                this.number = 0;
                this.lastTime = time;
            }

            //draw fps
            String text = "FPS: " + fps;
            theGraphics.setColor(Color.RED);
            theGraphics.drawString(text, LEFT_TEXT, TOP_TEXT);

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
            theGraphics.drawString(text, LEFT_TEXT, TOP_TEXT + GAP_TEXT);

            //draw total layers
            text = "LAYERS: " + totalLayers;
            theGraphics.drawString(text, LEFT_TEXT, TOP_TEXT + GAP_TEXT * 2);

            //draw current time ellapsed
            text = String.format("TIME: %.2f", timeEllapsed);
            theGraphics.drawString(text, LEFT_TEXT, this.getHeight() - TOP_TEXT);
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
        this.addSprite(aSprite, 0);
    }
}
