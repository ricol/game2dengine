/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class AlphaByAction : AlphaAction()
{
    var alphaBy = 0f
    var alphaByDuration = 0f
    var alphaByBySpeed = 0f
    var alphaByCurrent = 0f
    fun alphaBy(alpha: Float, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        alphaBy = alpha
        alphaByDuration = Math.abs(duration * 1000)
        if (!bImmediately)
        {
            alphaByBySpeed = alpha / Math.abs(duration * 1000)
        }
        alphaByCurrent = 0f
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.alpha = theSprite!!.alpha + alphaBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(alphaBy) > Action.Companion.MINIMUM)
        {
            var alpha = theSprite!!.alpha
            val value = alphaByBySpeed * runningTime
            alphaByCurrent += value.toFloat()
            if (alphaByBySpeed > 0)
            {
                if (alphaByCurrent > alphaBy)
                {
                    bComplete = true
                } else
                {
                    alpha += value.toFloat()
                }
            } else
            {
                if (alphaByCurrent < alphaBy)
                {
                    bComplete = true
                } else
                {
                    alpha += value.toFloat()
                }
            }
            theSprite!!.alpha = alpha
        } else
        {
            bComplete = true
        }
    }
}