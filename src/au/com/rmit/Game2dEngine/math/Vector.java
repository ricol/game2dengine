/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.math;

import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class Vector
{

    public double x;
    public double y;

    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector addVector(Vector B)
    {
        Vector C = new Vector(x, y);
        C.x += B.x;
        C.y += B.y;
        return C;
    }

    public Vector subVector(Vector B)
    {
        Vector C = new Vector(x, y);
        C.x -= B.x;
        C.y -= B.y;
        return C;
    }

    public Vector getNegativeVector()
    {
        Vector C = new Vector(-x, -y);
        return C;
    }

    public double dotProduct(Vector B)
    {
        return x * B.x + y * B.y;
    }

    public double getMagnitude()
    {
        return Math.sqrt(x * x + y * y);
    }

    public Vector multiplyNumber(double number)
    {
        Vector C = new Vector(x * number, y * number);
        return C;
    }

    public Vector getPerpendicularUnitVectorCounterClockwise()
    {
        if (x != 0)
        {
            double tmp = -1 * (x / Math.sqrt(x * x + y * y));
            Vector C = new Vector(-1 * (y / x) * tmp, tmp);
            return C;
        } else if (y != 0)
        {
            double tmp = -1 * (y / Math.sqrt(x * x + y * y));
            Vector C = new Vector(-1 * (x / y) * tmp, tmp);
            return C;
        } else
        {
            return new Vector(0, 0);
        }
    }

    public Vector getPerpendicularUnitVectorClockwise()
    {
        if (x != 0)
        {
            double tmp = x / Math.sqrt(x * x + y * y);
            Vector C = new Vector(-1 * (y / x) * tmp, tmp);
            return C;
        } else if (y != 0)
        {
            double tmp = y / Math.sqrt(x * x + y * y);
            Vector C = new Vector(-1 * (x / y) * tmp, tmp);
            return C;
        } else
        {
            return new Vector(0, 0);
        }
    }

    public boolean isPerpendicularTo(Vector B)
    {
        double dot = this.dotProduct(B);
        return abs(dot) <= MathConsts.E;
    }

    public boolean isParalleTo(Vector B)
    {
        double dot = this.dotProduct(B);
        double absThis = this.getMagnitude();
        double absB = B.getMagnitude();

        return abs((abs(dot) - abs(absThis * absB))) <= MathConsts.E;
    }

    @Override
    public String toString()
    {
        return "Vector[X: " + x + "; Y: " + y + "]";
    }

    public void print(String title)
    {
        System.out.println(title + ": " + this);
    }

    //number != 0
    public Vector divideByNumber(double number)
    {
        return this.multiplyNumber(1 / number);
    }

    //magnitude != 0
    public Vector getTheUnitVector()
    {
        Vector C = new Vector(x, y);
        return C.divideByNumber(this.getMagnitude());
    }

    //not perpendicular relationship
    public double getCosValueForAngleToVector(Vector B)
    {
        double magnitude = this.getMagnitude();
        double targetMagnitude = B.getMagnitude();

        if (magnitude > 0 && targetMagnitude > 0)
        {
            return this.dotProduct(B) / (this.getMagnitude() * B.getMagnitude());
        } else
        {
            return 0;
        }
    }

    //the magnitude of B != 0
    public Vector getProjectVectorOn(Vector B)
    {
        Vector C = B.getTheUnitVector();
        return C.multiplyNumber(this.getMagnitude() * this.getCosValueForAngleToVector(B));
    }

}
