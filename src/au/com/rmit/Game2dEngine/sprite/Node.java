/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.sprite;

import au.com.rmit.Game2dEngine.geometry.CircleShape;
import au.com.rmit.Game2dEngine.geometry.Shape;
import java.util.Random;

/**
 *
 * @author ricolwang
 */
public class Node
{

    public String identifier;
    private double x = 0;
    private double y = 0;
    private double width = 0;
    private double height = 0;

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
        double oldX = this.x;
        this.x = x;
        this.refreshShape(x - oldX, 0, 0, 0);
    }

    public void setY(double y)
    {
        double oldY = this.y;
        this.y = y;
        this.refreshShape(0, y - oldY, 0, 0);
    }

    public void setWidth(double width)
    {
        double oldWidth = this.width;
        this.width = width;
        this.refreshShape(0, 0, width - oldWidth, 0);
    }

    public void setHeight(double height)
    {
        double oldHeight = this.height;
        this.height = height;
        this.refreshShape(0, 0, 0, height - oldHeight);
    }

    public Shape getTheShape()
    {
        return this.theShape;
    }

    public void setTheShape(Shape theShape)
    {
        this.theShape = theShape;

        if (this.theShape != null)
            this.theShape.setTheNode(this);

        this.onShapeAdded(theShape);
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

    public void onShapeAdded(Shape theShape)
    {

    }

    void refreshShape(double changeX, double changeY, double changeWidth, double changeHeight)
    {
        if (this.theShape == null)
        {
            CircleShape aCircleShape = new CircleShape(this.getCentreX(), this.getCentreY(), this.getWidth() > this.getHeight() ? this.getWidth() / 2.0f : this.getHeight() / 2.0f);
            this.setTheShape(aCircleShape);
        }

        this.theShape.refresh(changeX, changeY, changeWidth, changeHeight);
    }
}
