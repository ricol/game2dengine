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
class ScaleHeightToAction(theSprite: Sprite?) : ScaleAction()
{
    var scaleHeightTo: Double
    var scaleHeightToDuration: Float
    var theScaleHeightByAction: ScaleHeightByAction? = null
    fun ScaleHeightTo(height: Double, duration: Float)
    {
        scaleHeightTo = height
        scaleHeightToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theScaleHeightByAction!!.clearSprite()
        theScaleHeightByAction = null
        theSprite = null
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theScaleHeightByAction == null)
        {
            theScaleHeightByAction = ScaleHeightByAction()
            val height = theSprite!!.height
            theScaleHeightByAction!!.scaleHeightBy(scaleHeightTo - height, scaleHeightToDuration / 1000.0f)
            theScaleHeightByAction!!.setSprite(theSprite)
        }
        theScaleHeightByAction!!.perform(runningTime)
        if (theScaleHeightByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        setSprite(theSprite)
        scaleHeightTo = this.theSprite!!.height
        scaleHeightToDuration = 0f
    }
}