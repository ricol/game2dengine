/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

import au.com.rmit.Game2dEngine.node.Sprite;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class MoveYToAction extends MoveAction
{

    MoveYByAction theMoveYByAction;
    double moveYTo;
    float moveYToDuration;

    public MoveYToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.moveYTo = this.theSprite.getY();
            this.moveYToDuration = 0;
        } else
        {
            bComplete = true;
        }
    }

    public void moveYTo(double y, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.moveYTo = y;
        this.moveYToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theMoveYByAction.clearSprite();
        this.theMoveYByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theMoveYByAction == null)
        {
            theMoveYByAction = new MoveYByAction();
            double tmpY = this.theSprite.getY();
            theMoveYByAction.moveYBy(moveYTo - tmpY, this.moveYToDuration / 1000.0f);
            theMoveYByAction.setSprite(theSprite);
        }

        theMoveYByAction.perform(runningTime);
        if (theMoveYByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
