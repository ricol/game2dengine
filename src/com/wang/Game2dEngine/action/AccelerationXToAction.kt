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
class AccelerationXToAction(theSprite: Sprite?) : AccelerationAction()
{
    var theAccelerationXByAction: AccelerationXByAction? = null
    var accelerationXTo = 0.0
    var accelerationXToDuration = 0f
    fun accelerationXToAction(x: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        accelerationXTo = x
        accelerationXToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theAccelerationXByAction!!.clearSprite()
        theAccelerationXByAction = null
        theSprite = null
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theAccelerationXByAction == null)
        {
            theAccelerationXByAction = AccelerationXByAction()
            val tmpX = theSprite!!.accelarationX
            theAccelerationXByAction!!.accelarationXBy(accelerationXTo - tmpX, accelerationXToDuration / 1000.0f)
            theAccelerationXByAction!!.setSprite(theSprite)
        }
        theAccelerationXByAction!!.perform(runningTime)
        if (theAccelerationXByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            accelerationXTo = this.theSprite!!.accelarationX
            accelerationXToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}