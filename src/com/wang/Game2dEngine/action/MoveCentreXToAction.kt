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
class MoveCentreXToAction(theSprite: Sprite?) : MoveAction()
{
    var theMoveXToAction: MoveXToAction? = null
    var moveCentreXTo = 0.0
    var moveCentreXToDuration = 0f
    fun moveCentreXTo(x: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        moveCentreXTo = x
        moveCentreXToDuration = Math.abs(duration * 1000)
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theMoveXToAction == null)
        {
            theMoveXToAction = MoveXToAction(theSprite)
            theMoveXToAction!!.moveXTo(moveCentreXTo - theSprite!!.width / 2.0, moveCentreXToDuration / 1000.0f)
            theMoveXToAction!!.setSprite(theSprite)
        }
        theMoveXToAction!!.perform(runningTime)
        if (theMoveXToAction!!.bComplete)
        {
            bComplete = true
        }
    }

    override fun clearSprite()
    {
        theMoveXToAction!!.clearSprite()
        theMoveXToAction = null
        theSprite = null
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            moveCentreXTo = this.theSprite!!.centreX
            moveCentreXToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}