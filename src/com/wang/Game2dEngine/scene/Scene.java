/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.scene;

import com.wang.Game2dEngine.painter.EngineGraphics;
import com.wang.Game2dEngine.painter.Painter;
import com.wang.Game2dEngine.physics.collision.PhysicsCollisionProcess;
import com.wang.Game2dEngine.sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author ricolwang
 */
public class Scene extends Painter implements Runnable
{

    public static int MIN_LAYER = 0;
    public static int MAX_LAYER = 9;
    public boolean bShowMemoryUsage = true;
    public BufferedImage theImageBackground;
    private boolean bPaused;
    private boolean bQuit;
    private boolean bRunning;

    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private Color theBackgroundColor = new Color(red, green, blue);
    private boolean bEnableCollisionDetect = false;
    private static final long LEFT_TEXT = 30;
    private static final long TOP_TEXT = 45;
    private static final long GAP_TEXT = 20;
    private long lastFPSTime = System.currentTimeMillis();
    private final int FPS_UPDATE_INTERVAL = 500;
    private long lastUpdateFPSTime = System.currentTimeMillis();
    private long fps = 0;
    private float timeEllapsed = 0;
    private long actionCount = 0;
    private long FPS = 200;
    private String strMemoryUsage = "";
    private Graphics g = null;
    private String text;
    private long delta = 0;
    private double currentTime = System.currentTimeMillis();

    protected Random theRandom = new Random();
    private Thread theThread;

    private final HashMap<Integer, Layer> layers = new HashMap<>();
    private final ArrayList<Sprite> allNodes = new ArrayList<>();
    private final ArrayList<Sprite> allInLoop = new ArrayList<>();

    private final Timer theTimer = new Timer(10, e -> timeEllapsed += 0.01);
    private final Timer theTimerForMemory = new Timer(500, e -> collectMemoryInfo());

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
                painterSizeDidChanged();
            }
        });
    }

    @Override
    public void painterSizeDidChanged()
    {
        super.painterSizeDidChanged(); //To change body of generated methods, choose Tools | Templates.

        if (theImage == null || getWidth() <= 0 || getHeight() <= 0) return;

        synchronized (this)
        {
            theImage = null;
            if (theEngineGraphics != null)
            {
                theEngineGraphics.dispose();
            }
            theEngineGraphics = null;

            theImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            EngineGraphics g = new EngineGraphics();
            g.theGraphics = theImage.createGraphics();
            theEngineGraphics = g;
        }
    }

    public void start()
    {
        if (getWidth() > 0 && getHeight() > 0)
        {
            if (theImage == null)
            {
                theImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                if (theEngineGraphics != null)
                {
                    theEngineGraphics.dispose();
                }
                theEngineGraphics = null;
                EngineGraphics g = new EngineGraphics();
                g.theGraphics = theImage.createGraphics();
                theEngineGraphics = g;
            }

            bPaused = false;

            if (theThread == null)
            {
                theThread = new Thread(this);
            }

            theThread.start();
        }

        didStart();
    }

    public void didStart()
    {

    }

    @Override
    public void run()
    {
        if (bRunning) return;

        bRunning = true;
        while (!bQuit)
        {
            currentTime = System.currentTimeMillis();
            delta = (long) (currentTime - lastFPSTime);
            if (delta > 1000.0 / FPS)
            {
                if ((long) (currentTime - lastUpdateFPSTime) >= FPS_UPDATE_INTERVAL)
                {
                    fps = (long) (1000.0 / delta);
                    lastUpdateFPSTime = (long) currentTime;
                }

                lastFPSTime = (long) currentTime;

                try
                {
                    this.updateModel(currentTime);
                    this.updateGUI(currentTime);
                    g = this.getRenderGraphics();
                    g.drawImage(theImage, 0, 0, null);
                    this.render();
                } catch (Exception e)
                {
                    System.out.println("Exception: Graphics failure!");
                } finally
                {
                    if (g != null)
                    {
                        g.dispose();
                        g = null;
                    }
                }

            }
        }

        System.out.println("Game Loop Run Quit!");
        bRunning = false;
    }

    public void pause()
    {
        bPaused = !bPaused;
    }

    public void stop()
    {
        theImage = null;
        if (theEngineGraphics != null)
        {
            theEngineGraphics.dispose();
        }
        theEngineGraphics = null;

        bPaused = true;
        bQuit = true;
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

    private void updateModel(double currentTime)
    {
        allInLoop.clear();

        for (int i = MIN_LAYER; i <= MAX_LAYER; i++)
        {
            Layer aLayer = layers.get(i);
            if (aLayer == null)
            {
                continue;
            }

            allInLoop.addAll(aLayer.AllObjects);
        }

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
                if (aSprite == null) continue;

                aSprite.willUpdateState();
                aSprite.updateState(currentTime);
                aSprite.didUpdateState();

                actionCount += aSprite.getActionCount();

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
            {
                if (aSprite == null) continue;
                aSprite.didCollisionProcess();
            }
        }

        for (Sprite aSprite : allInLoop)
        {
            if (aSprite == null) continue;
            aSprite.didFinishUpdateState();
        }

        didUpdateModel();
    }

    protected void didUpdateModel()
    {

    }

    private void updateGUI(double currentTime)
    {
        //update GUI
        if (theEngineGraphics == null)
        {
            allInLoop.clear();
            return;
        }

        if (theImageBackground != null)
        {
            theEngineGraphics.drawImage(theImageBackground, 0, 0, this.getWidth(), this.getHeight());
        } else
        {
            theEngineGraphics.setColor(theBackgroundColor);
            theEngineGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        for (Sprite aSprite : allInLoop)
        {
            if (aSprite == null) continue;

            aSprite.willUpdateGUI();
            aSprite.updateGUI(theEngineGraphics);
            aSprite.didUpdateGUI();
        }

        //draw fps
        text = "FPS: " + fps;
        theEngineGraphics.setColor(Color.RED);
        theEngineGraphics.drawString(text, LEFT_TEXT, TOP_TEXT);

        //draw sprites count
        int totalNodes = allInLoop.size();

        text = "NODES: " + totalNodes;
        theEngineGraphics.drawString(text, LEFT_TEXT, TOP_TEXT + GAP_TEXT);

        //draw total actions
        text = "ACTIONS: " + actionCount;
        theEngineGraphics.drawString(text, LEFT_TEXT, TOP_TEXT + GAP_TEXT * 2);

        //draw total layers
        int totalLayers = 0;

        for (int i = MIN_LAYER; i <= MAX_LAYER; i++)
        {
            Layer aLayer = layers.get(i);
            if (aLayer == null) continue;

            totalLayers++;
        }
        text = "LAYERS: " + totalLayers;
        theEngineGraphics.drawString(text, LEFT_TEXT, TOP_TEXT + GAP_TEXT * 3);

        //draw current time ellapsed
        text = String.format("TIME: %.2f", timeEllapsed);
        theEngineGraphics.drawString(text, LEFT_TEXT, this.getHeight() - TOP_TEXT);

        if (bShowMemoryUsage) theEngineGraphics.drawString(strMemoryUsage, LEFT_TEXT, this.getHeight() - TOP_TEXT - GAP_TEXT);

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

    public void setFps(int fps)
    {
        if (fps > 150) this.FPS = 150;
        else this.FPS = Math.max(fps, 50);
    }
}
