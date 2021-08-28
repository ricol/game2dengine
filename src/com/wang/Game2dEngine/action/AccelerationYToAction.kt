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
class AccelerationYToAction(theSprite: Sprite?) : AccelerationAction()
{
    var theAccelerationYByAction: AccelerationYByAction? = null
    var accelerationYTo = 0.0
    var accelerationYToDuration = 0f
    fun accelerationYTo(y: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        accelerationYTo = y
        accelerationYToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theAccelerationYByAction!!.clearSprite()
        theAccelerationYByAction = null
        theSprite = null
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theAccelerationYByAction == null)
        {
            theAccelerationYByAction = AccelerationYByAction()
            val tmpY = theSprite!!.accelarationY
            theAccelerationYByAction!!.accelerationYBy(accelerationYTo - tmpY, accelerationYToDuration / 1000.0f)
            theAccelerationYByAction!!.setSprite(theSprite)
        }
        theAccelerationYByAction!!.perform(runningTime)
        if (theAccelerationYByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            accelerationYTo = this.theSprite!!.accelarationY
            accelerationYToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}