/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

import au.com.rmit.Game2dEngine.node.MovingSprite;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class MoveXToAction extends MoveAction
{

    MoveXByAction theMoveXByAction;

    double moveXTo;
    float moveXToDuration;

    public MoveXToAction(MovingSprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.moveXTo = this.theSprite.getX();
            this.moveXToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    //duratio in seconds
    public void moveXTo(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.moveXTo = x;
        this.moveXToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theMoveXByAction.clearSprite();
        this.theMoveXByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theMoveXByAction == null)
        {
            theMoveXByAction = new MoveXByAction();
            double tmpX = this.theSprite.getX();
            theMoveXByAction.moveXBy(moveXTo - tmpX, this.moveXToDuration / 1000.0f);
            theMoveXByAction.setSprite(theSprite);
        }

        theMoveXByAction.perform(runningTime);
        if (theMoveXByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
