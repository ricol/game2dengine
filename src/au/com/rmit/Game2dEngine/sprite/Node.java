/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.sprite;

import au.com.rmit.Game2dEngine.geometry.shape.CircleShape;
import java.util.Random;

/**
 *
 * @author ricolwang
 */
public class Node
{
    public String identifier;
    protected double x;
    protected double y;
    private double width;
    private double height;

    protected Random theRandom = new Random();
    private CircleShape theCircleShape = new CircleShape(0, 0, 0); //coordinates are based on the node

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
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void setWidth(double width)
    {
        this.width = width;
        this.rebuildTheCircleShape();
    }

    public void setHeight(double height)
    {
        this.height = height;
        this.rebuildTheCircleShape();
    }

    public CircleShape getTheCircleShape()
    {
        return this.theCircleShape;
    }

    public void setTheCircleShape(CircleShape theCircleShape)
    {
        this.theCircleShape = theCircleShape;
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

    public boolean rectangleOverlaps(final Node target)
    {
        return x < target.x + target.width && x + width > target.x && y < target.y + target.height && y + height > target.y;
    }

    public boolean circleOverlaps(final Node theTarget)
    {
        double delX = theTarget.getCentreX() - this.getCentreX();
        double delY = theTarget.getCentreY() - this.getCentreY();
        double distance = Math.sqrt(delX * delX + delY * delY);
        double targetRadius = theTarget.getTheCircleShape().radius;
        double thisRadius = this.getTheCircleShape().radius;
        return distance < targetRadius + thisRadius;
    }

    private void rebuildTheCircleShape()
    {
        this.theCircleShape.centreX = this.width / 2.0f;
        this.theCircleShape.centreY = this.height / 2.0f;
        this.theCircleShape.radius = width > height ? (width / 2.0f) : (height / 2.0f);
    }
}
