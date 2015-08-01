/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.sprites;

import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.rmit.test.TestCommon;
import au.com.rmit.test.sprites.WallSprite.WALLTYPE;
import java.awt.Graphics2D;

/**
 *
 * @author ricolwang
 */
public class CircleYellow extends CircleSprite
{

    public CircleYellow()
    {
        this.setRed(255);
        this.setGreen(255);
        this.setWidth(50);
        this.setHeight(this.getWidth());

        this.bCustomDrawing = true;
        this.bCollisionDetect = true;
        this.bDrawCircle = true;
        this.identifier = "CircleYellow";

        this.setCollisionCategory(TestCommon.CATEGORY_CIRCLE_RED);

        this.addTargetCollisionCategory(TestCommon.CATEGORY_WALL);
        this.addTargetCollisionCategory(TestCommon.CATEGORY_CIRCLE_BLUE);
    }

    @Override
    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        super.onCustomDraw(theGraphics2D); //To change body of generated methods, choose Tools | Templates.

        theGraphics2D.setColor(this.getColor());
        theGraphics2D.fillArc(0, 0, (int) this.getWidth(), (int) this.getHeight(), 0, 360);
    }

    @Override
    public void onCollideWith(Sprite target)
    {
        if (target instanceof WallSprite)
        {
            System.out.println(this.identifier + " onCollideWith before: " + this.getVelocityX());
            WallSprite aWall = (WallSprite) target;
            if (aWall.wallType == WALLTYPE.LEFT)
            {
                this.setVelocityX(-this.getVelocityX());
            } else if (aWall.wallType == WALLTYPE.RIGHT)
            {
                this.setVelocityX(-this.getVelocityX());
            } else if (aWall.wallType == WALLTYPE.TOP)
            {
                this.setVelocityY(-this.getVelocityY());
            } else if (aWall.wallType == WALLTYPE.BOTTOM)
            {
                this.setVelocityY(-this.getVelocityY());
            }
            System.out.println("onCollideWith after: " + this.getVelocityX());
            
        } else if (target instanceof CircleBlue)
        {
            this.processCollision(target);
        }
    }

    @Override
    public boolean collideWith(Sprite target)
    {
        if (target instanceof WallSprite)
        {
            return super.rectangleOverlaps(target);
        } else
        {
            return super.circleOverlaps(target);
        }
    }

}
