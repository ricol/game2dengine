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
class AlphaToAction(theSprite: Sprite?) : AlphaAction()
{
    var theAlphaByAction: AlphaByAction? = null
    var alphaTo = 0f
    var alphaToDuration = 0f

    //duration in seconds
    fun alphaTo(x: Float, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
        }
        alphaTo = x
        alphaToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theAlphaByAction!!.clearSprite()
        theAlphaByAction = null
        theSprite = null
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theAlphaByAction == null)
        {
            theAlphaByAction = AlphaByAction()
            val alpha = theSprite!!.alpha
            theAlphaByAction!!.alphaBy(alphaTo - alpha, alphaToDuration / 1000.0f)
            theAlphaByAction!!.setSprite(theSprite)
        }
        theAlphaByAction!!.perform(runningTime)
        if (theAlphaByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        if (theSprite != null)
        {
            setSprite(theSprite)
            alphaTo = this.theSprite!!.alpha
            alphaToDuration = 0f
        } else
        {
            bComplete = true
        }
    }
}