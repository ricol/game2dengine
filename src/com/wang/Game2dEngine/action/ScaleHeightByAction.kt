/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class ScaleHeightByAction : ScaleAction()
{
    var scaleHeightBy = 0.0
    var scaleHeightByDuration = 0f
    var scaleHeightBySpeed = 0.0
    var scaleHeightByCurrent = 0.0
    fun scaleHeightBy(value: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        scaleHeightBy = value
        scaleHeightByDuration = duration
        if (!bImmediately)
        {
            scaleHeightBySpeed = value / Math.abs(duration * 1000)
        }
        scaleHeightByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.height = theSprite!!.height + scaleHeightBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(scaleHeightBy) > Action.Companion.MINIMUM)
        {
            var height = theSprite!!.height
            val value = scaleHeightBySpeed * runningTime
            scaleHeightByCurrent += value
            if (scaleHeightBy > 0)
            {
                if (scaleHeightByCurrent > scaleHeightBy)
                {
                    bComplete = true
                } else
                {
                    height += value
                }
            } else
            {
                if (scaleHeightByCurrent < scaleHeightBy)
                {
                    bComplete = true
                } else
                {
                    height += value
                }
            }
            theSprite!!.height = height
        } else
        {
            bComplete = true
        }
    }
}