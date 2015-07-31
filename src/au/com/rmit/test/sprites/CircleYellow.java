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
                this.setVelocityX(-this.getVelocityX() + 100);
            } else if (aWall.wallType == WALLTYPE.RIGHT)
            {
                this.setVelocityX(-this.getVelocityX() - 100);
            } else if (aWall.wallType == WALLTYPE.TOP)
            {
                this.setVelocityY(-this.getVelocityY() + 100);
            } else if (aWall.wallType == WALLTYPE.BOTTOM)
            {
                this.setVelocityY(-this.getVelocityY() - 100);
            }
        } else if (target instanceof CircleBlue)
        {
//            {
//                AlphaToAction aAction = new AlphaToAction(this);
//                aAction.alphaTo(0, 0.1f);
//                Set<Action> aSet = new HashSet<>();
//                aSet.add(aAction);
//                this.enQueueActions(aSet);
//            }
//            {
//                AlphaToAction aAction = new AlphaToAction(this);
//                aAction.alphaTo(1, 0.1f);
//                Set<Action> aSet = new HashSet<>();
//                aSet.add(aAction);
//                this.enQueueActions(aSet);
//            }

            Vector V_A = new Vector(this.getVelocityX(), this.getVelocityY());
            Vector Unit_V_A = V_A.getTheUnitVector();
            Vector AB = new Vector(target.getCentreX() - this.getCentreX(), target.getCentreY() - this.getCentreY());
            Vector Unit_AB = AB.getTheUnitVector();
            Vector BC = Unit_V_A.subVector(Unit_AB);
            Vector Unit_BC = BC.getTheUnitVector();
            Vector V_A_AB = V_A.getProjectVectorOn(Unit_AB);
            Vector V_A_BC = V_A.getProjectVectorOn(Unit_BC);
            Vector New_V_A = V_A_BC.addVector(V_A_AB.getNegativeVector());

            this.setVelocityX(New_V_A.x);
            this.setVelocityY(New_V_A.y);
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
