/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.painter;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 *
 * @author ricolwang
 */
public class Panel extends Canvas
{

    BufferStrategy strategy;

    public void render()
    {
        strategy.show();
    }

    public Graphics getRenderGraphics()
    {
        if (strategy == null)
        {
            createBufferStrategy(2);
            strategy = this.getBufferStrategy();
            this.setIgnoreRepaint(true);
        }
        
        return strategy.getDrawGraphics();
    }
}
