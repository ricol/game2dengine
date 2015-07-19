/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import au.com.rmit.Game2dEngine.gravity.Gravity;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import javax.imageio.ImageIO;

/**
 *
 * @author Philology
 */
public class Sprite extends Node
{
    private double currentLife = 0;
    private boolean isAlive = true;
    
    public int layer = 0;
    public boolean bCustomDrawing = false;
    public static final long EVER = Long.MAX_VALUE;
    public double mass;
    public Gravity g;
    protected float alpha = 1;
    protected int red = 0;
    protected int green = 0;
    protected int blue = 0;
    protected double lastUpdateTime;
    public double lifetime = 1; //in seconds

    protected double starttime = System.currentTimeMillis();
    protected BufferedImage theImage;

    BufferedImage theImageCanvas;
    Graphics2D theGraphics;

    public Sprite(double x, double y, double width, double height, double mass)
    {
        super(x, y, width, height);

        this.mass = mass;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    BufferedImage getTheImageCanvas()
    {
        if (theImageCanvas == null)
        {
            theImageCanvas = new BufferedImage(abs((int) width), abs((int) height), BufferedImage.TYPE_INT_ARGB);
        }
        return theImageCanvas;
    }

    Graphics2D getTheImageGraphics()
    {
        if (theGraphics == null)
        {
            theGraphics = this.getTheImageCanvas().createGraphics();
        }
        return theGraphics;
    }

    void releaseTheImageCanvas()
    {
        theGraphics = null;
        theImageCanvas = null;
    }

    public void applyGravity(Gravity g)
    {
        this.g = new Gravity(g.GX, g.GY);
    }

    public void updateState(double currentTime)
    {
        double t = (currentTime - this.lastUpdateTime) / 1000.0f;
        currentLife += t;
        if (currentLife >= lifetime)
        {
            this.setDead();
        }
    }

    public void updateGUI(Graphics2D g)
    {
        int w = (int) width;
        int h = (int) height;

        if (abs(w) <= 0.001 || abs(h) <= 0.001)
        {
            return;
        }

        if (g == null)
        {
            return;
        }

        if (this.isAlive)
        {
            if (bCustomDrawing)
            {
                Graphics2D theGraphics2D = this.getTheImageGraphics();
                this.onCustomDraw(theGraphics2D);
            } else
            {
                if (this.theImage != null)
                {
                    Graphics2D theGraphics2D = this.getTheImageGraphics();
                    Color blackTransparent = new Color(0, 0, 0, 0);
                    theGraphics2D.setColor(blackTransparent);
                    theGraphics2D.fillRect(0, 0, w, h);

                    AffineTransform old = theGraphics2D.getTransform();
                    theGraphics2D.rotate(angle);

                    theGraphics2D.drawImage(theImage, 0, 0, w, h, null);
                    theGraphics2D.setTransform(old);
                } else
                {
                    Graphics2D theGraphics2D = this.getTheImageGraphics();
                    Color blackTransparent = new Color(0, 0, 0, 0);
                    theGraphics2D.setColor(blackTransparent);
                    theGraphics2D.fillRect(0, 0, w, h);

                    AffineTransform old = theGraphics2D.getTransform();

                    theGraphics2D.rotate(angle);
                    Color theColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha);
                    theGraphics2D.setColor(theColor);
                    theGraphics2D.fillArc(0, 0, w, h, 0, 360);

                    theGraphics2D.setTransform(old);
                }
            }

            if (theImageCanvas != null)
            {
                Composite old = g.getComposite();

                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g.setComposite(ac);
                g.drawImage(theImageCanvas, (int) x, (int) y, null);

                g.setComposite(old);
            }
        }
    }

    public boolean isAlive()
    {
        return this.isAlive;
    }

    public void setImage(String imageName)
    {
        try
        {
            theImage = ImageIO.read(new File(imageName));
        } catch (IOException e)
        {
            theImage = null;
        }
    }

    @Override
    public void setWidth(double width)
    {
        super.setWidth(width);
        this.releaseTheImageCanvas();
    }

    @Override
    public void setHeight(double height)
    {
        super.setHeight(height);
        this.releaseTheImageCanvas();
    }

    public float getAlpha()
    {
        return alpha;
    }

    public void setAlpha(float alpha)
    {
        if (alpha < 0)
        {
            alpha = 0;
        }
        if (alpha > 1)
        {
            alpha = 1;
        }
        this.alpha = alpha;
    }

    public int getRed()
    {
        return red;
    }

    public int getGreen()
    {
        return green;
    }

    public int getBlue()
    {
        return blue;
    }

    public void setRed(int value)
    {
        if (value < 0)
        {
            value = 0;
        }
        if (value > 255)
        {
            value = 255;
        }
        this.red = value;
    }

    public void setGreen(int value)
    {
        if (value < 0)
        {
            value = 0;
        }
        if (value > 255)
        {
            value = 255;
        }
        this.green = value;
    }

    public void setBlue(int value)
    {
        if (value < 0)
        {
            value = 0;
        }
        if (value > 255)
        {
            value = 255;
        }
        this.blue = value;
    }

    public double getCentreX()
    {
        return this.x + width / 2.0;
    }

    public double getCentreY()
    {
        return this.y + height / 2.0;
    }

    public void setCentreX(double value)
    {
        this.setX(value - width / 2.0);
    }

    public void setCentreY(double value)
    {
        this.setY(value - height / 2.0);
    }
    
    public void setDead()
    {
        this.isAlive = false;
        this.onDead();
    }

    public void onCustomDraw(Graphics2D theGraphics2D)
    {

    }
    
    public void onDead()
    {
        
    }
}
