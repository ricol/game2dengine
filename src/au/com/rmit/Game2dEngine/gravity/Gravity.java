/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.gravity;

/**
 *
 * @author ricolwang
 */
public class Gravity
{

    public float GX = 9.8f;
    public float GY = 0f;

    public Gravity(float gx, float gy)
    {
        this.GX = gx;
        this.GY = gy;
    }
}
