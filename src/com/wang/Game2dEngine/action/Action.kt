/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action

import com.wang.Game2dEngine.sprite.Sprite
import java.util.*

/**
 * @author ricolwang
 */
abstract class Action
{
    var identifier = this.javaClass.name
    protected var bImmediately = false
    var theRandom = Random()
    var theSprite: Sprite? = null
        protected set
    var bComplete = false

    //runningTime in milliseconds
    open fun perform(runningTime: Double)
    {
        bComplete = theRandom.nextBoolean()
    }

    fun setSprite(aSprite: Sprite?)
    {
        theSprite = aSprite
    }

    open fun clearSprite()
    {
        theSprite = null
    }

    override fun toString(): String
    {
        return "Action: " + this.javaClass.name + " - id: " + identifier
    }

    companion object
    {
        const val MINIMUM = 1e-03
        const val EQUAL_STANDARD = 1e-10
        const val MINIMUM_DURATION = 1e-3
    }
}