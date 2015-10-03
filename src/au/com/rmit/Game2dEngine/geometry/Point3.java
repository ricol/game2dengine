/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import static java.lang.Math.sqrt;

/**
 *
 * @author ricolwang
 */
public class Point3
{

    public double x, y, z;

    public Point3(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getDistanceFrom(Point3 B)
    {
        double delX = B.x - x;
        double delY = B.y - y;
        double delZ = B.z - z;
        return sqrt(delX * delX + delY * delY + delZ * delZ);
    }

}
