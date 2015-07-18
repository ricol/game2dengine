/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import au.com.rmit.Game2dEngine.gravity.Gravity;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Philology
 */
public class Sprite extends Node
{

    public double mass;
    public Gravity g;
    public Color color;
    protected double lastUpdateTime;
    public double lifetime = 1; //in seconds
    protected double currentLife = 0;
    protected boolean isAlive = true;
    protected double starttime = System.currentTimeMillis();
    protected BufferedImage theImage;

    public Sprite(double x, double y, double width, double height, double mass)
    {
        super(x, y, width, height);

        this.mass = mass;
        this.lastUpdateTime = System.currentTimeMillis();
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
            this.isAlive = false;
        }
    }

    public void updateGUI(Graphics g)
    {
        if (this.isAlive)
        {
            if (this.theImage != null)
            {
                g.drawImage(theImage, (int) x, (int) y, null);
            } else
            {
                if (color == null)
                {
                    g.setColor(Color.RED);
                } else
                {
                    g.setColor(color);
                }

                g.fillArc((int) x, (int) y, (int) width, (int) height, 0, 360);
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
}