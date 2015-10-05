/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.math.vector;

import au.com.rmit.Game2dEngine.geometry.Point;
import au.com.rmit.Game2dEngine.math.common.MathConsts;
import au.com.rmit.Game2dEngine.math.equation.QuadraticEquation;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class Vector
{

    public Point start = new Point(0, 0);
    public double x;
    public double y;

    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Point getTheEndPoint()
    {
        Point aPoint = new Point(x + start.x, y + start.y);
        return aPoint;
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

    public Vector getTheNegativeVector()
    {
        Vector C = new Vector(-x, -y);
        return C;
    }

    public double dotProduct(Vector B)
    {
        return x * B.x + y * B.y;
    }

    public double getTheMagnitude()
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
        if (x == 0 && y == 0)
            return new Vector(0, 0);
        if (x == 0)
        {
            if (y > 0)
                return new Vector(1, 0);
            else
                return new Vector(-1, 0);
        }
        if (y == 0)
        {
            if (x > 0)
                return new Vector(0, -1);
            else
                return new Vector(0, 1);
        }

        double tmp = -1 * (x / Math.sqrt(x * x + y * y));
        Vector C = new Vector(-1 * (y / x) * tmp, tmp);
        return C;
    }

    public Vector getPerpendicularUnitVectorClockwise()
    {
        if (x == 0 && y == 0)
            return new Vector(0, 0);
        if (x == 0)
        {
            if (y > 0)
                return new Vector(-1, 0);
            else
                return new Vector(1, 0);
        }
        if (y == 0)
        {
            if (x > 0)
                return new Vector(0, 1);
            else
                return new Vector(0, -1);
        }

        double tmp = x / Math.sqrt(x * x + y * y);
        Vector C = new Vector(-1 * (y / x) * tmp, tmp);
        return C;
    }

    public boolean isPerpendicularTo(Vector B)
    {
        double dot = this.dotProduct(B);
        return abs(dot) <= MathConsts.E;
    }

    public boolean isParalleTo(Vector B)
    {
        double dot = this.dotProduct(B);
        double absThis = this.getTheMagnitude();
        double absB = B.getTheMagnitude();

        return abs((abs(dot) - abs(absThis * absB))) <= MathConsts.E;
    }

    @Override
    public String toString()
    {
        return "Vector[X: " + x + "; Y: " + y + "; Magnitude: " + this.getTheMagnitude() + "]";
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

    public Vector3D getCrossProduct(Vector B)
    {
        Vector3D Result = new Vector3D(0, 0, this.x * B.y - this.y * B.x);
        return Result;
    }

    //magnitude != 0
    public Vector getTheUnitVector()
    {
        Vector C = new Vector(x, y);
        return C.divideByNumber(this.getTheMagnitude());
    }

    //not perpendicular relationship
    public double getCosValueForAngleToVector(Vector B)
    {
        double magnitude = this.getTheMagnitude();
        double targetMagnitude = B.getTheMagnitude();

        if (magnitude > 0 && targetMagnitude > 0)
        {
            return this.dotProduct(B) / (this.getTheMagnitude() * B.getTheMagnitude());
        } else
        {
            return 0;
        }
    }

    //the magnitude of B != 0
    public Vector getProjectVectorOn(Vector B)
    {
        Vector C = B.getTheUnitVector();
        return C.multiplyNumber(this.getTheMagnitude() * this.getCosValueForAngleToVector(B));
    }

    public Vector getVectorRotateByInClockwise(double angle)
    {
        Vector Result = new Vector(0, 0);

        double a = this.x;
        double b = this.y;
        double t = (a * a + b * b) * Math.cos(angle);

        if (abs(a) > MathConsts.E)
        {
            double A = a * a + b * b;
            double B = - 2 * t * b;
            double C = t * t - a * a * t;

            QuadraticEquation aEquation = new QuadraticEquation(A, B, C);
            Result.y = aEquation.getX1();
            Result.x = (t - b * Result.y) / a;
            Vector3D theCrossProduct = Result.getCrossProduct(this);
            if (theCrossProduct.z > 0)
            {
                Result.y = aEquation.getX2();
                Result.x = (t - b * Result.y) / a;
            }
        } else if (abs(b) > MathConsts.E)
        {
            double A = a * a + b * b;
            double B = - 2 * t * a;
            double C = t * t - b * b * t;

            QuadraticEquation aEquation = new QuadraticEquation(A, B, C);
            Result.x = aEquation.getX1();
            Result.y = (t - a * Result.x) / b;
            Vector3D theCrossProduct = Result.getCrossProduct(this);
            if (theCrossProduct.z > 0)
            {
                Result.x = aEquation.getX2();
                Result.y = (t - a * Result.x) / b;
            }
        }

        Vector Unit_Result = Result.getTheUnitVector();
        Result = Unit_Result.multiplyNumber(this.getTheMagnitude());
        return Result;
    }

    public Vector getVectorRotateByInCounterClockwise(double angle)
    {
        Vector Result = new Vector(0, 0);

        double a = this.x;
        double b = this.y;
        double t = (a * a + b * b) * Math.cos(angle);

        if (abs(a) > MathConsts.E)
        {
            double A = a * a + b * b;
            double B = - 2 * t * b;
            double C = t * t - a * a * t;

            QuadraticEquation aEquation = new QuadraticEquation(A, B, C);
            Result.y = aEquation.getX1();
            Result.x = (t - b * Result.y) / a;
            Vector3D theCrossProduct = Result.getCrossProduct(this);
            if (theCrossProduct.z < 0)
            {
                Result.y = aEquation.getX2();
                Result.x = (t - b * Result.y) / a;
            }
        } else if (abs(b) > MathConsts.E)
        {
            double A = a * a + b * b;
            double B = - 2 * t * a;
            double C = t * t - b * b * t;

            QuadraticEquation aEquation = new QuadraticEquation(A, B, C);
            Result.x = aEquation.getX1();
            Result.y = (t - a * Result.x) / b;
            Vector3D theCrossProduct = Result.getCrossProduct(this);
            if (theCrossProduct.z < 0)
            {
                Result.x = aEquation.getX2();
                Result.y = (t - a * Result.y) / b;
            }
        }

        Vector Unit_Result = Result.getTheUnitVector();
        Result = Unit_Result.multiplyNumber(this.getTheMagnitude());

        return Result;
    }

}
