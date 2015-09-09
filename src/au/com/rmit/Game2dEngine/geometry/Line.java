/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

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
    
    public ArrayList<Line> getSeperateLines(double distance)
    {
        ArrayList<Line> lines = new ArrayList<>();

        double a = start.x, b = start.y, c = end.x, d = end.y;
        
        Line line1 = new Line(start.x, start.y, 0, 0);
        Line line2 = new Line(0, 0, end.x, end.y);
        lines.add(line1);
        lines.add(line2);
        
        return lines;
    }
    
    @Override
    public void refresh(double changeX, double changeY, double changeWidth, double changeHeight)
    {
        super.refresh(changeX, changeY, changeWidth, changeHeight);
        
        this.start.refresh(changeX, changeY, changeWidth, changeHeight);
        this.end.refresh(changeX, changeY, changeWidth, changeHeight);
    }
}
