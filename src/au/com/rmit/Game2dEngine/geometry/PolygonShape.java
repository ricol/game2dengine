/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author ricolwang
 */
public class PolygonShape extends ClosureShape
{

    ArrayList<Line> sides = new ArrayList<>();

    public PolygonShape()
    {

    }

    public void addSide(Line aLine)
    {
        this.sides.add(aLine);
    }

    public void addSides(ArrayList<Line> lines)
    {
        this.sides.addAll(lines);
    }

    public void removeSide(Line aLine)
    {
        this.sides.remove(aLine);
    }

    public void removeSides(Set<Line> lines)
    {
        this.sides.removeAll(lines);
    }

    public int getSidesCount()
    {
        return this.sides.size();
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
    public double getArea()
    {
        return 0;
    }

    @Override
    public double getCircumference()
    {
        double sum = 0;
        for (Line aLine : this.sides)
            sum += aLine.getLength();

        return sum;
    }

    @Override
    public void refresh(double changeX, double changeY, double changeWidth, double changeHeight)
    {
        super.refresh(changeX, changeY, changeWidth, changeHeight);
        for (Line aLine : this.sides)
            aLine.refresh(changeX, changeY, changeWidth, changeHeight);
    }

    @Override
    public void draw(Graphics2D theGraphicsInTheScene, Color theColor)
    {
        super.draw(theGraphicsInTheScene, theColor);

        for (Line aLine : this.sides)
            aLine.draw(theGraphicsInTheScene, theColor);
    }

    @Override
    public void print(String text)
    {
        System.out.println(text + " - PolygonShape: " + this.sides.size() + " Lines.");
    }
}
