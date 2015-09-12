/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import au.com.rmit.Game2dEngine.math.QuadraticEquation;
import au.com.rmit.Game2dEngine.math.Vector;
import java.awt.Color;
import java.awt.Graphics2D;
import static java.lang.Math.abs;
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

    public ArrayList<Line> getSeperateLinesClock(double distance)
    {
        ArrayList<Line> lines = new ArrayList<>();

        double a = start.x, b = start.y, c = end.x, d = end.y;

        Point middle = new Point((a + c) / 2, (b + d) / 2);
        if (abs(b - d) > 0.01)
        {
            double t = getT1(a, b, c, d);
            double A = (1 + ((a - c) / (b - d)) * ((a - c) / (b - d)));
            double B = ((b + d) * (a - c) / (b - d) - (a + c) - 2 * t * (a - c) / (b - d));
            double C = ((a + c) / 2) * ((a + c) / 2) + ((b + d) / 2) * ((b + d) / 2) + t * t - (b + d) * t - distance * distance;
            QuadraticEquation theEquation = new QuadraticEquation(A, B, C);

            double x, y;

            x = theEquation.getX1();
            y = this.getYfromX(a, b, c, d, x);

            double k = (c - a) * (y - b) - (x - a) * (d - b);
            if (k < 0)
            {
                x = theEquation.getX2();
                y = this.getYfromX(a, b, c, d, x);
            }

            middle.x = x;
            middle.y = y;
        } else
        {
            double t = getT2(a, b, c, d);
            double A = (1 + ((b - d) / (a - c)) * ((b - d) / (a - c)));
            double B = ((a + c) * (b - d) / (a - c) - (b + d) - 2 * t * (b - d) / (a - c));
            double C = ((a + c) / 2) * ((a + c) / 2) + ((b + d) / 2) * ((b + d) / 2) + t * t - (a + c) * t - distance * distance;
            QuadraticEquation theEquation = new QuadraticEquation(A, B, C);

            double x, y;

            y = theEquation.getX1();
            x = this.getXfromY(a, b, c, d, y);

            double k = (c - a) * (y - b) - (x - a) * (d - b);
            if (k < 0)
            {
                y = theEquation.getX2();
                x = this.getXfromY(a, b, c, d, y);
            }

            middle.x = x;
            middle.y = y;
        }

        Line line1 = new Line(start.x, start.y, middle.x, middle.y);
        Line line2 = new Line(middle.x, middle.y, end.x, end.y);
        lines.add(line1);
        lines.add(line2);

        return lines;
    }

    public ArrayList<Line> getSeperateLinesCounterClock(double distance)
    {
        ArrayList<Line> lines = new ArrayList<>();

        double a = start.x, b = start.y, c = end.x, d = end.y;

        Point middle = new Point((a + c) / 2, (b + d) / 2);
        if (abs(b - d) > 0.01)
        {
            double t = getT1(a, b, c, d);
            double A = (1 + ((a - c) / (b - d)) * ((a - c) / (b - d)));
            double B = ((b + d) * (a - c) / (b - d) - (a + c) - 2 * t * (a - c) / (b - d));
            double C = ((a + c) / 2) * ((a + c) / 2) + ((b + d) / 2) * ((b + d) / 2) + t * t - (b + d) * t - distance * distance;
            QuadraticEquation theEquation = new QuadraticEquation(A, B, C);

            double x, y;

            x = theEquation.getX1();
            y = this.getYfromX(a, b, c, d, x);

            double k = (c - a) * (y - b) - (x - a) * (d - b);
            if (k > 0)
            {
                x = theEquation.getX2();
                y = this.getYfromX(a, b, c, d, x);
            }

            middle.x = x;
            middle.y = y;
        } else
        {
            double t = getT2(a, b, c, d);
            double A = (1 + ((b - d) / (a - c)) * ((b - d) / (a - c)));
            double B = ((a + c) * (b - d) / (a - c) - (b + d) - 2 * t * (b - d) / (a - c));
            double C = ((a + c) / 2) * ((a + c) / 2) + ((b + d) / 2) * ((b + d) / 2) + t * t - (a + c) * t - distance * distance;
            QuadraticEquation theEquation = new QuadraticEquation(A, B, C);

            double x, y;

            y = theEquation.getX1();
            x = this.getXfromY(a, b, c, d, y);

            double k = (c - a) * (y - b) - (x - a) * (d - b);
            if (k > 0)
            {
                y = theEquation.getX2();
                x = this.getXfromY(a, b, c, d, y);
            }

            middle.x = x;
            middle.y = y;
        }

        Line line1 = new Line(start.x, start.y, middle.x, middle.y);
        Line line2 = new Line(middle.x, middle.y, end.x, end.y);
        lines.add(line1);
        lines.add(line2);

        return lines;
    }

    double getT1(double a, double b, double c, double d)
    {
        return (a * a + b * b - c * c - d * d) / (2 * (b - d));
    }

    double getT2(double a, double b, double c, double d)
    {
        return (a * a + b * b - c * c - d * d) / (2 * (a - c));
    }

    double getYfromX(double a, double b, double c, double d, double x)
    {
        return getT1(a, b, c, d) - ((a - c) / (b - d)) * x;
    }

    double getXfromY(double a, double b, double c, double d, double y)
    {
        return getT2(a, b, c, d) - ((b - d) / (a - c)) * y;
    }

    public ArrayList<Line> getArrowLines(double r, double angle)
    {
        double l = Math.tan(angle) * this.getLength() / 2.0f;
        ArrayList<Line> lines = this.getSeperateLinesCounterClock(l);
        Line theArrowRight = lines.get(lines.size() - 1);
        theArrowRight.reverse();
        lines = this.getSeperateLinesClock(l);
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

    public Point getTheCorrectPoint(Line aLine, double r)
    {
        Vector V_theArrowRight = new Vector(aLine.end.x - aLine.start.x, aLine.end.y - aLine.start.y);
        V_theArrowRight.start = aLine.start;

        Vector V_theArrowRight_UNIT = V_theArrowRight.getTheUnitVector();
        Vector V_theNewArrowRight = V_theArrowRight_UNIT.multiplyNumber(r);
        V_theNewArrowRight.start = aLine.start;

        return V_theNewArrowRight.getEndPoint();
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
