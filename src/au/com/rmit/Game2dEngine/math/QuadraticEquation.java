/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.math;

/**
 *
 * @author ricolwang
 */
public class QuadraticEquation
{

    public double a = 1;
    public double b = 0;
    public double c = 0;

    public QuadraticEquation(double a, double b, double c)
    {
        if (a != 0)
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    @Override
    public String toString()
    {
        return a + " * X * X + " + b + " * X + " + c + " = 0";
    }

    public double getX1()
    {
        double mark = Math.sqrt(b * b - 4 * a * c);
        double x1 = (-b + mark) / (2 * a);
        return x1;
    }

    public double getX2()
    {
        double mark = Math.sqrt(b * b - 4 * a * c);
        double x2 = (-b - mark) / (2 * a);
        return x2;
    }
}
