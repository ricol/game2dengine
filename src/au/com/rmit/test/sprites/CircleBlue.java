/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.sprites;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.math.Vector;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.rmit.test.TestCommon;
import java.awt.Graphics2D;
import static java.lang.Math.abs;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Philology
 */
public class CircleBlue extends Sprite
{

    public CircleBlue()
    {
        this.setBlue(255);
        this.setWidth(abs(theRandom.nextInt()) % 100 + 100);
        this.setHeight(this.getWidth());

        this.bCustomDrawing = true;
        this.bCollisionDetect = true;
        this.bDrawCircle = true;

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
            {
                AlphaToAction aAction = new AlphaToAction(this);
                aAction.alphaTo(0, 0.1f);
                Set<Action> aSet = new HashSet<>();
                aSet.add(aAction);
                this.enQueueActions(aSet);
            }
            {
                AlphaToAction aAction = new AlphaToAction(this);
                aAction.alphaTo(1, 0.1f);
                Set<Action> aSet = new HashSet<>();
                aSet.add(aAction);
                this.enQueueActions(aSet);
            }

            Vector V_A = new Vector(this.getVelocityX(), this.getVelocityY());
            Vector Unit_V_A = V_A.getTheUnitVector();
            Vector AB = new Vector(target.getCentreX() - this.getCentreX(), target.getCentreY() - this.getCentreY());
            Vector Unit_AB = AB.getTheUnitVector();
            double cos = Unit_V_A.getCosAngleForVector(Unit_AB);
            double sin = Math.sqrt(1 - cos * cos);
            Vector New_V_A = V_A.multiplyNumber(sin - cos);
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
