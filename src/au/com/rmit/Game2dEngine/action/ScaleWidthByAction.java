/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class ScaleWidthByAction extends ScaleAction
{

    double scaleWidthBy;
    float scaleWidthByDuration;
    double scaleWidthBySpeed;
    double scaleWidthByCurrent;

    public ScaleWidthByAction()
    {
        this.scaleWidthBy = 0;
        this.scaleWidthByDuration = 0;
        this.scaleWidthBySpeed = 0;
        this.scaleWidthByCurrent = 0;
    }

    public void scaleWidthBy(double value, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            bImmediately = true;
        }
        this.scaleWidthBy = value;
        this.scaleWidthByDuration = duration;
        if (!bImmediately)
        {
            this.scaleWidthBySpeed = value / abs(duration * 1000);
        }
        this.scaleWidthByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            this.theSprite.setWidth(this.theSprite.getWidth() + scaleWidthBy);
            bComplete = true;
        }

        if (bComplete)
        {
            return;
        }

        if (abs(this.scaleWidthBy) > Action.MINIMUM)
        {
            double width = this.theSprite.getWidth();
            double value = this.scaleWidthBySpeed * runningTime;
            this.scaleWidthByCurrent += value;

            if (this.scaleWidthBy > 0)
            {
                if (this.scaleWidthByCurrent > this.scaleWidthBy)
                {
                    bComplete = true;
                } else
                {
                    width += value;
                }
            } else
            {
                if (this.scaleWidthByCurrent < this.scaleWidthBy)
                {
                    bComplete = true;
                } else
                {
                    width += value;
                }
            }
            this.theSprite.setWidth(width);
        } else
        {
            bComplete = true;
        }

    }
}
