/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action;

import static java.lang.Math.abs;

/**
 * @author ricolwang
 */
public class AccelerationYByAction extends AccelerationAction
{

    double accelerationYBy;
    float accelerationYByDuration;
    double accelerationYBySpeed;
    double accelerationYByCurrent;

    public AccelerationYByAction()
    {
        this.accelerationYBy = 0;
        this.accelerationYByDuration = 0;
        this.accelerationYBySpeed = 0;
        this.accelerationYByCurrent = 0;
    }

    public void accelerationYBy(double y, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            bImmediately = true;
        }
        this.accelerationYBy = y;
        this.accelerationYByDuration = abs(duration * 1000);
        if (!bImmediately)
        {
            this.accelerationYBySpeed = y / abs(duration * 1000);
        }
        this.accelerationYByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            this.theSprite.setAccelarationY(this.theSprite.getAccelarationY() + accelerationYBy);
            bComplete = true;
        }

        if (bComplete)
        {
            return;
        }

        if (abs(this.accelerationYBy) > Action.MINIMUM)
        {
            double x = this.theSprite.getAccelarationY();
            double value = this.accelerationYBySpeed * runningTime;
            this.accelerationYByCurrent += value;

            if (this.accelerationYBySpeed > 0)
            {
                if (this.accelerationYByCurrent > this.accelerationYBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            } else
            {
                if (this.accelerationYByCurrent < this.accelerationYBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            }
            this.theSprite.setAccelarationY(x);
        } else
        {
            bComplete = true;
        }

    }
}
