/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class VelocityXByAction : VelocityAction()
{
    var velocityXBy = 0.0
    var velocityXByDuration = 0f
    var velocityXBySpeed = 0.0
    var velocityXByCurrent = 0.0
    fun velocityXBy(x: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        velocityXBy = x
        velocityXByDuration = Math.abs(duration * 1000)
        if (!bImmediately)
        {
            velocityXBySpeed = x / velocityXByDuration
        }
        velocityXByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.velocityX = theSprite!!.velocityX + velocityXBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(velocityXBy) > Action.Companion.MINIMUM)
        {
            var x = theSprite!!.velocityX
            val value = velocityXBySpeed * runningTime
            velocityXByCurrent += value
            if (velocityXBySpeed > 0)
            {
                if (velocityXByCurrent > velocityXBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            } else
            {
                if (velocityXByCurrent < velocityXBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            }
            theSprite!!.velocityX = x
        } else
        {
            bComplete = true
        }
    }
}