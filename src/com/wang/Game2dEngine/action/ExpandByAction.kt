/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class ExpandByAction : Action()
{
    var expandBy = 0f
    var expandByDuration = 0f
    var bTheMoveXByActionComplete = false
    var bTheMoveYByActionComplete = false
    var bTheScaleWidthByActionComplete = false
    var bTheScaleHeightByActionComplete = false
    var theMoveXByAction: MoveXByAction? = null
    var theMoveYByAction: MoveYByAction? = null
    var theScaleWidthByAction: ScaleWidthByAction? = null
    var theScaleHeightByAction: ScaleHeightByAction? = null
    fun expandBy(expand: Float, duration: Float)
    {
        expandBy = expand
        expandByDuration = Math.abs(duration * 1000)
    }

    override fun perform(runningTime: Double)
    {
        if (bComplete)
        {
            return
        }
        if (!bTheMoveXByActionComplete)
        {
            if (theMoveXByAction == null)
            {
                theMoveXByAction = MoveXByAction()
                theMoveXByAction!!.moveXBy(-expandBy.toDouble(), expandByDuration / 1000.0f)
                theMoveXByAction!!.setSprite(theSprite)
            }
            theMoveXByAction!!.perform(runningTime)
            if (theMoveXByAction!!.bComplete)
            {
                bTheMoveXByActionComplete = true
            }
        }
        if (!bTheMoveYByActionComplete)
        {
            if (theMoveYByAction == null)
            {
                theMoveYByAction = MoveYByAction()
                theMoveYByAction!!.moveYBy(-expandBy.toDouble(), expandByDuration / 1000.0f)
                theMoveYByAction!!.setSprite(theSprite)
            }
            theMoveYByAction!!.perform(runningTime)
            if (theMoveYByAction!!.bComplete)
            {
                bTheMoveYByActionComplete = true
            }
        }
        if (!bTheScaleWidthByActionComplete)
        {
            if (theScaleWidthByAction == null)
            {
                theScaleWidthByAction = ScaleWidthByAction()
                theScaleWidthByAction!!.scaleWidthBy(2 * expandBy.toDouble(), expandByDuration / 1000.0f)
                theScaleWidthByAction!!.setSprite(theSprite)
            }
            theScaleWidthByAction!!.perform(runningTime)
            if (theScaleWidthByAction!!.bComplete)
            {
                bTheScaleWidthByActionComplete = true
            }
        }
        if (!bTheScaleHeightByActionComplete)
        {
            if (theScaleHeightByAction == null)
            {
                theScaleHeightByAction = ScaleHeightByAction()
                theScaleHeightByAction!!.scaleHeightBy(2 * expandBy.toDouble(), expandByDuration / 1000.0f)
                theScaleHeightByAction!!.setSprite(theSprite)
            }
            theScaleHeightByAction!!.perform(runningTime)
            if (theScaleHeightByAction!!.bComplete)
            {
                bTheScaleHeightByActionComplete = true
            }
        }
        if (bTheMoveXByActionComplete && bTheMoveYByActionComplete && bTheScaleWidthByActionComplete && bTheScaleHeightByActionComplete)
        {
            bComplete = true
        }
    }

    override fun clearSprite()
    {
        theMoveXByAction!!.clearSprite()
        theMoveXByAction = null
        theMoveYByAction!!.clearSprite()
        theMoveYByAction = null
        theScaleWidthByAction!!.clearSprite()
        theScaleWidthByAction = null
        theScaleHeightByAction!!.clearSprite()
        theScaleHeightByAction = null
        theSprite = null
    }
}