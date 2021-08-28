/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class MoveCentreXByAction : MoveAction()
{
    var theMoveXByAction: MoveXByAction? = null
    var moveCentreXBy = 0.0
    var moveCentreXByDuration = 0f
    fun moveCentreXBy(x: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        moveCentreXBy = x
        moveCentreXByDuration = Math.abs(duration * 1000)
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theMoveXByAction == null)
        {
            theMoveXByAction = MoveXByAction()
            theMoveXByAction!!.moveXBy(moveCentreXBy, moveCentreXByDuration / 1000.0f)
            theMoveXByAction!!.setSprite(theSprite)
        }
        theMoveXByAction!!.perform(runningTime)
        if (theMoveXByAction!!.bComplete)
        {
            bComplete = true
        }
    }
}