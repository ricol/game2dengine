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
public class MoveYByAction extends MoveAction
{

    double moveYBy;
    float moveYByDuration;
    double moveYBySpeed;
    double moveYByCurrent;

    public MoveYByAction()
    {
        this.moveYBy = 0;
        this.moveYByDuration = 0;
        this.moveYBySpeed = 0;
        this.moveYByCurrent = 0;
    }

    //duratio in seconds
    public void moveYBy(double y, float duration)
    {
        if (duration <= 0)
        {
            duration = (float) Action.MINIMUM_DURATION;
        }
        this.moveYBy = y;
        this.moveYByDuration = abs(duration * 1000);
        this.moveYBySpeed = y / abs(duration * 1000);
        this.moveYByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (abs(this.moveYBy) > Action.MINIMUM)
        {
            double y = this.theSprite.getY();
            double value = this.moveYBySpeed * runningTime;
            this.moveYByCurrent += value;

            if (this.moveYBySpeed > 0)
            {
                if (this.moveYByCurrent > this.moveYBy)
                {
                    bComplete = true;
                } else
                {
                    y += value;
                }
            } else
            {
                if (this.moveYByCurrent < this.moveYBy)
                {
                    bComplete = true;
                } else
                {
                    y += value;
                }
            }

            this.theSprite.setY(y);
        } else
        {
            bComplete = true;
        }

    }
}