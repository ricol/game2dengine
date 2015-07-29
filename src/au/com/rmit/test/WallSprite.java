/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test;

import au.com.rmit.Game2dEngine.node.Sprite;

/**
 *
 * @author ricolwang
 */
public class WallSprite extends Sprite
{
    public static enum WALLTYPE {LEFT, RIGHT, TOP, BOTTOM};
    public WALLTYPE wallType = WALLTYPE.LEFT;

    public WallSprite(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
        
        this.bCollisionDetect = true;
        this.collisionCategory = 0;
        this.collisionTargetCategory = 1;
    }

}