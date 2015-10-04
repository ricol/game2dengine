/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.math.vector;

import au.com.rmit.Game2dEngine.geometry.Point3D;

/**
 *
 * @author ricolwang
 */
public class Vector3D
{

    public Point3D start = new Point3D(0, 0, 0);
    public double x;
    public double y;
    public double z;

    public Vector3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D getTheEndPoint()
    {
        Point3D aPoint = new Point3D(x + start.x, y + start.y, z + start.z);
        return aPoint;
    }

    public Vector3D addVector(Vector3D B)
    {
        Vector3D C = new Vector3D(x, y, z);
        C.x += B.x;
        C.y += B.y;
        C.z += B.z;
        return C;
    }

    public Vector3D subVector(Vector3D B)
    {
        Vector3D C = new Vector3D(x, y, z);
        C.x -= B.x;
        C.y -= B.y;
        C.z -= B.z;
        return C;
    }

    public Vector3D getTheNegativeVector()
    {
        Vector3D C = new Vector3D(-x, -y, -z);
        return C;
    }

    public double getTheMagnitude()
    {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3D multiplyNumber(double number)
    {
        Vector3D C = new Vector3D(x * number, y * number, z * number);
        return C;
    }

    @Override
    public String toString()
    {
        return "Vector3[X: " + x + "; Y: " + y + "; Z: " + z + "; Magnitude: " + this.getTheMagnitude() + "]";
    }

    public void print(String title)
    {
        System.out.println(title + ": " + this);
    }

    //number != 0
    public Vector3D divideByNumber(double number)
    {
        return this.multiplyNumber(1 / number);
    }

    //magnitude != 0
    public Vector3D getTheUnitVector()
    {
        Vector3D C = new Vector3D(x, y, z);
        return C.divideByNumber(this.getTheMagnitude());
    }

}
