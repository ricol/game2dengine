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
public class AccelarationYByAction extends AccelarationAction
{

    double accelarationYBy;
    float accelarationYByDuration;
    double accelarationYBySpeed;
    double accelarationYByCurrent;

    public AccelarationYByAction()
    {
        this.accelarationYBy = 0;
        this.accelarationYByDuration = 0;
        this.accelarationYBySpeed = 0;
        this.accelarationYByCurrent = 0;
    }

    public void accelarationYBy(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            bImmediately = true;
        }
        this.accelarationYBy = x;
        this.accelarationYByDuration = abs(duration * 1000);
        if (!bImmediately)
        {
            this.accelarationYBySpeed = x / abs(duration * 1000);
        }
        this.accelarationYByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            this.theSprite.setAccelarationY(this.theSprite.getAccelarationY() + accelarationYBy);
            bComplete = true;
        }

        if (bComplete)
        {
            return;
        }

        if (abs(this.accelarationYBy) > Action.MINIMUM)
        {
            double x = this.theSprite.getAccelarationY();
            double value = this.accelarationYBySpeed * runningTime;
            this.accelarationYByCurrent += value;

            if (this.accelarationYBySpeed > 0)
            {
                if (this.accelarationYByCurrent > this.accelarationYBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            } else
            {
                if (this.accelarationYByCurrent < this.accelarationYBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            }
            this.theSprite.setAccelarationY(x);
        } else
        {
            bComplete = true;
        }

    }
}
