/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.node.Sprite;
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
        } else if (target instanceof CircleRed)
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
