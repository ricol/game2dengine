/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

import com.wang.Game2dEngine.sprite.Sprite

/**
 * @author Philology
 */
class VelocityYToAction(theSprite: Sprite?) : VelocityAction()
{
    var theVelocityYByAction: VelocityYByAction? = null
    var velocityYTo = 0.0
    var velocityYToDuration = 0f
    fun velocityYTo(y: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        velocityYTo = y
        velocityYToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theVelocityYByAction!!.clearSprite()
        theVelocityYByAction = null
        theSprite = null
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theVelocityYByAction == null)
        {
            theVelocityYByAction = VelocityYByAction()
            val tmpY = theSprite!!.velocityY
            theVelocityYByAction!!.velocityYBy(velocityYTo - tmpY, velocityYToDuration / 1000.0f)
            theVelocityYByAction!!.setSprite(theSprite)
        }
        theVelocityYByAction!!.perform(runningTime)
        if (theVelocityYByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            velocityYTo = this.theSprite!!.velocityY
            velocityYToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}