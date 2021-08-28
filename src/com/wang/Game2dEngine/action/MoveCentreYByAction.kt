/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class MoveCentreYByAction : MoveAction()
{
    var theMoveYByAction: MoveYByAction? = null
    var moveCentreYBy = 0.0
    var moveCentreYByDuration = 0f
    fun moveCentreYBy(y: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        moveCentreYBy = y
        moveCentreYByDuration = Math.abs(duration * 1000)
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theMoveYByAction == null)
        {
            theMoveYByAction = MoveYByAction()
            theMoveYByAction!!.moveYBy(moveCentreYBy, moveCentreYByDuration / 1000.0f)
            theMoveYByAction!!.setSprite(theSprite)
        }
        theMoveYByAction!!.perform(runningTime)
        if (theMoveYByAction!!.bComplete)
        {
            bComplete = true
        }
    }
}