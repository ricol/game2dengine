/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry.shape;

import au.com.rmit.Game2dEngine.geometry.Point;

/**
 *
 * @author ricolwang
 */
public class PolygonShape extends ClosureShape
{

    Point data[];

    public PolygonShape(Point data[])
    {
        this.data = data;
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
        return 0;
    }
}
