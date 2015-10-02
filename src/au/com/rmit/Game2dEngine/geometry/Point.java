/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import static java.lang.Math.sqrt;

/**
 *
 * @author Philology
 */
public class Point extends Shape
{

    public double x, y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getDistanceFrom(Point B)
    {
        double delX = B.x - x;
        double delY = B.y - y;
        return sqrt(delX * delX + delY * delY);
    }

    @Override
    public void draw(Graphics2D theGraphicsInTheScene, Color theColor)
    {
        super.draw(theGraphicsInTheScene, theColor); //To change body of generated methods, choose Tools | Templates.

        theGraphicsInTheScene.setColor(theColor);
        theGraphicsInTheScene.fillArc((int) x, (int) y, 2, 2, 0, 360);
    }

    @Override
    public void refresh(double changeX, double changeY, double changeWidth, double changeHeight)
    {
        super.refresh(changeX, changeY, changeWidth, changeHeight);

        x += changeX;
        y += changeY;
    }

}
