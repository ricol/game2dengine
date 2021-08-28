/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.scene

import com.wang.Game2dEngine.sprite.Sprite
import java.util.*

/**
 * @author ricolwang
 */
class Layer(var zOrder: Int, var theScene: Scene)
{
    var AllObjects = ArrayList<Sprite?>()
    var DeadObjects = ArrayList<Sprite?>()
    var NewObjects = ArrayList<Sprite?>()
    fun addSprite(aSprite: Sprite)
    {
        NewObjects.add(aSprite)
        aSprite.theScene = theScene
        aSprite.onAddToLayer(this)
    }
}