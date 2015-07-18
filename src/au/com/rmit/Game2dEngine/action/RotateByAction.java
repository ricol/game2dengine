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
public class RotateByAction extends RotateAction
{

    double rotateBy;
    double rotateByDuration;
    double rotateBySpeed;
    double rotateByCurrent;

    public void rotateBy(double angle, double duration)
    {
        if (duration <= 0)
        {
            duration = (float) Action.MINIMUM_DURATION;
        }
        this.rotateBy = angle;
        this.rotateByDuration = duration;
        this.rotateBySpeed = angle / abs(duration * 1000);
        this.rotateByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (abs(this.rotateBy) > Action.MINIMUM)
        {
            double angle = this.theSprite.getAngle();
            double value = this.rotateBySpeed * runningTime;
            this.rotateByCurrent += value;

            if (this.rotateBySpeed > 0)
            {
                if (this.rotateByCurrent > this.rotateBy)
                {
                    bComplete = true;
                } else
                {
                    angle += value;
                }
            } else
            {
                if (this.rotateByCurrent < this.rotateBy)
                {
                    bComplete = true;
                } else
                {
                    angle += value;
                }
            }
            this.theSprite.setAngle(angle);
        } else
        {
            bComplete = true;
        }

    }
}
