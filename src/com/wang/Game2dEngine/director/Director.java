/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.director;

import com.wang.Game2dEngine.scene.Scene;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author ricolwang
 */
public class Director
{

    public Scene currentScene;
    JPanel parent;

    static Director instance = new Director();

    public static Director getSharedInstance()
    {
        return instance;
    }

    private Director()
    {
        System.setProperty("sun.java2d.opengl", "True");
    }

    public void start()
    {
        if (currentScene != null)
        {
            currentScene.start();
        }
    }

    public void pause()
    {
        if (currentScene != null)
        {
            currentScene.pause();
        }
    }

    public void stop()
    {
        if (currentScene != null)
        {
            currentScene.stop();
        }
    }

    public void setParent(JPanel parent)
    {
        this.parent = parent;
        this.parent.setLayout(new BorderLayout());
    }

    public void showScene(Scene scene)
    {
        if (scene == null)
        {
            return;
        }

        if (currentScene != null)
        {
            if (parent != null)
            {
                parent.remove(currentScene.getComponent());
            }
            currentScene.stop();
            currentScene = null;
        }

        currentScene = scene;
        if (parent != null)
        {
            scene.getComponent().setLocation(0, 0);
            scene.getComponent().setSize(new Dimension(parent.getWidth(), parent.getHeight()));

            parent.add(scene.getComponent(), BorderLayout.CENTER);
            parent.repaint();
        }

        scene.start();
    }

    public void updatePosition(int x, int y, int width, int height)
    {
        if (this.currentScene != null)
        {
            this.currentScene.getComponent().setLocation(x, y);
            this.currentScene.getComponent().setSize(new Dimension(width, height));
        }
    }
}
