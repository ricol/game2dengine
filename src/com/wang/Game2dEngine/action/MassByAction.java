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
public class MassByAction extends MassAction
{

    double massBy;
    float massByDuration;
    double massByBySpeed;
    double massByCurrent;

    public void massBy(double mass, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            this.bImmediately = true;
        }
        this.massBy = mass;
        this.massByDuration = abs(duration * 1000);
        if (!this.bImmediately)
        {
            this.massByBySpeed = mass / abs(duration * 1000);
        }
        this.massByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            this.theSprite.setMass(this.theSprite.getMass() + massBy);
            bComplete = true;
        }

        if (bComplete)
        {
            return;
        }

        if (abs(this.massBy) > Action.MINIMUM)
        {
            double mass = this.theSprite.getMass();
            double value = this.massByBySpeed * runningTime;
            this.massByCurrent += value;

            if (this.massByBySpeed > 0)
            {
                if (this.massByCurrent > this.massBy)
                {
                    bComplete = true;
                } else
                {
                    mass += value;
                }
            } else
            {
                if (this.massByCurrent < this.massBy)
                {
                    bComplete = true;
                } else
                {
                    mass += value;
                }
            }
            this.theSprite.setMass(mass);
        } else
        {
            bComplete = true;
        }

    }
}
