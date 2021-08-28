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
class MassToAction(theSprite: Sprite?) : MassAction()
{
    var theMassByAction: MassByAction? = null
    var massTo = 0.0
    var massToDuration = 0f

    //duration in seconds
    fun massTo(x: Float, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        massTo = x.toDouble()
        massToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theMassByAction!!.clearSprite()
        theMassByAction = null
        theSprite = null
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theMassByAction == null)
        {
            theMassByAction = MassByAction()
            val mass = theSprite!!.mass
            theMassByAction!!.massBy(massTo - mass, massToDuration / 1000.0f)
            theMassByAction!!.setSprite(theSprite)
        }
        theMassByAction!!.perform(runningTime)
        if (theMassByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            massTo = this.theSprite!!.mass
            massToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}