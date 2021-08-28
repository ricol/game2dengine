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
class VelocityAngleToAction(theSprite: Sprite?) : VelocityAngleAction()
{
    var theVelocityAngleByAction: VelocityAngleByAction? = null
    var velocityAngleTo = 0.0
    var velocityAngleToDuration = 0f
    fun velocityAngleTo(x: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        velocityAngleTo = x
        velocityAngleToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theVelocityAngleByAction!!.clearSprite()
        theVelocityAngleByAction = null
        theSprite = null
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theVelocityAngleByAction == null)
        {
            theVelocityAngleByAction = VelocityAngleByAction()
            val tmpAngle = theSprite!!.velocityAngle
            theVelocityAngleByAction!!.velocityAngleBy(velocityAngleTo - tmpAngle, velocityAngleToDuration / 1000.0f)
            theVelocityAngleByAction!!.setSprite(theSprite)
        }
        theVelocityAngleByAction!!.perform(runningTime)
        if (theVelocityAngleByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            velocityAngleTo = this.theSprite!!.velocityAngle
            velocityAngleToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}