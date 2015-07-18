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
public class MoveXByAction extends MoveAction
{

    double moveXBy;
    float moveXByDuration;
    double moveXBySpeed;
    double moveXByCurrent;

    public MoveXByAction()
    {
        this.moveXBy = 0;
        this.moveXByDuration = 0;
        this.moveXBySpeed = 0;
        this.moveXByCurrent = 0;
    }

    //duratio in seconds
    public void moveXBy(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = (float) Action.MINIMUM_DURATION;
        }
        this.moveXBy = x;
        this.moveXByDuration = abs(duration * 1000);
        this.moveXBySpeed = x / abs(duration * 1000);
        this.moveXByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (abs(this.moveXBy) > Action.MINIMUM)
        {
            double x = this.theSprite.getX();
            double value = this.moveXBySpeed * runningTime;
            this.moveXByCurrent += value;

            if (this.moveXBySpeed > 0)
            {
                if (this.moveXByCurrent > this.moveXBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            } else
            {
                if (this.moveXByCurrent < this.moveXBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            }
            this.theSprite.setX(x);
        } else
        {
            bComplete = true;
        }

    }
}
