/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action;

import com.wang.Game2dEngine.sprite.Sprite;

import static java.lang.Math.abs;

/**
 * @author ricolwang
 */
public class AccelerationYToAction extends AccelerationAction
{

    AccelerationYByAction theAccelerationYByAction;

    double accelerationYTo;
    float accelerationYToDuration;

    public AccelerationYToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.accelerationYTo = this.theSprite.getAccelarationY();
            this.accelerationYToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    public void accelerationYTo(double y, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.accelerationYTo = y;
        this.accelerationYToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theAccelerationYByAction.clearSprite();
        this.theAccelerationYByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theAccelerationYByAction == null)
        {
            theAccelerationYByAction = new AccelerationYByAction();
            double tmpY = this.theSprite.getAccelarationY();
            theAccelerationYByAction.accelerationYBy(accelerationYTo - tmpY, this.accelerationYToDuration / 1000.0f);
            theAccelerationYByAction.setSprite(theSprite);
        }

        theAccelerationYByAction.perform(runningTime);
        if (theAccelerationYByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
