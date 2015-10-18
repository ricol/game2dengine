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
import java.util.ArrayList;

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
    
    public static Vector getTheZeroVector()
    {
        return new Vector(0, 0);
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
        ArrayList<Vector> results = this.getVectorsByRotateAngle(angle);
        for (Vector aVector : results)
        {
            Vector3D V_CROSS_PRODUCT = this.getCrossProduct(aVector);
            if (V_CROSS_PRODUCT.z < 0) return aVector;
        }
        
        return Vector.getTheZeroVector();
    }

    public Vector getVectorRotateByInCounterClockwise(double angle)
    {
        ArrayList<Vector> results = this.getVectorsByRotateAngle(angle);
        for (Vector aVector : results)
        {
            Vector3D V_CROSS_PRODUCT = this.getCrossProduct(aVector);
            if (V_CROSS_PRODUCT.z > 0) return aVector;
        }
        
        return Vector.getTheZeroVector();
    }

    public Vector getVectorAsProjectForAngelInCloseWise(double angel)
    {
        double a = this.x;
        double b = this.y;
        double cos = Math.cos(angel);
        double t = Math.sqrt((a * a + b * b) / (cos * cos) - a * a);

        Vector result = new Vector(this.x, t);
        Vector3D V_CrossProduct = this.getCrossProduct(result);
        if (V_CrossProduct.z > 0)
            result = new Vector(this.x, -t);

        return result;
    }

    public Vector getVectorAsProjectForAngelInCounterCloseWise(double angel)
    {
        double a = this.x;
        double b = this.y;
        double cos = Math.cos(angel);
        double t = Math.sqrt((a * a + b * b) / (cos * cos) - a * a);

        Vector result = new Vector(this.x, t);
        Vector3D V_CrossProduct = this.getCrossProduct(result);
        if (V_CrossProduct.z < 0)
            result = new Vector(this.x, -t);

        return result;
    }

    public ArrayList<Vector> getVectorsByRotateAngle(double angle)
    {
        ArrayList<Vector> results = new ArrayList<>();

        Vector UNIT_THIS = this.getTheUnitVector();
        double a = UNIT_THIS.x;
        double b = UNIT_THIS.y;
        double cos = Math.cos(angle);

        if (abs(a) > MathConsts.E)
        {
            double A = a * a + b * b;
            double B = -2 * b * cos;
            double C = cos * cos - a * a;

            QuadraticEquation aEquation = new QuadraticEquation(A, B, C);
            double y1 = aEquation.getX1();
            double x1 = (cos - b * y1) / a;
            Vector UNIT_TARGET = new Vector(x1, y1);
            results.add(UNIT_TARGET.multiplyNumber(this.getTheMagnitude()));
            y1 = aEquation.getX2();
            x1 = (cos - b * y1) / a;
            UNIT_TARGET = new Vector(x1, y1);
            results.add(UNIT_TARGET.multiplyNumber(this.getTheMagnitude()));
        } else if (abs(b) > MathConsts.E)
        {
            double A = a * a + b * b;
            double B = -2 * a * cos;
            double C = cos * cos - b * b;

            QuadraticEquation aEquation = new QuadraticEquation(A, B, C);
            double x1 = aEquation.getX1();
            double y1 = (cos - a * x1) / b;
            Vector UNIT_TARGET = new Vector(x1, y1);
            results.add(UNIT_TARGET.multiplyNumber(this.getTheMagnitude()));
            x1 = aEquation.getX2();
            y1 = (cos - a * x1) / b;
            UNIT_TARGET = new Vector(x1, y1);
            results.add(UNIT_TARGET.multiplyNumber(this.getTheMagnitude()));
        }

        return results;
    }
}
