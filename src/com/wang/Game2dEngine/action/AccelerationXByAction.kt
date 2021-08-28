/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

/**
 * @author ricolwang
 */
class AccelerationXByAction : AccelerationAction()
{
    var accelarationXBy = 0.0
    var accelarationXByDuration = 0f
    var accelarationXBySpeed = 0.0
    var accelarationXByCurrent = 0.0
    fun accelarationXBy(x: Double, duration: Float)
    {
        var duration = duration
        if (duration <= 0)
        {
            duration = 0f
            bImmediately = true
        }
        accelarationXBy = x
        accelarationXByDuration = Math.abs(duration * 1000)
        if (!bImmediately)
        {
            accelarationXBySpeed = x / accelarationXByDuration
        }
        accelarationXByCurrent = 0.0
    }

    override fun perform(runningTime: Double)
    {
        if (bImmediately)
        {
            theSprite!!.accelarationX = theSprite!!.accelarationX + accelarationXBy
            bComplete = true
        }
        if (bComplete)
        {
            return
        }
        if (Math.abs(accelarationXBy) > Action.Companion.MINIMUM)
        {
            var x = theSprite!!.accelarationX
            val value = accelarationXBySpeed * runningTime
            accelarationXByCurrent += value
            println("accelarationXByCurrent: $accelarationXByCurrent")
            if (accelarationXBySpeed > 0)
            {
                if (accelarationXByCurrent > accelarationXBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            } else
            {
                if (accelarationXByCurrent < accelarationXBy)
                {
                    bComplete = true
                } else
                {
                    x += value
                }
            }
            theSprite!!.accelarationX = x
            println("Accelaration: " + theSprite!!.accelarationX)
        } else
        {
            bComplete = true
        }
    }
}