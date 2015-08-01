/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.sprites;

import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.rmit.test.TestCommon;
import java.awt.Graphics2D;

/**
 *
 * @author Philology
 */
public class CircleBlue extends CircleSprite
{

    public CircleBlue()
    {
        this.setBlue(255);
        this.setWidth(100);
        this.setHeight(this.getWidth());
        this.bCustomDrawing = true;
        this.bCollisionDetect = true;
        this.bDrawCircle = true;
        this.identifier = "CircleBlue";
        this.setCollisionCategory(TestCommon.CATEGORY_CIRCLE_BLUE);

        this.addTargetCollisionCategory(TestCommon.CATEGORY_WALL);
        this.addTargetCollisionCategory(TestCommon.CATEGORY_CIRCLE_RED);
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
            WallSprite aWall = (WallSprite) target;
            if (aWall.wallType == WallSprite.WALLTYPE.LEFT)
            {
                this.setVelocityX(-this.getVelocityX());
            } else if (aWall.wallType == WallSprite.WALLTYPE.RIGHT)
            {
                this.setVelocityX(-this.getVelocityX());
            } else if (aWall.wallType == WallSprite.WALLTYPE.TOP)
            {
                this.setVelocityY(-this.getVelocityY());
            } else if (aWall.wallType == WallSprite.WALLTYPE.BOTTOM)
            {
                this.setVelocityY(-this.getVelocityY());
            }
        } else if (target instanceof CircleYellow)
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
