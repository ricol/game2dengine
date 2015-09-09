/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author ricolwang
 */
public class SpecialRectangleShape extends ClosureShape
{

    public double left, top, width, height;

    public SpecialRectangleShape(double left, double top, double width, double height)
    {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    @Override
    public void refresh(double changeX, double changeY, double changeWidth, double changeHeight)
    {
        super.refresh(changeX, changeY, changeWidth, changeHeight);
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
            return Shape.RectangleCollideWithCircle(this, (CircleShape) theShape);
        } else if (theShape instanceof SpecialRectangleShape)
        {
            return Shape.RectangleCollideWithRectangle(this, (SpecialRectangleShape) theShape);
        } else
            return false;
    }

    @Override
    public void draw(Graphics2D theGraphicsInTheScene, Color theColor)
    {
        theGraphicsInTheScene.setColor(theColor);
        theGraphicsInTheScene.drawRect((int) this.left, (int) this.top, (int) width - 1, (int) height - 1);
    }

    @Override
    public void print(String text)
    {
        System.out.println(text + " - RectangleShape: " + left + ", " + top + ", " + width + ", " + height);
    }
}
