/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class VelocityAngleByAction : VelocityAngleAction()
{
    var velocityAngleBy = 0.0
    var velocityAngleByDuration = 0f
    var velocityAngleBySpeed = 0.0
    var velocityAngleByCurrent = 0.0
    fun velocityAngleBy(x: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        velocityAngleBy = x
        velocityAngleByDuration = Math.abs(duration * 1000)
        if (!bImmediately)
        {
            velocityAngleBySpeed = x / Math.abs(duration * 1000)
        }
        velocityAngleByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.velocityAngle = theSprite!!.velocityAngle + velocityAngleBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(velocityAngleBy) > Action.Companion.MINIMUM)
        {
            var x = theSprite!!.velocityAngle
            val value = velocityAngleBySpeed * runningTime
            velocityAngleByCurrent += value
            if (velocityAngleBySpeed > 0)
            {
                if (velocityAngleByCurrent > velocityAngleBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            } else
            {
                if (velocityAngleByCurrent < velocityAngleBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            }
            theSprite!!.velocityAngle = x
        } else
        {
            bComplete = true
        }
    }
}