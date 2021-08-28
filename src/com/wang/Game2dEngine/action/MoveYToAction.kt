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
class MoveYToAction(theSprite: Sprite?) : MoveAction()
{
    var theMoveYByAction: MoveYByAction? = null
    var moveYTo = 0.0
    var moveYToDuration = 0f
    fun moveYTo(y: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        moveYTo = y
        moveYToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theMoveYByAction!!.clearSprite()
        theMoveYByAction = null
        theSprite = null
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
            val tmpY = theSprite!!.y
            theMoveYByAction!!.moveYBy(moveYTo - tmpY, moveYToDuration / 1000.0f)
            theMoveYByAction!!.setSprite(theSprite)
        }
        theMoveYByAction!!.perform(runningTime)
        if (theMoveYByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            moveYTo = this.theSprite!!.y
            moveYToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}