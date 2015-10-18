/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import au.com.rmit.Game2dEngine.math.vector.Vector;
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

    public Line getReverseLine()
    {
        return new Line(end, start);
    }

    public void reverse()
    {
        Point tmp = this.start;
        this.start = this.end;
        this.end = tmp;
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

    public ArrayList<Line> getSpecialLinesClockwise(double distance)
    {
        ArrayList<Line> lines = new ArrayList<>();

        Vector V_AB = new Vector(this.end.x - this.start.x, this.end.y - this.start.y);
        Vector V_PEN = V_AB.getPerpendicularUnitVectorClockwise();
        V_PEN.start = new Point((this.start.x + this.end.x) / 2, (this.start.y + this.end.y) / 2);
        Vector V_RESULT = V_PEN.multiplyNumber(distance);
        V_RESULT.start = V_PEN.start;
        Point result = V_RESULT.getTheEndPoint();

        Line line1 = new Line(this.start.x, this.start.y, result.x, result.y);
        Line line2 = new Line(result.x, result.y, this.end.x, this.end.y);
        lines.add(line1);
        lines.add(line2);

        return lines;
    }

    public ArrayList<Line> getSpecialLinesCounterClockwise(double distance)
    {
        ArrayList<Line> lines = new ArrayList<>();

        Vector V_AB = new Vector(this.end.x - this.start.x, this.end.y - this.start.y);
        Vector V_PEN = V_AB.getPerpendicularUnitVectorCounterClockwise();
        V_PEN.start = new Point((this.start.x + this.end.x) / 2, (this.start.y + this.end.y) / 2);
        Vector V_RESULT = V_PEN.multiplyNumber(distance);
        V_RESULT.start = V_PEN.start;
        Point result = V_RESULT.getTheEndPoint();

        Line line1 = new Line(this.start.x, this.start.y, result.x, result.y);
        Line line2 = new Line(result.x, result.y, this.end.x, this.end.y);
        lines.add(line1);
        lines.add(line2);

        return lines;
    }

    public ArrayList<Line> getArrowLines(double r, double angle)
    {
        double l = Math.tan(angle) * this.getLength() / 2.0f;
        ArrayList<Line> lines = this.getSpecialLinesCounterClockwise(l);
        Line theArrowRight = lines.get(lines.size() - 1);
        theArrowRight.reverse();
        lines = this.getSpecialLinesClockwise(l);
        Line theArrowLeft = lines.get(lines.size() - 1);
        theArrowLeft.reverse();

        Point endRight = this.getTheCorrectPoint(theArrowRight, r);
        theArrowRight.end = endRight;

        endRight = this.getTheCorrectPoint(theArrowLeft, r);
        theArrowLeft.end = endRight;

        lines = new ArrayList<>();
        lines.add(theArrowRight);
        lines.add(theArrowLeft);

        return lines;
    }

    Point getTheCorrectPoint(Line aLine, double r)
    {
        Vector V_theArrowRight = new Vector(aLine.end.x - aLine.start.x, aLine.end.y - aLine.start.y);
        V_theArrowRight.start = aLine.start;

        Vector V_theArrowRight_UNIT = V_theArrowRight.getTheUnitVector();
        Vector V_theNewArrowRight = V_theArrowRight_UNIT.multiplyNumber(r);
        V_theNewArrowRight.start = aLine.start;

        return V_theNewArrowRight.getTheEndPoint();
    }

    @Override
    public void refresh(double changeX, double changeY, double changeWidth, double changeHeight)
    {
        super.refresh(changeX, changeY, changeWidth, changeHeight);

        this.start.refresh(changeX, changeY, changeWidth, changeHeight);
        this.end.refresh(changeX, changeY, changeWidth, changeHeight);
    }

    @Override
    public String toString()
    {
        return "(" + start.x + ", " + start.y + ") - (" + end.x + ", " + end.y + ")";
    }
}
