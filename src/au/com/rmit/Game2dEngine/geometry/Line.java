/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import au.com.rmit.Game2dEngine.math.QuadraticEquation;
import java.awt.Color;
import java.awt.Graphics2D;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ricolwang
 */
public class Line extends Shape
{

    public Point start;
    public Point end;

    public Line(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2)
    {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    public double getLength()
    {
        return start.getDistanceFrom(end);
    }

    @Override
    public void draw(Graphics2D theGraphicsInTheScene, Color theColor)
    {
        super.draw(theGraphicsInTheScene, theColor); //To change body of generated methods, choose Tools | Templates.

        theGraphicsInTheScene.setColor(theColor);
        theGraphicsInTheScene.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);
    }
    
    public Set<Line> getSeperateLines(double distance)
    {
        Set<Line> lines = new HashSet<>();

        double a = start.x, b = start.y, c = end.x, d = end.y;
        
        Point middle = new Point((a + c) / 2, (b + d) / 2);
        if (abs(b - d) > 0.00001)
        {
            double t = getT(a, b, c, d);
            double A = (1 + ((a - c) / (b - d)) * ((a - c) / (b - d)));
            double B = ((b + d) * (a - c) / (b - d) - (a + c) - 2 * t * (a - c) / (b - d));
            double C = ((a + c) / 2) * ((a + c) / 2) + ((b + d) / 2) * ((b + d) / 2) + t * t - (b + d) * t - distance * distance;
            QuadraticEquation theEquation = new QuadraticEquation(A, B, C);
            double x1 = theEquation.getX1();
            double x2 = theEquation.getX2();
            double y1 = this.getYfromX(a, b, c, d, x1);
            double y2 = this.getYfromX(a, b, c, d, x2);
            
            middle.x = x1;
            middle.y = y1;
        }
        
        Line line1 = new Line(start.x, start.y, middle.x, middle.y);
        Line line2 = new Line(middle.x, middle.y, end.x, end.y);
        lines.add(line1);
        lines.add(line2);
        
        return lines;
    }
    
    double getT(double a, double b, double c, double d)
    {
        return (a * a + b * b - c * c - d * d) / (2 * (b - d));
    }
    
    double getYfromX(double a, double b, double c, double d, double x)
    {
        return getT(a, b, c, d) - ((a - c) / (b - d)) * x;
    }
    
    @Override
    public void refresh(double changeX, double changeY, double changeWidth, double changeHeight)
    {
        super.refresh(changeX, changeY, changeWidth, changeHeight);
        
        this.start.refresh(changeX, changeY, changeWidth, changeHeight);
        this.end.refresh(changeX, changeY, changeWidth, changeHeight);
    }
}
