/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.physicengine;

import au.com.rmit.Game2dEngine.math.CollisionQuadraticEquation;
import au.com.rmit.Game2dEngine.math.Vector;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.rmit.test.gui.TestCommon;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class CircleSprite extends Sprite
{

    public CircleSprite()
    {
        this.bCollisionDetect = true;
        this.setCollisionCategory(TestCommon.CATEGORY_CIRCLE);
        this.addTargetCollisionCategory(TestCommon.CATEGORY_WALL);

        this.bCollisionArbitrary = true;
        this.bCustomDrawing = true;
//        this.setVelocityAngle(abs(theRandom.nextFloat()) * Math.PI * 2);
    }

    @Override
    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        super.onCustomDraw(theGraphics2D); //To change body of generated methods, choose Tools | Templates.

        AffineTransform old = theGraphics2D.getTransform();

        //rotate the angle
        theGraphics2D.rotate(this.getAngle(), this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        theGraphics2D.setColor(this.getColor());
        theGraphics2D.drawLine(0, (int)(this.getHeight() / 2.0f), (int)this.getWidth() - 1, (int)(this.getHeight() / 2.0f));
        theGraphics2D.drawLine((int)(this.getWidth() / 2.0f), 0, (int)(this.getWidth() / 2.0f), (int)this.getHeight() - 1);
        theGraphics2D.drawArc(0, 0, (int)this.getWidth() - 1, (int)this.getHeight() - 1, 0, 360);
        
        theGraphics2D.setTransform(old);
    }

    @Override
    public void onCollideWith(Sprite target)
    {
        if (target instanceof WallSprite)
        {
            Vector V1 = new Vector(this.getVelocityX(), this.getVelocityY());
            V1.print("BEFORE V");
            System.out.println("Y: " + (this.getY() + this.getHeight()) + " <-> Bottom: " + this.theScene.getHeight());

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

            Vector V2 = new Vector(this.getVelocityX(), this.getVelocityY());
            V2.print("AFTER V");
            System.out.println("Y: " + (this.getY() + this.getHeight()) + " <-> Bottom: " + this.theScene.getHeight());
        } else
            this.processCollision(target);
    }

    @Override
    public boolean collideWith(Sprite target)
    {
        if (target instanceof WallSprite)
            return super.rectangleOverlaps(target);
        else
            return super.circleOverlaps(target);
    }

    public void processCollision(Sprite target)
    {
        Vector AB = new Vector(target.getCentreX() - this.getCentreX(), target.getCentreY() - this.getCentreY());
        if (AB.getMagnitude() <= 0)
            return;

        Vector BC = AB.getPerpendicularUnitVectorClockwise();

        Vector V_A = new Vector(this.getVelocityX(), this.getVelocityY());

        double cosBC_V_A = BC.getCosValueForAngleToVector(V_A);
        if (cosBC_V_A < 0)
            BC = AB.getPerpendicularUnitVectorCounterClockwise();

        Vector UNIT_AB = AB.getTheUnitVector();
        Vector V_A_AB = V_A.getProjectVectorOn(UNIT_AB);

        Vector V_B = new Vector(target.getVelocityX(), target.getVelocityY());
        Vector V_B_AB = V_B.getProjectVectorOn(UNIT_AB);

        double absV_A_AB = V_A_AB.getMagnitude();

        if (V_A.getCosValueForAngleToVector(AB) < 0)
            absV_A_AB = -absV_A_AB;

        double absV_B_AB = V_B_AB.getMagnitude();

        if (V_B.getCosValueForAngleToVector(AB) < 0)
            absV_B_AB = -absV_B_AB;

        CollisionQuadraticEquation aEquation = new CollisionQuadraticEquation(this.getMass(), target.getMass(), absV_A_AB, absV_B_AB);
        double resultAbsV_A_AB = aEquation.getNewVelocityAlternative();
        double resultAbsV_B_AB = aEquation.getTheOtherObjectVelocityAlternative();

        Vector RESULT_V_A_AB = UNIT_AB.multiplyNumber(resultAbsV_A_AB);
        Vector UNIT_BC = BC.getTheUnitVector();
        Vector V_A_BC = V_A.getProjectVectorOn(UNIT_BC);
        Vector RESULT_V_A = RESULT_V_A_AB.addVector(V_A_BC);

        this.setVelocityX(RESULT_V_A.x);
        this.setVelocityY(RESULT_V_A.y);
        
        this.setVelocityAngle(abs(theRandom.nextFloat()) * Math.PI * 2);

        Vector RESULT_V_B_AB = UNIT_AB.multiplyNumber(resultAbsV_B_AB);
        Vector V_B_BC = V_B.getProjectVectorOn(UNIT_BC);
        Vector RESULT_V_B = RESULT_V_B_AB.addVector(V_B_BC);

        target.setVelocityX(RESULT_V_B.x);
        target.setVelocityY(RESULT_V_B.y);
        
        target.setVelocityAngle(abs(theRandom.nextFloat()) * Math.PI * 2);

        this.setTargetCollisionProcessed(true);
    }

    @Override
    public String toString()
    {
        return "Class: " + this.getClass() + "; identifier: " + this.identifier + "; velocityX: " + this.getVelocityX() + "; velocityY: " + this.getVelocityY();
    }
}
