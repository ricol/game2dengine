/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.sprite;

import au.com.rmit.Game2dEngine.geometry.shape.CircleShape;
import au.com.rmit.Game2dEngine.geometry.shape.Shape;
import java.util.Random;

/**
 *
 * @author ricolwang
 */
public class Node
{

    public String identifier;
    private double x;
    private double y;
    private double width;
    private double height;

    protected Random theRandom = new Random();
    private Shape theShape = null;

    public Node(double x, double y, double width, double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }

    public void setX(double x)
    {
        this.x = x;
        this.refreshShape();
    }

    public void setY(double y)
    {
        this.y = y;
        this.refreshShape();
    }

    public void setWidth(double width)
    {
        this.width = width;
        this.refreshShape();
    }

    public void setHeight(double height)
    {
        this.height = height;
        this.refreshShape();
    }

    public Shape getTheShape()
    {
        return this.theShape;
    }

    public void setTheShape(Shape theShape)
    {
        this.theShape = theShape;
        this.theShape.setTheNode(this);
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

    void refreshShape()
    {
        if (this.theShape == null)
        {
            this.theShape = new CircleShape(0, 0, 0);
            this.theShape.setTheNode(this);
            ((CircleShape) this.theShape).centreX = this.getCentreX();
            ((CircleShape) this.theShape).centreY = this.getCentreY();
            ((CircleShape) this.theShape).radius = this.getWidth() > this.getHeight() ? (this.getWidth() / 2.0f) : (this.getHeight() / 2.0f);
        }
        
        this.theShape.refresh();
    }
}
