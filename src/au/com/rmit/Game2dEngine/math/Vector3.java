/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.math;

import au.com.rmit.Game2dEngine.geometry.Point3;

/**
 *
 * @author ricolwang
 */
public class Vector3
{

    public Point3 start = new Point3(0, 0, 0);
    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3 getEndPoint()
    {
        Point3 aPoint = new Point3(x + start.x, y + start.y, z + start.z);
        return aPoint;
    }

    public Vector3 addVector(Vector3 B)
    {
        Vector3 C = new Vector3(x, y, z);
        C.x += B.x;
        C.y += B.y;
        C.z += B.z;
        return C;
    }

    public Vector3 subVector(Vector3 B)
    {
        Vector3 C = new Vector3(x, y, z);
        C.x -= B.x;
        C.y -= B.y;
        C.z -= B.z;
        return C;
    }

    public Vector3 getNegativeVector()
    {
        Vector3 C = new Vector3(-x, -y, -z);
        return C;
    }

    public double getMagnitude()
    {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3 multiplyNumber(double number)
    {
        Vector3 C = new Vector3(x * number, y * number, z * number);
        return C;
    }

    @Override
    public String toString()
    {
        return "Vector3[X: " + x + "; Y: " + y + "; Z: " + z + "; Magnitude: " + this.getMagnitude() + "]";
    }

    public void print(String title)
    {
        System.out.println(title + ": " + this);
    }

    //number != 0
    public Vector3 divideByNumber(double number)
    {
        return this.multiplyNumber(1 / number);
    }

    //magnitude != 0
    public Vector3 getTheUnitVector()
    {
        Vector3 C = new Vector3(x, y, z);
        return C.divideByNumber(this.getMagnitude());
    }

}
