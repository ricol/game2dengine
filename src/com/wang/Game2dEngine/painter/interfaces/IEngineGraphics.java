/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.painter.interfaces;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author ricolwang
 */
public interface IEngineGraphics
{

    public void drawString(String text, float x, float y);

    public void drawImage(Image img, int x, int y, int width, int height);

    public void setColor(Color color);

    public void fillRect(int x, int y, int width, int height);

    public void dispose();

    public void drawRect(int x, int y, int width, int height);

    public void drawLine(int x0, int y0, int x1, int y1);

    public void fillArc(int x, int y, int width, int height, int startAngel, int endAngel);

    public void drawArc(int x, int y, int width, int height, int startAngel, int endAngel);

    public void setBackground(Color blackTransparent);

    public void clearRect(int x, int y, int width, int height);

    public AffineTransform getTransform();

    public void rotate(double theta, float x, float y);

    public void setTransform(AffineTransform t);

    public Composite getComposite();

    public void setComposite(AlphaComposite ac);

    public void drawImage(BufferedImage image, int x, int y);

    public void setComposite(Composite c);

    public void setFont(Font f);

    public void fillOval(int x, int y, int width, int height);

    public void drawPolygon(Polygon p);

    public void setStroke(BasicStroke basicStroke);
}
