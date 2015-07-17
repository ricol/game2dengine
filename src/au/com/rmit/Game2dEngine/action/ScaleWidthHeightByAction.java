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
public class ScaleWidthHeightByAction extends ScaleAction
{

    double scaleWidthBy;
    float scaleWidthByDuration;
    double scaleWidthBySpeed;
    double scaleWidthByCurrent;
    boolean scaleWidthByComplete;

    double scaleHeightBy;
    float scaleHeightByDuration;
    double scaleHeightBySpeed;
    double scaleHeightByCurrent;
    boolean scaleHeightByComplete;

    public ScaleWidthHeightByAction()
    {
        this.scaleWidthBy = 0;
        this.scaleWidthByDuration = 0;
        this.scaleWidthBySpeed = 0;
        this.scaleWidthByCurrent = 0;
        this.scaleWidthByComplete = false;

        this.scaleHeightBy = 0;
        this.scaleHeightByDuration = 0;
        this.scaleHeightBySpeed = 0;
        this.scaleHeightByCurrent = 0;
        this.scaleHeightByComplete = false;
    }

    public void scaleWidthBy(double value, float duration)
    {
        if (duration <= 0)
        {
            duration = (float) Action.MINIMUM_DURATION;
        }
        this.scaleWidthBy = value;
        this.scaleWidthByDuration = duration;
        this.scaleWidthBySpeed = value / abs(duration * 1000);
        this.scaleWidthByCurrent = 0;
    }

    public void scaleHeightBy(double value, float duration)
    {
        if (duration <= 0)
        {
            duration = (float) Action.MINIMUM_DURATION;
        }
        this.scaleHeightBy = value;
        this.scaleHeightByDuration = duration;
        this.scaleHeightBySpeed = value / abs(duration * 1000);
        this.scaleHeightByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (!scaleWidthByComplete)
        {

            if (abs(this.scaleWidthBy) > Action.MINIMUM)
            {
                double width = this.theSprite.getWidth();
                double value = this.scaleWidthBySpeed * runningTime;
                this.scaleWidthByCurrent += value;

                if (this.scaleWidthBy > 0)
                {
                    if (this.scaleWidthByCurrent > this.scaleWidthBy)
                    {
                        scaleWidthByComplete = true;
                    } else
                    {
                        width += value;
                    }
                } else
                {
                    if (this.scaleWidthByCurrent < this.scaleWidthBy)
                    {
                        scaleWidthByComplete = true;
                    } else
                    {
                        width += value;
                    }
                }
                this.theSprite.setWidth(width);
            } else
            {
                scaleWidthByComplete = true;
            }

        }

        if (!scaleHeightByComplete)
        {

            if (abs(this.scaleHeightBy) > Action.MINIMUM)
            {
                double height = this.theSprite.getHeight();
                double value = this.scaleHeightBySpeed * runningTime;
                this.scaleHeightByCurrent += value;

                if (this.scaleHeightBy > 0)
                {
                    if (this.scaleHeightByCurrent > this.scaleHeightBy)
                    {
                        scaleHeightByComplete = true;
                    } else
                    {
                        height += value;
                    }
                } else
                {
                    if (this.scaleHeightByCurrent < this.scaleHeightBy)
                    {
                        scaleHeightByComplete = true;
                    } else
                    {
                        height += value;
                    }
                }
                this.theSprite.setHeight(height);
            } else
            {
                scaleHeightByComplete = true;
            }

        }

        if (scaleWidthByComplete && scaleHeightByComplete)
        {
            bComplete = true;
        }
    }
}
