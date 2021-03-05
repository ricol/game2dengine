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

    void drawString(String text, float x, float y);

    void drawImage(Image img, int x, int y, int width, int height);

    void setColor(Color color);

    void fillRect(int x, int y, int width, int height);

    void dispose();

    void drawRect(int x, int y, int width, int height);

    void drawLine(int x0, int y0, int x1, int y1);

    void fillArc(int x, int y, int width, int height, int startAngel, int endAngel);

    void drawArc(int x, int y, int width, int height, int startAngel, int endAngel);

    void setBackground(Color blackTransparent);

    void clearRect(int x, int y, int width, int height);

    AffineTransform getTransform();

    void rotate(double theta, float x, float y);

    void setTransform(AffineTransform t);

    Composite getComposite();

    void setComposite(AlphaComposite ac);

    void drawImage(BufferedImage image, int x, int y);

    void setComposite(Composite c);

    void setFont(Font f);

    void fillOval(int x, int y, int width, int height);

    void drawPolygon(Polygon p);

    void setStroke(BasicStroke basicStroke);
}
