/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.Shape;

import au.com.rmit.math.geometry.Shape;

/**
 *
 * @author ricolwang
 */
public interface EIShape extends EINode
{

    public Shape getShape();

    public void refresh(double changeX, double changeY, double changeWidth, double changeHeight);
}
