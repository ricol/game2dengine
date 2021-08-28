/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class RotateByAction : RotateAction()
{
    var rotateBy = 0.0
    var rotateByDuration = 0.0
    var rotateBySpeed = 0.0
    var rotateByCurrent = 0.0
    fun rotateBy(angle: Double, duration: Double)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0.0
            bImmediately = true
        }
        rotateBy = angle
        rotateByDuration = duration
        if (!bImmediately)
        {
            rotateBySpeed = angle / Math.abs(duration * 1000)
        }
        rotateByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.angle = theSprite!!.angle + rotateBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(rotateBy) > Action.Companion.MINIMUM)
        {
            var angle = theSprite!!.angle
            val value = rotateBySpeed * runningTime
            rotateByCurrent += value
            if (rotateBySpeed > 0)
            {
                if (rotateByCurrent > rotateBy)
                {
                    bComplete = true
                } else
                {
                    angle += value
                }
            } else
            {
                if (rotateByCurrent < rotateBy)
                {
                    bComplete = true
                } else
                {
                    angle += value
                }
            }
            theSprite!!.angle = angle
        } else
        {
            bComplete = true
        }
    }
}