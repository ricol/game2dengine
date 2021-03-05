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
public class AccelerationXToAction extends AccelerationAction
{
    AccelerationXByAction theAccelerationXByAction;

    double accelerationXTo;
    float accelerationXToDuration;

    public AccelerationXToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.accelerationXTo = this.theSprite.getAccelarationX();
            this.accelerationXToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    public void accelerationXToAction(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.accelerationXTo = x;
        this.accelerationXToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theAccelerationXByAction.clearSprite();
        this.theAccelerationXByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theAccelerationXByAction == null)
        {
            theAccelerationXByAction = new AccelerationXByAction();
            double tmpX = this.theSprite.getAccelarationX();
            theAccelerationXByAction.accelarationXBy(accelerationXTo - tmpX, this.accelerationXToDuration / 1000.0f);
            theAccelerationXByAction.setSprite(theSprite);
        }

        theAccelerationXByAction.perform(runningTime);
        if (theAccelerationXByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
