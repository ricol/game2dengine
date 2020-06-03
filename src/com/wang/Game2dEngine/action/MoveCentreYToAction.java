/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action;

import com.wang.Game2dEngine.sprite.Sprite;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class MoveCentreYToAction extends MoveAction
{

    MoveYToAction theMoveYToAction;

    double moveCentreYTo;
    float moveCentreYToDuration;

    public MoveCentreYToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.moveCentreYTo = this.theSprite.getCentreY();
            this.moveCentreYToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    public void MoveCentreYTo(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.moveCentreYTo = x;
        this.moveCentreYToDuration = abs(duration * 1000);
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theMoveYToAction == null)
        {
            theMoveYToAction = new MoveYToAction(this.theSprite);
            theMoveYToAction.moveYTo(moveCentreYTo - this.theSprite.getWidth() / 2.0, this.moveCentreYToDuration / 1000.0f);
            theMoveYToAction.setSprite(theSprite);
        }

        theMoveYToAction.perform(runningTime);
        if (theMoveYToAction.bComplete)
        {
            bComplete = true;
        }
    }

    @Override
    public void clearSprite()
    {
        this.theMoveYToAction.clearSprite();
        this.theMoveYToAction = null;
        this.theSprite = null;
    }
}
