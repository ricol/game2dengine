/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class MoveXByAction : MoveAction()
{
    var moveXBy = 0.0
    var moveXByDuration = 0f
    var moveXBySpeed = 0.0
    var moveXByCurrent = 0.0
    fun moveXBy(x: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        moveXBy = x
        moveXByDuration = Math.abs(duration * 1000)
        if (!bImmediately)
        {
            moveXBySpeed = x / Math.abs(duration * 1000)
        }
        moveXByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.x = theSprite!!.x + moveXBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(moveXBy) > Action.Companion.MINIMUM)
        {
            var x = theSprite!!.x
            val value = moveXBySpeed * runningTime
            moveXByCurrent += value
            if (moveXBySpeed > 0)
            {
                if (moveXByCurrent > moveXBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            } else
            {
                if (moveXByCurrent < moveXBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            }
            theSprite!!.x = x
        } else
        {
            bComplete = true
        }
    }
}