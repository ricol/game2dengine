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
public class MoveXYToAction extends MoveAction
{

    MoveXYByAction theMoveXYByAction;

    double moveXTo;
    float moveXToDuration;

    double moveYTo;
    float moveYToDuration;

    public MoveXYToAction(MovingSprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.moveXTo = this.theSprite.getX();
            this.moveXToDuration = 0;

            this.moveYTo = this.theSprite.getY();
            this.moveYToDuration = 0;
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
            duration = (float) Action.MINIMUM_DURATION;
        }
        this.moveXTo = x;
        this.moveXToDuration = abs(duration * 1000);
    }

    //duratio in seconds
    public void moveYTo(double y, float duration)
    {
        if (duration <= 0)
        {
            duration = (float) Action.MINIMUM_DURATION;
        }
        this.moveYTo = y;
        this.moveYToDuration = abs(duration * 1000);
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theMoveXYByAction == null)
        {
            theMoveXYByAction = new MoveXYByAction();
            double tmpX = this.theSprite.getX();
            double tmpY = this.theSprite.getY();
            theMoveXYByAction.moveXBy(moveXTo - tmpX, this.moveXToDuration / 1000.0f);
            theMoveXYByAction.moveYBy(moveYTo - tmpY, this.moveYToDuration / 1000.0f);
            theMoveXYByAction.setSprite(theSprite);
        }

        theMoveXYByAction.perform(runningTime);
        if (theMoveXYByAction.bComplete)
        {
            bComplete = true;
        }
    }

    @Override
    public void clearSprite()
    {
        this.theMoveXYByAction.clearSprite();
        this.theMoveXYByAction = null;
        this.theSprite = null;
    }
}
