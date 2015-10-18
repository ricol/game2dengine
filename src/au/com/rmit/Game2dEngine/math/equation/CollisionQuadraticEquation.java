/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.math.equation;

/**
 *
 * @author ricolwang
 */
public class CollisionQuadraticEquation extends QuadraticEquation
{

    private final double mass1, mass2, v1, v2;

    public CollisionQuadraticEquation(double mass1, double mass2, double v1, double v2)
    {
        super(1, 0, 0);
        this.mass1 = mass1;
        this.mass2 = mass2;
        this.v1 = v1;
        this.v2 = v2;

        a = mass1 + mass2;
        b = -2 * (mass1 * v1 + mass2 * v2);
        c = (mass1 - mass2) * v1 * v1 + 2 * mass2 * v1 * v2;
    }

    public double getNewVelocity()
    {
        return this.getX1();
    }

    public double getNewVelocityAlternative()
    {
        return this.getX2();
    }

    public double getTheOtherObjectVelocity()
    {
        return (mass1 * v1 + mass2 * v2 - mass1 * this.getX1()) / mass2;
    }

    public double getTheOtherObjectVelocityAlternative()
    {
        return (mass1 * v1 + mass2 * v2 - mass1 * this.getX2()) / mass2;
    }
}
