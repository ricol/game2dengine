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
class MoveXToAction(theSprite: Sprite?) : MoveAction()
{
    var theMoveXByAction: MoveXByAction? = null
    var moveXTo = 0.0
    var moveXToDuration = 0f
    fun moveXTo(x: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        moveXTo = x
        moveXToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theMoveXByAction!!.clearSprite()
        theMoveXByAction = null
        theSprite = null
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
            val tmpX = theSprite!!.x
            theMoveXByAction!!.moveXBy(moveXTo - tmpX, moveXToDuration / 1000.0f)
            theMoveXByAction!!.setSprite(theSprite)
        }
        theMoveXByAction!!.perform(runningTime)
        if (theMoveXByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            moveXTo = this.theSprite!!.x
            moveXToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}