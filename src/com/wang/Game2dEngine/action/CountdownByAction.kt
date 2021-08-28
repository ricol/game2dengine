/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class CountdownByAction : Action()
{
    var CountdownByDuration = 0f
    var CountdownByBySpeed = 0f
    var CountdownByCurrent = 0f
    fun CountdownBy(duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        CountdownByDuration = Math.abs(duration * 1000)
        CountdownByCurrent = 0f
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(CountdownByDuration) > 0)
        {
            CountdownByCurrent += runningTime.toFloat()
            if (CountdownByCurrent >= CountdownByDuration)
            {
                bComplete = true
            }
        } else
        {
            bComplete = true
        }
    }
}