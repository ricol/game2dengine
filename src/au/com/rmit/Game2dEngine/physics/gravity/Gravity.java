/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.physics.gravity;

/**
 *
 * @author ricolwang
 */
public class Gravity
{

    public double GX = 9.8f;
    public double GY = 0f;

    public Gravity(double gx, double gy)
    {
        this.GX = gx;
        this.GY = gy;
    }

}
