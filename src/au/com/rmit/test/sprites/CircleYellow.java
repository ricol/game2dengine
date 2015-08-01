/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.sprites;

import au.com.rmit.Game2dEngine.math.Vector;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.rmit.test.TestCommon;
import au.com.rmit.test.sprites.WallSprite.WALLTYPE;
import java.awt.Graphics2D;

/**
 *
 * @author ricolwang
 */
public class CircleYellow extends Sprite
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
        } else if (target instanceof CircleBlue)
        {
            Vector AB = new Vector(target.getCentreX() - this.getCentreX(), target.getCentreY() - this.getCentreY());
            Vector UNIT_AB = AB.getTheUnitVector();
            Vector BC = AB.getPerpendicularUnitVectorClockwise();
            
            Vector V_A = new Vector(this.getVelocityX(), this.getVelocityY());
            double cosBC_V_A = BC.getCosAngleForVector(V_A);
            if (cosBC_V_A < 0)
            {
                BC = AB.getPerpendicularUnitVectorCounterClockwise();
            }
            
            Vector UNIT_BC = BC.getTheUnitVector();
            
            Vector V_A_AB = V_A.getProjectVectorOn(UNIT_AB);
            Vector V_A_BC = V_A.getProjectVectorOn(UNIT_BC);
            
            Vector RESULT_V_A = V_A_BC.addVector(V_A_AB.getNegativeVector());

            this.setVelocityX(RESULT_V_A.x);
            this.setVelocityY(RESULT_V_A.y);
            System.out.println("|Velocity|: " + RESULT_V_A.getMagitude());
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
