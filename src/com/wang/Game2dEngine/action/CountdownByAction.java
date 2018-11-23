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
public class CountdownByAction extends Action
{

    float CountdownByDuration;
    float CountdownByBySpeed;
    float CountdownByCurrent;

    public void CountdownBy(float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            this.bImmediately = true;
        }
        this.CountdownByDuration = abs(duration * 1000);
        this.CountdownByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            bComplete = true;
        }

        if (bComplete)
        {
            return;
        }

        if (abs(this.CountdownByDuration) > 0)
        {
            this.CountdownByCurrent += runningTime;

            if (this.CountdownByCurrent >= this.CountdownByDuration)
            {
                bComplete = true;
            }
        } else
        {
            bComplete = true;
        }
    }
}
