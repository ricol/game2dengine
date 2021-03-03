/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.physics.sprites;

import com.wang.Game2dEngine.Shape.ESpecialRectangleShape;
import com.wang.Game2dEngine.sprite.Sprite;

/**
 * @author ricolwang
 */
public class WallSprite extends Sprite
{

    public WallSprite()
    {
        this(0, 0, 0, 0, 0, 0, 0);
    }

    public static enum WALLTYPE
    {

        LEFT, RIGHT, TOP, BOTTOM
    }

    ;

    public WALLTYPE wallType = WALLTYPE.LEFT;

    public WallSprite(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);

        this.bCollisionDetect = true;
        this.setTheShape(new ESpecialRectangleShape(x, y, width, height));
    }

}
