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
public class VelocityAngleByAction extends VelocityAngleAction
{

    double velocityAngleBy;
    float velocityAngleByDuration;
    double velocityAngleBySpeed;
    double velocityAngleByCurrent;

    public VelocityAngleByAction()
    {
        this.velocityAngleBy = 0;
        this.velocityAngleByDuration = 0;
        this.velocityAngleBySpeed = 0;
        this.velocityAngleByCurrent = 0;
    }

    public void velocityAngleBy(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            bImmediately = true;
        }
        this.velocityAngleBy = x;
        this.velocityAngleByDuration = abs(duration * 1000);
        if (!bImmediately)
        {
            this.velocityAngleBySpeed = x / abs(duration * 1000);
        }
        this.velocityAngleByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            this.theSprite.setVelocityAngle(this.theSprite.getVelocityAngle() + velocityAngleBy);
            bComplete = true;
        }

        if (bComplete)
        {
            return;
        }

        if (abs(this.velocityAngleBy) > Action.MINIMUM)
        {
            double x = this.theSprite.getVelocityAngle();
            double value = this.velocityAngleBySpeed * runningTime;
            this.velocityAngleByCurrent += value;

            if (this.velocityAngleBySpeed > 0)
            {
                if (this.velocityAngleByCurrent > this.velocityAngleBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            } else
            {
                if (this.velocityAngleByCurrent < this.velocityAngleBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            }
            this.theSprite.setVelocityAngle(x);
        } else
        {
            bComplete = true;
        }

    }
}
