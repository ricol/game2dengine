/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class MoveYByAction : MoveAction()
{
    var moveYBy = 0.0
    var moveYByDuration = 0f
    var moveYBySpeed = 0.0
    var moveYByCurrent = 0.0
    fun moveYBy(y: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        moveYBy = y
        moveYByDuration = Math.abs(duration * 1000)
        if (!bImmediately)
        {
            moveYBySpeed = y / Math.abs(duration * 1000)
        }
        moveYByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.y = theSprite!!.y + moveYBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(moveYBy) > Action.Companion.MINIMUM)
        {
            var y = theSprite!!.y
            val value = moveYBySpeed * runningTime
            moveYByCurrent += value
            if (moveYBySpeed > 0)
            {
                if (moveYByCurrent > moveYBy)
                {
                    bComplete = true
                } else
                {
                    y += value
                }
            } else
            {
                if (moveYByCurrent < moveYBy)
                {
                    bComplete = true
                } else
                {
                    y += value
                }
            }
            theSprite!!.y = y
        } else
        {
            bComplete = true
        }
    }
}