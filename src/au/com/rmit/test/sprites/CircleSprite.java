/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.sprites;

import au.com.rmit.Game2dEngine.math.CollisionQuadraticEquation;
import au.com.rmit.Game2dEngine.math.Vector;
import au.com.rmit.Game2dEngine.sprite.Sprite;

/**
 *
 * @author ricolwang
 */
public class CircleSprite extends Sprite
{

    public void processCollision(Sprite target)
    {
        Vector AB = new Vector(target.getCentreX() - this.getCentreX(), target.getCentreY() - this.getCentreY());
        if (AB.getMagnitude() <= 0)
        {
            return;
        }

        Vector BC = AB.getPerpendicularUnitVectorClockwise();

        Vector V_A = new Vector(this.getVelocityX(), this.getVelocityY());

        double cosBC_V_A = BC.getCosValueForAngleToVector(V_A);
        if (cosBC_V_A < 0)
        {
            BC = AB.getPerpendicularUnitVectorCounterClockwise();
        }

        Vector UNIT_AB = AB.getTheUnitVector();
        Vector V_A_AB = V_A.getProjectVectorOn(UNIT_AB);
        V_A_AB.print("V_A_AB");

        Vector V_B = new Vector(target.getVelocityX(), target.getVelocityY());
        Vector V_B_AB = V_B.getProjectVectorOn(UNIT_AB);
        V_B_AB.print("V_B_AB");

        double absV_A_AB = V_A_AB.getMagnitude();

        if (V_A.getCosValueForAngleToVector(AB) < 0)
        {
            absV_A_AB = -absV_A_AB;
        }
        double absV_B_AB = V_B_AB.getMagnitude();

        if (V_B.getCosValueForAngleToVector(AB) < 0)
        {
            absV_B_AB = -absV_B_AB;
        }

        CollisionQuadraticEquation aEquation = new CollisionQuadraticEquation(this.getMass(), target.getMass(), absV_A_AB, absV_B_AB);
        double resultAbsV_A_AB = aEquation.getNewVelocityAlternative();
        Vector RESULT_V_A_AB = UNIT_AB.multiplyNumber(resultAbsV_A_AB);

        Vector UNIT_BC = BC.getTheUnitVector();

        Vector V_A_BC = V_A.getProjectVectorOn(UNIT_BC);
        Vector RESULT_V_A = RESULT_V_A_AB.addVector(V_A_BC);
        
        RESULT_V_A.print("RESULT_V_A");
            
        this.setVelocityX(RESULT_V_A.x);
        this.setVelocityY(RESULT_V_A.y);
    }
}
