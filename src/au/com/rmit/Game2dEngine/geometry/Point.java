/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import au.com.rmit.Game2dEngine.math.MathConsts;
import java.awt.Color;
import java.awt.Graphics2D;
import static java.lang.Math.abs;
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

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Point)
        {
            Point B = (Point) obj;
            return abs(this.x - B.x) <= MathConsts.E && abs(this.y - B.y) <= MathConsts.E;
        } else
            return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    public static Point getZeroPoint()
    {
        Point zero = new Point(0, 0);
        return zero;
    }
}
