/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action;

import com.wang.Game2dEngine.sprite.Sprite;

import static java.lang.Math.abs;

/**
 * @author Philology
 */
public class VelocityYToAction extends VelocityAction
{

    VelocityYByAction theVelocityYByAction;

    double velocityYTo;
    float velocityYToDuration;

    public VelocityYToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.velocityYTo = this.theSprite.getVelocityY();
            this.velocityYToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    public void velocityYTo(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.velocityYTo = x;
        this.velocityYToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theVelocityYByAction.clearSprite();
        this.theVelocityYByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theVelocityYByAction == null)
        {
            theVelocityYByAction = new VelocityYByAction();
            double tmpY = this.theSprite.getVelocityY();
            theVelocityYByAction.velocityYBy(velocityYTo - tmpY, this.velocityYToDuration / 1000.0f);
            theVelocityYByAction.setSprite(theSprite);
        }

        theVelocityYByAction.perform(runningTime);
        if (theVelocityYByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
