/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import au.com.rmit.Game2dEngine.geometry.shape.CircleShape;
import au.com.rmit.Game2dEngine.geometry.shape.Shape;
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
    protected Shape theShape = new CircleShape(this.getCentreX(), this.getCentreY(), 0);

    protected Random theRandom = new Random();

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

    public Shape getShape()
    {
        return this.theShape;
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
    }

    public void setHeight(double height)
    {
        this.height = height;
    }

    public void setTheShape(Shape theShape)
    {
        this.theShape = theShape;
    }

    public Shape getTheShape()
    {
        return this.theShape;
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

    public boolean overlaps(float targetX, float targetY, float targetWidth, float targetHeight)
    {
        return x < targetX + targetWidth && x + width > targetX && y < targetY + targetHeight && y + height > targetY;
    }
    
    public boolean overlaps(Shape theTarget)
    {
        if ((theTarget instanceof CircleShape) && (theShape instanceof CircleShape))
        {
            CircleShape theTargetCircle = (CircleShape)theTarget;
            CircleShape myself = (CircleShape)theShape;
            double delX = theTargetCircle.centreX - myself.centreX;
            double delY = theTargetCircle.centreY - myself.centreY;
            double distance = Math.sqrt(delX * delX + delY * delY);
            return distance <= theTargetCircle.radius + myself.radius;
        }else
            return false;
    }

}
