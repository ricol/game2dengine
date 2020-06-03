/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.painter;

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import com.wang.Game2dEngine.painter.interfaces.IPainter;
import com.wang.Game2dEngine.painter.interfaces.IUserInteraction;
import com.wang.Game2dEngine.painter.interfaces.IWindow;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 *
 * @author ricolwang
 */
public class Painter implements IPainter, IUserInteraction, IWindow
{

    private Panel panel = new Panel();
    protected BufferedImage theImage;
    protected IEngineGraphics theEngineGraphics;

    @Override
    public int getHeight()
    {
        return panel.getHeight();
    }

    @Override
    public int getWidth()
    {
        return panel.getWidth();
    }

    @Override
    public Component getComponent()
    {
        return panel;
    }

    @Override
    public void addMouseListener(MouseListener listener)
    {
        panel.addMouseListener(listener);
    }

    @Override
    public void addComponentListener(ComponentAdapter componentAdapter)
    {
        panel.addComponentListener(componentAdapter);
    }

    @Override
    public void setSize(Dimension d)
    {
        panel.setSize(d);
    }

    public void render()
    {
        panel.render();
    }

    public Graphics getRenderGraphics()
    {
        return panel.getRenderGraphics();
    }

    public void painterSizeDidChanged()
    {

    }
}
