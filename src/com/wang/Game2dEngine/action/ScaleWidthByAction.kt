/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class ScaleWidthByAction : ScaleAction()
{
    var scaleWidthBy = 0.0
    var scaleWidthByDuration = 0f
    var scaleWidthBySpeed = 0.0
    var scaleWidthByCurrent = 0.0
    fun scaleWidthBy(value: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        scaleWidthBy = value
        scaleWidthByDuration = duration
        if (!bImmediately)
        {
            scaleWidthBySpeed = value / Math.abs(duration * 1000)
        }
        scaleWidthByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.width = theSprite!!.width + scaleWidthBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(scaleWidthBy) > Action.Companion.MINIMUM)
        {
            var width = theSprite!!.width
            val value = scaleWidthBySpeed * runningTime
            scaleWidthByCurrent += value
            if (scaleWidthBy > 0)
            {
                if (scaleWidthByCurrent > scaleWidthBy)
                {
                    bComplete = true
                } else
                {
                    width += value
                }
            } else
            {
                if (scaleWidthByCurrent < scaleWidthBy)
                {
                    bComplete = true
                } else
                {
                    width += value
                }
            }
            theSprite!!.width = width
        } else
        {
            bComplete = true
        }
    }
}