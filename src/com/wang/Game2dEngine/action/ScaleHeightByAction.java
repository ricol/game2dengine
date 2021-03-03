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
public class ScaleHeightByAction extends ScaleAction
{

    double scaleHeightBy;
    float scaleHeightByDuration;
    double scaleHeightBySpeed;
    double scaleHeightByCurrent;

    public ScaleHeightByAction()
    {
        this.scaleHeightBy = 0;
        this.scaleHeightByDuration = 0;
        this.scaleHeightBySpeed = 0;
        this.scaleHeightByCurrent = 0;
    }

    public void scaleHeightBy(double value, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            bImmediately = true;
        }
        this.scaleHeightBy = value;
        this.scaleHeightByDuration = duration;
        if (!bImmediately)
        {
            this.scaleHeightBySpeed = value / abs(duration * 1000);
        }
        this.scaleHeightByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            this.theSprite.setHeight(this.theSprite.getHeight() + scaleHeightBy);
            bComplete = true;
        }
        if (bComplete)
        {
            return;
        }

        if (abs(this.scaleHeightBy) > Action.MINIMUM)
        {
            double height = this.theSprite.getHeight();
            double value = this.scaleHeightBySpeed * runningTime;
            this.scaleHeightByCurrent += value;

            if (this.scaleHeightBy > 0)
            {
                if (this.scaleHeightByCurrent > this.scaleHeightBy)
                {
                    bComplete = true;
                } else
                {
                    height += value;
                }
            } else
            {
                if (this.scaleHeightByCurrent < this.scaleHeightBy)
                {
                    bComplete = true;
                } else
                {
                    height += value;
                }
            }
            this.theSprite.setHeight(height);
        } else
        {
            bComplete = true;
        }

    }
}
