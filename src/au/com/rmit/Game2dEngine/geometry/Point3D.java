/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import au.com.rmit.Game2dEngine.math.common.MathConsts;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/**
 *
 * @author ricolwang
 */
public class Point3D
{

    public double x, y, z;

    public Point3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getDistanceFrom(Point3D B)
    {
        double delX = B.x - x;
        double delY = B.y - y;
        double delZ = B.z - z;
        return sqrt(delX * delX + delY * delY + delZ * delZ);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Point3D)
        {
            Point3D B = (Point3D) obj;
            return abs(this.x - B.x) <= MathConsts.E && abs(this.y - B.y) <= MathConsts.E && abs(this.z - B.z) <= MathConsts.E;
        } else
            return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }

    public static Point3D getZeroPoint()
    {
        Point3D zero = new Point3D(0, 0, 0);
        return zero;
    }
}
