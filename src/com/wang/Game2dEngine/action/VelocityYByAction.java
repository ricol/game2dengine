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
public class VelocityYByAction extends VelocityAction
{

    double velocityYBy;
    float velocityYByDuration;
    double velocityYBySpeed;
    double velocityYByCurrent;

    public VelocityYByAction()
    {
        this.velocityYBy = 0;
        this.velocityYByDuration = 0;
        this.velocityYBySpeed = 0;
        this.velocityYByCurrent = 0;
    }

    public void velocityYBy(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            bImmediately = true;
        }
        this.velocityYBy = x;
        this.velocityYByDuration = abs(duration * 1000);
        if (!bImmediately)
        {
            this.velocityYBySpeed = x / velocityYByDuration;
        }
        this.velocityYByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            this.theSprite.setVelocityY(this.theSprite.getVelocityY() + velocityYBy);
            bComplete = true;
        }

        if (bComplete)
        {
            return;
        }

        if (abs(this.velocityYBy) > Action.MINIMUM)
        {
            double x = this.theSprite.getVelocityY();
            double value = this.velocityYBySpeed * runningTime;
            this.velocityYByCurrent += value;

            if (this.velocityYBySpeed > 0)
            {
                if (this.velocityYByCurrent > this.velocityYBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            } else
            {
                if (this.velocityYByCurrent < this.velocityYBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            }
            this.theSprite.setVelocityY(x);
        } else
        {
            bComplete = true;
        }

    }
}
