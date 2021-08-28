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
class RotateToAction(theSprite: Sprite?) : RotateAction()
{
    var theRotateByAction: RotateByAction? = null
    var rotateTo = 0.0
    var rotateToDuration = 0.0
    fun rotateTo(angle: Double, duration: Double)
    {
        rotateTo = angle
        rotateToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theRotateByAction!!.clearSprite()
        theRotateByAction = null
        theSprite = null
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theRotateByAction == null)
        {
            theRotateByAction = RotateByAction()
            val angle = theSprite!!.angle
            theRotateByAction!!.rotateBy(rotateTo - angle, rotateToDuration / 1000.0f)
            theRotateByAction!!.setSprite(theSprite)
        }
        theRotateByAction!!.perform(runningTime)
        if (theRotateByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            rotateTo = this.theSprite!!.angle
            rotateToDuration = 0.0
        } else
        {
            bComplete = true
        }
    }
}