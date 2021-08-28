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
class ScaleWidthToAction(theSprite: Sprite?) : ScaleAction()
{
    var scaleWidthTo: Double
    var scaleWidthToDuration: Float
    var theScaleWidthByAction: ScaleWidthByAction? = null
    fun ScaleWidthTo(width: Double, duration: Float)
    {
        scaleWidthTo = width
        scaleWidthToDuration = Math.abs(duration * 1000)
    }

    override fun clearSprite()
    {
        theScaleWidthByAction!!.clearSprite()
        theScaleWidthByAction = null
        theSprite = null
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (theScaleWidthByAction == null)
        {
            theScaleWidthByAction = ScaleWidthByAction()
            val width = theSprite!!.width
            theScaleWidthByAction!!.scaleWidthBy(scaleWidthTo - width, scaleWidthToDuration / 1000.0f)
            theScaleWidthByAction!!.setSprite(theSprite)
        }
        theScaleWidthByAction!!.perform(runningTime)
        if (theScaleWidthByAction!!.bComplete)
        {
            bComplete = true
        }
    }

    init
    {
        setSprite(theSprite)
        scaleWidthTo = this.theSprite!!.width
        scaleWidthToDuration = 0f
    }
}