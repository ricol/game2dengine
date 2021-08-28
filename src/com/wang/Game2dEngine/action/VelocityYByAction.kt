/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class VelocityYByAction : VelocityAction()
{
    var velocityYBy = 0.0
    var velocityYByDuration = 0f
    var velocityYBySpeed = 0.0
    var velocityYByCurrent = 0.0
    fun velocityYBy(y: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        velocityYBy = y
        velocityYByDuration = Math.abs(duration * 1000)
        if (!bImmediately)
        {
            velocityYBySpeed = y / velocityYByDuration
        }
        velocityYByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.velocityY = theSprite!!.velocityY + velocityYBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(velocityYBy) > Action.Companion.MINIMUM)
        {
            var x = theSprite!!.velocityY
            val value = velocityYBySpeed * runningTime
            velocityYByCurrent += value
            if (velocityYBySpeed > 0)
            {
                if (velocityYByCurrent > velocityYBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            } else
            {
                if (velocityYByCurrent < velocityYBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            }
            theSprite!!.velocityY = x
        } else
        {
            bComplete = true
        }
    }
}