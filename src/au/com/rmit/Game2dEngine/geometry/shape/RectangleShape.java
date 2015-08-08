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
public class RectangleShape extends ClosureShape
{

    public double left, top, width, height;

    public RectangleShape(double left, double top, double width, double height)
    {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void refresh()
    {
        super.refresh(); //To change body of generated methods, choose Tools | Templates.

        this.left = theNode.getX();
        this.top = theNode.getY();
        this.width = theNode.getWidth();
        this.height = theNode.getHeight();
    }

    @Override
    public boolean collideWith(ClosureShape theShape)
    {
        if (theShape instanceof CircleShape)
        {
            CircleShape theCircleShape = (CircleShape) theShape;
            return Shape.RectangleCollideWithCircle(this, theCircleShape);
        } else if (theShape instanceof RectangleShape)
        {
            RectangleShape theRectangleShape = (RectangleShape) theShape;
            return Shape.RectangleCollideWithRectangle(this, theRectangleShape);
        } else
            return false;
    }

    @Override
    public void draw(Graphics2D theGraphicsInTheScene, Color theColor)
    {
        theGraphicsInTheScene.setColor(theColor);
        theGraphicsInTheScene.drawRect((int)this.left, (int)this.top, (int) width - 1, (int) height - 1);
    }

    @Override
    public void print(String text)
    {
        System.out.println(text + " - RectangleShape: " + left + ", " + top + ", " + width + ", " + height);
    }
}
