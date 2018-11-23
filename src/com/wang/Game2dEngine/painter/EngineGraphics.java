/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.painter;

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author ricolwang
 */
public class EngineGraphics implements IEngineGraphics
{

    public Graphics2D theGraphics;

    @Override
    public void drawString(String text, float x, float y)
    {
        theGraphics.drawString(text, x, y);
    }

    @Override
    public void drawImage(Image img, int x, int y,
            int width, int height)
    {
        theGraphics.drawImage(img, x, y, width, height, null);
    }

    @Override
    public void setColor(Color color)
    {
        theGraphics.setColor(color);
    }

    @Override
    public void fillRect(int x, int y, int width, int height)
    {
        theGraphics.fillRect(x, y, width, height);
    }

    @Override
    public void dispose()
    {
        theGraphics.dispose();
    }

    @Override
    public void drawRect(int x, int y, int width, int height)
    {
        theGraphics.drawRect(x, y, width, height);
    }

    @Override
    public void drawLine(int x0, int y0, int x1, int y1)
    {
        theGraphics.drawLine(x0, y0, x1, y1);
    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngel, int endAngel)
    {
        theGraphics.fillArc(x, y, width, height, startAngel, endAngel);
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngel, int endAngel)
    {
        theGraphics.drawArc(x, y, width, height, startAngel, endAngel);
    }

    @Override
    public void setBackground(Color blackTransparent)
    {
        theGraphics.setBackground(blackTransparent);
    }

    @Override
    public void clearRect(int x, int y, int width, int height)
    {
        theGraphics.clearRect(x, y, width, height);
    }

    @Override
    public AffineTransform getTransform()
    {
        return theGraphics.getTransform();
    }

    @Override
    public void rotate(double theta, float x, float y)
    {
        theGraphics.rotate(theta, x, y);
    }

    @Override
    public void setTransform(AffineTransform t)
    {
        theGraphics.setTransform(t);
    }

    @Override
    public Composite getComposite()
    {
        return theGraphics.getComposite();
    }

    @Override
    public void setComposite(AlphaComposite ac)
    {
        theGraphics.setComposite(ac);
    }

    @Override
    public void drawImage(BufferedImage image, int x, int y)
    {
        theGraphics.drawImage(image, x, y, null);
    }

    @Override
    public void setComposite(Composite c)
    {
        theGraphics.setComposite(c);
    }

    @Override
    public void setFont(Font f)
    {
        theGraphics.setFont(f);
    }

    @Override
    public void fillOval(int x, int y, int width, int height)
    {
        theGraphics.fillOval(x, y, width, height);
    }

    @Override
    public void drawPolygon(Polygon p)
    {
        theGraphics.drawPolygon(p);
    }

    @Override
    public void setStroke(BasicStroke basicStroke)
    {
        theGraphics.setStroke(basicStroke);
    }
}
