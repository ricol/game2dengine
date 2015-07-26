/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test;

import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.rmit.test.WallSprite.WALLTYPE;
import java.awt.Graphics2D;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class CircleSprite extends Sprite
{
    public CircleSprite()
    {
        this.setRed(abs(theRandom.nextInt()) % 255);
        this.setGreen(abs(theRandom.nextInt()) % 255);
        this.setBlue(abs(theRandom.nextInt()) % 255);
        this.setWidth(abs(theRandom.nextInt()) % 100 + 100);
        this.setHeight(this.getWidth());
        
        this.bCustomDrawing = true;
        this.bCollisionDetect = true;
        this.collisionCategory = 1;
        this.collisionTargetCategory = 0;
    }

    @Override
    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        super.onCustomDraw(theGraphics2D); //To change body of generated methods, choose Tools | Templates.
        
        theGraphics2D.setColor(this.getColor());
        theGraphics2D.fillArc(0, 0, (int)this.getWidth(), (int)this.getHeight(), 0, 360);
    }

    @Override
    public void onCollideWith(Sprite target)
    {
        if (target instanceof WallSprite)
        {
            WallSprite aWall = (WallSprite)target;
            if (aWall.wallType == WALLTYPE.LEFT)
            {
                this.setVelocityX(-this.getVelocityX());
            }else if (aWall.wallType == WALLTYPE.RIGHT)
            {
                this.setVelocityX(-this.getVelocityX());
            }else if (aWall.wallType == WALLTYPE.TOP)
            {
                this.setVelocityY(-this.getVelocityY());
            }else if (aWall.wallType == WALLTYPE.BOTTOM)
            {
                this.setVelocityY(-this.getVelocityY());
            }
        }
    }
    
}
