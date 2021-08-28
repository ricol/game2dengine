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
class VelocityXToAction(theSprite: Sprite?) : VelocityAction()
{
    var theVelocityXByAction: VelocityXByAction? = null
    var velocityXTo = 0.0
    var velocityXToDuration = 0f
    fun velocityXTo(x: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        velocityXTo = x
        velocityXToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theVelocityXByAction!!.clearSprite()
        theVelocityXByAction = null
        theSprite = null
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theVelocityXByAction == null)
        {
            theVelocityXByAction = VelocityXByAction()
            val tmpX = theSprite!!.velocityX
            theVelocityXByAction!!.velocityXBy(velocityXTo - tmpX, velocityXToDuration / 1000.0f)
            theVelocityXByAction!!.setSprite(theSprite)
        }
        theVelocityXByAction!!.perform(runningTime)
        if (theVelocityXByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            velocityXTo = this.theSprite!!.velocityX
            velocityXToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}