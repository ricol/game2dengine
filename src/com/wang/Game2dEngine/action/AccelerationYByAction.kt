/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class AccelerationYByAction : AccelerationAction()
{
    var accelerationYBy = 0.0
    var accelerationYByDuration = 0f
    var accelerationYBySpeed = 0.0
    var accelerationYByCurrent = 0.0
    fun accelerationYBy(y: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        accelerationYBy = y
        accelerationYByDuration = Math.abs(duration * 1000)
        if (!bImmediately)
        {
            accelerationYBySpeed = y / Math.abs(duration * 1000)
        }
        accelerationYByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.accelarationY = theSprite!!.accelarationY + accelerationYBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(accelerationYBy) > Action.Companion.MINIMUM)
        {
            var x = theSprite!!.accelarationY
            val value = accelerationYBySpeed * runningTime
            accelerationYByCurrent += value
            if (accelerationYBySpeed > 0)
            {
                if (accelerationYByCurrent > accelerationYBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            } else
            {
                if (accelerationYByCurrent < accelerationYBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            }
            theSprite!!.accelarationY = x
        } else
        {
            bComplete = true
        }
    }
}