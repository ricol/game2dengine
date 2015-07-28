/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry.shape;

/**
 *
 * @author ricolwang
 */
public class CircleShape extends ClosureShape
{
    double centreX, centreY;
    double radius;
    
    public CircleShape(double centreX, double centreY, double radius)
    {
        this.centreX = centreX;
        this.centreY = centreY;
        this.radius = radius;
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
    public double getCircumference()
    {
        return Math.PI * this.radius * 2;
    }
    
    @Override
    public double getArea()
    {
        return Math.PI * this.radius * this.radius;
    }
}
