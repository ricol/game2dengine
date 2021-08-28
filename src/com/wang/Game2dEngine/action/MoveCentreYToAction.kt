/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

import com.wang.Game2dEngine.sprite.Sprite

/**
 * @author ricolwang
 */
class MoveCentreYToAction(theSprite: Sprite?) : MoveAction()
{
    var theMoveYToAction: MoveYToAction? = null
    var moveCentreYTo = 0.0
    var moveCentreYToDuration = 0f
    fun moveCentreYTo(y: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        moveCentreYTo = y
        moveCentreYToDuration = Math.abs(duration * 1000)
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theMoveYToAction == null)
        {
            theMoveYToAction = MoveYToAction(theSprite)
            theMoveYToAction!!.moveYTo(moveCentreYTo - theSprite!!.width / 2.0, moveCentreYToDuration / 1000.0f)
            theMoveYToAction!!.setSprite(theSprite)
        }
        theMoveYToAction!!.perform(runningTime)
        if (theMoveYToAction!!.bComplete)
        {
            bComplete = true
        }
    }

    override fun clearSprite()
    {
        theMoveYToAction!!.clearSprite()
        theMoveYToAction = null
        theSprite = null
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            moveCentreYTo = this.theSprite!!.centreY
            moveCentreYToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}