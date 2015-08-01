/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.gravity;

import au.com.rmit.Game2dEngine.interfaces.ICopy;

/**
 *
 * @author ricolwang
 */
public class Gravity implements ICopy
{

    public float GX = 9.8f;
    public float GY = 0f;

    public Gravity(float gx, float gy)
    {
        this.GX = gx;
        this.GY = gy;
    }

    @Override
    public void copyContent(Object theObject)
    {
        if (theObject instanceof Gravity)
        {
            Gravity theGravity = (Gravity)theObject;
            theGravity.GX = this.GX;
            theGravity.GY = this.GY;
        }
    }

    @Override
    public Object getACopy()
    {
        final Gravity g = new Gravity(this.GX, this.GY);
        return g;
    }
}
