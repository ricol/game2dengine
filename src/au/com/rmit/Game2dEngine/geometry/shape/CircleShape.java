/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry.shape;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author ricolwang
 */
public class CircleShape extends ClosureShape
{

    public double centreX, centreY;
    public double radius;
    SquareShape theSquareShape = new SquareShape(0, 0, 0, 0);

    public CircleShape(double centreX, double centreY, double radius)
    {
        this.centreX = centreX;
        this.centreY = centreY;
        this.radius = radius;
    }

    @Override
    public boolean intersect(ClosureShape theTarget)
    {
        return false;
    }

    @Override
    public boolean contain(ClosureShape theTarget)
    {
        return false;
    }

    @Override
    public double getCircumference()
    {
        return Math.PI * this.radius * 2;
    }

    @Override
    public double getArea()
    {
        return Math.PI * this.radius * this.radius;
    }

    @Override
    public boolean collideWith(ClosureShape theShape)
    {
        if (theShape instanceof CircleShape)
        {
            CircleShape theCircleShape = (CircleShape) theShape;
            return Shape.CircleCollideWithCircle(this, theCircleShape);
        } else if (theShape instanceof RectangleShape)
        {
            return Shape.RectangleCollideWithRectangle(this.theSquareShape, (RectangleShape) theShape);
        } else
            return false;
    }

    @Override
    public void refresh()
    {
        super.refresh(); //To change body of generated methods, choose Tools | Templates.

        this.centreX = theNode.getCentreX();
        this.centreY = theNode.getCentreY();
        this.radius = theNode.getWidth() > theNode.getHeight() ? theNode.getWidth() / 2.0f : theNode.getHeight() / 2.0f;

        this.theSquareShape.left = this.centreX - this.radius;
        this.theSquareShape.top = this.centreY - this.radius;
        this.theSquareShape.width = this.radius * 2;
        this.theSquareShape.height = this.radius * 2;
    }

    @Override
    public void draw(Graphics2D theGraphicsInTheScene, Color theColor)
    {
        int tmpRadius = (int) radius;
        int tmpX = (int) (centreX - tmpRadius);
        int tmpY = (int) (centreY - tmpRadius);
        theGraphicsInTheScene.setColor(theColor);
        theGraphicsInTheScene.drawArc(tmpX, tmpY, 2 * tmpRadius, 2 * tmpRadius, 0, 360);
    }

    @Override
    public void print(String text)
    {
        System.out.println(text + " - CircleShape: Radius: " + radius + " at centre: " + centreX + " : " + centreY);
    }

    public SquareShape getTheSquareShape()
    {
        return this.theSquareShape;
    }
}
