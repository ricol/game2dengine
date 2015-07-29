/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import java.util.Random;

/**
 *
 * @author ricolwang
 */
public class Node
{

    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected double radius;

    protected Random theRandom = new Random();

    public Node(double x, double y, double width, double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        radius = width > height ? width : height;
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
        radius = width > height ? width : height;
    }

    public void setHeight(double height)
    {
        this.height = height;
        radius = width > height ? width : height;
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
        return distance <= theTarget.radius + this.radius;
    }

}
