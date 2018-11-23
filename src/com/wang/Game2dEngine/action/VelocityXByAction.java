/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action;

import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class VelocityXByAction extends VelocityAction
{

    double velocityXBy;
    float velocityXByDuration;
    double velocityXBySpeed;
    double velocityXByCurrent;

    public VelocityXByAction()
    {
        this.velocityXBy = 0;
        this.velocityXByDuration = 0;
        this.velocityXBySpeed = 0;
        this.velocityXByCurrent = 0;
    }

    public void velocityXBy(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            bImmediately = true;
        }
        this.velocityXBy = x;
        this.velocityXByDuration = abs(duration * 1000);
        if (!bImmediately)
        {
            this.velocityXBySpeed = x / abs(duration * 1000);
        }
        this.velocityXByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            this.theSprite.setVelocityX(this.theSprite.getVelocityX() + velocityXBy);
            bComplete = true;
        }

        if (bComplete)
        {
            return;
        }

        if (abs(this.velocityXBy) > Action.MINIMUM)
        {
            double x = this.theSprite.getVelocityX();
            double value = this.velocityXBySpeed * runningTime;
            this.velocityXByCurrent += value;

            if (this.velocityXBySpeed > 0)
            {
                if (this.velocityXByCurrent > this.velocityXBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            } else
            {
                if (this.velocityXByCurrent < this.velocityXBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            }
            this.theSprite.setVelocityX(x);
        } else
        {
            bComplete = true;
        }

    }
}
