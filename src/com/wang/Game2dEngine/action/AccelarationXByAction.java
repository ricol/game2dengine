/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action;

import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class AccelarationXByAction extends AccelarationAction
{
    double accelarationXBy;
    float accelarationXByDuration;
    double accelarationXBySpeed;
    double accelarationXByCurrent;

    public AccelarationXByAction()
    {
        this.accelarationXBy = 0;
        this.accelarationXByDuration = 0;
        this.accelarationXBySpeed = 0;
        this.accelarationXByCurrent = 0;
    }

    public void accelarationXBy(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            bImmediately = true;
        }
        this.accelarationXBy = x;
        this.accelarationXByDuration = abs(duration * 1000);
        if (!bImmediately)
        {
            this.accelarationXBySpeed = x / this.accelarationXByDuration;
        }
        this.accelarationXByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            this.theSprite.setAccelarationX(this.theSprite.getAccelarationX() + accelarationXBy);
            bComplete = true;
        }

        if (bComplete)
        {
            return;
        }

        if (abs(this.accelarationXBy) > Action.MINIMUM)
        {
            double x = this.theSprite.getAccelarationX();
            double value = this.accelarationXBySpeed * runningTime;
            this.accelarationXByCurrent += value;
            System.out.println("accelarationXByCurrent: " + accelarationXByCurrent);
            if (this.accelarationXBySpeed > 0)
            {
                if (this.accelarationXByCurrent > this.accelarationXBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            } else
            {
                if (this.accelarationXByCurrent < this.accelarationXBy)
                {
                    bComplete = true;
                } else
                {
                    x += value;
                }
            }
            this.theSprite.setAccelarationX(x);
            System.out.println("Accelaration: " + this.theSprite.getAccelarationX());
        } else
        {
            bComplete = true;
        }

    }
}
