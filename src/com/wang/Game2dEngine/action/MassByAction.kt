/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class MassByAction : MassAction()
{
    var massBy = 0.0
    var massByDuration = 0f
    var massByBySpeed = 0.0
    var massByCurrent = 0.0
    fun massBy(mass: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        massBy = mass
        massByDuration = Math.abs(duration * 1000)
        if (!bImmediately)
        {
            massByBySpeed = mass / Math.abs(duration * 1000)
        }
        massByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.mass = theSprite!!.mass + massBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(massBy) > Action.Companion.MINIMUM)
        {
            var mass = theSprite!!.mass
            val value = massByBySpeed * runningTime
            massByCurrent += value
            if (massByBySpeed > 0)
            {
                if (massByCurrent > massBy)
                {
                    bComplete = true
                } else
                {
                    mass += value
                }
            } else
            {
                if (massByCurrent < massBy)
                {
                    bComplete = true
                } else
                {
                    mass += value
                }
            }
            theSprite!!.mass = mass
        } else
        {
            bComplete = true
        }
    }
}