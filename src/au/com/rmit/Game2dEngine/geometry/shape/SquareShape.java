/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry.shape;

import au.com.rmit.Game2dEngine.sprite.Sprite;

/**
 *
 * @author ricolwang
 */
public class SquareShape extends Sprite
{

    public SquareShape()
    {
        super(0, 0, 100, 100);
        this.bDrawFrame = true;
    }

    @Override
    public Object getACopy()
    {
        Object aCopy = new SquareShape();
        return aCopy;
    }
}
