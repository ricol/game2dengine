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

    //duratio in seconds
    public void moveXBy(double x, float duration)
    {
        this.moveXBy = x;
        this.moveXByDuration = abs(duration * 1000);
        this.moveXBySpeed = x / abs(duration * 1000);
        this.moveXByCurrent = 0;
    }

    //duratio in seconds
    public void moveYBy(double y, float duration)
    {
        this.moveYBy = y;
        this.moveYByDuration = abs(duration * 1000);
        this.moveYBySpeed = y / abs(duration * 1000);
        this.moveYByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (!moveXByComplete)
        {

            if (abs(abs(this.moveXBy) - Action.MINIMUM) > Action.EQUAL_STANDARD)
            {
                double tmpX = this.theSprite.getX();
                double value = this.moveXBySpeed * runningTime;
                this.moveXByCurrent += value;

                if (this.moveXBySpeed > 0)
                {
                    if (this.moveXByCurrent > this.moveXBy)
                    {
                        moveXByComplete = true;
                    } else
                    {
                        tmpX += value;
                    }
                } else
                {
                    if (this.moveXByCurrent < this.moveXBy)
                    {
                        moveXByComplete = true;
                    } else
                    {
                        tmpX += value;
                    }
                }
                this.theSprite.setX(tmpX);
            } else
            {
                moveXByComplete = true;
            }

        }

        if (!moveYByComplete)
        {

            if (abs(abs(this.moveYBy) - Action.MINIMUM) > Action.EQUAL_STANDARD)
            {
                double tmpY = this.theSprite.getY();
                double value = this.moveYBySpeed * runningTime;
                this.moveYByCurrent += value;

                if (this.moveYBySpeed > 0)
                {
                    if (this.moveYByCurrent > this.moveYBy)
                    {
                        moveYByComplete = true;
                    } else
                    {
                        tmpY += value;
                    }
                } else
                {
                    if (this.moveYByCurrent < this.moveYBy)
                    {
                        moveYByComplete = true;
                    } else
                    {
                        tmpY += value;
                    }
                }

                this.theSprite.setY(tmpY);
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
