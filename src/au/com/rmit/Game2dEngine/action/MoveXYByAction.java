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
public class MoveXYByAction extends MoveAction
{

    double moveXBy;
    float moveXByDuration;
    double moveXBySpeed;
    double moveXByCurrent;
    boolean moveXByComplete;

    double moveYBy;
    float moveYByDuration;
    double moveYBySpeed;
    double moveYByCurrent;
    boolean moveYByComplete;

    public MoveXYByAction()
    {
        this.moveXBy = 0;
        this.moveXByDuration = 0;
        this.moveXBySpeed = 0;
        this.moveXByCurrent = 0;
        this.moveXByComplete = false;

        this.moveYBy = 0;
        this.moveYByDuration = 0;
        this.moveYBySpeed = 0;
        this.moveYByCurrent = 0;
        this.moveYByComplete = false;
    }

    //duratio in seconds
    public void moveXBy(double x, float duration)
    {
        if (duration <= 0) duration = (float)Action.MINIMUM_DURATION;
        this.moveXBy = x;
        this.moveXByDuration = abs(duration * 1000);
        this.moveXBySpeed = x / abs(duration * 1000);
        this.moveXByCurrent = 0;
    }

    //duratio in seconds
    public void moveYBy(double y, float duration)
    {
        if (duration <= 0) duration = (float)Action.MINIMUM_DURATION;
        this.moveYBy = y;
        this.moveYByDuration = abs(duration * 1000);
        this.moveYBySpeed = y / abs(duration * 1000);
        this.moveYByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete) return;
        
        if (!moveXByComplete)
        {

            if (abs(this.moveXBy) > Action.MINIMUM)
            {
                double x = this.theSprite.getX();
                double value = this.moveXBySpeed * runningTime;
                this.moveXByCurrent += value;

                if (this.moveXBySpeed > 0)
                {
                    if (this.moveXByCurrent > this.moveXBy)
                    {
                        moveXByComplete = true;
                    } else
                    {
                        x += value;
                    }
                } else
                {
                    if (this.moveXByCurrent < this.moveXBy)
                    {
                        moveXByComplete = true;
                    } else
                    {
                        x += value;
                    }
                }
                this.theSprite.setX(x);
            } else
            {
                moveXByComplete = true;
            }

        }

        if (!moveYByComplete)
        {
            if (abs(this.moveYBy) > Action.MINIMUM)
            {
                double y = this.theSprite.getY();
                double value = this.moveYBySpeed * runningTime;
                this.moveYByCurrent += value;

                if (this.moveYBySpeed > 0)
                {
                    if (this.moveYByCurrent > this.moveYBy)
                    {
                        moveYByComplete = true;
                    } else
                    {
                        y += value;
                    }
                } else
                {
                    if (this.moveYByCurrent < this.moveYBy)
                    {
                        moveYByComplete = true;
                    } else
                    {
                        y += value;
                    }
                }

                this.theSprite.setY(y);
            } else
            {
                moveYByComplete = true;
            }

        }

        if (moveXByComplete && moveYByComplete)
        {
            bComplete = true;
        }
    }
}
