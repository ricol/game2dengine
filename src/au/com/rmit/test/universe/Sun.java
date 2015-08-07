/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.universe;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.math.CollisionQuadraticEquation;
import au.com.rmit.Game2dEngine.math.Vector;
import au.com.rmit.Game2dEngine.scene.Layer;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.rmit.test.physicengine.WallSprite;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class Sun extends Star implements ActionListener
{

    Timer theTimer = new Timer(100, this);

    @Override
    public void onAddToLayer(Layer theLayer)
    {
        super.onAddToLayer(theLayer); //To change body of generated methods, choose Tools | Templates.

//        theTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int number = abs(theRandom.nextInt()) % 20 + 20;
        double v = 15;

        for (int i = 0; i < number; i++)
        {
            double tmpVelocityX = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * v * sin(i);
            double tmpVelocityY = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * v * cos(i);

            int size = 5;

            Sprite aFire = new Flame();

            aFire.setWidth(size);
            aFire.setHeight(size);
            aFire.setCentreX(this.getCentreX());
            aFire.setCentreY(this.getCentreY());

            aFire.setVelocityX(tmpVelocityX);
            aFire.setVelocityY(tmpVelocityY);
            aFire.setRed(255);
            aFire.setBlue(255);
            aFire.setGreen(255);

            aFire.bDeadIfNoActions = true;
            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 4f);
            aFire.addAction(aAction);

            if (this.theScene != null)
            {
                this.theScene.addSprite(aFire);
            }
        }
    }
    
    @Override
    public boolean collideWith(Sprite target)
    {
        if (target instanceof WallSprite)
            return super.rectangleOverlaps(target);
        else
            return super.circleOverlaps(target);
    }

    @Override
    public void onCollideWith(Sprite target)
    {
        super.onCollideWith(target); //To change body of generated methods, choose Tools | Templates.
        
        System.out.println("Collide...");
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
        }else
            this.processCollision(target);
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
    
}
