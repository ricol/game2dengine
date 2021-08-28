/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.physics.sprites

import com.wang.Game2dEngine.Shape.ESpecialRectangleShape
import com.wang.Game2dEngine.sprite.Sprite

/**
 * @author ricolwang
 */
open class WallSprite @JvmOverloads constructor(x: Double = 0.0, y: Double = 0.0, width: Double = 0.0, height: Double = 0.0, mass: Double = 0.0, velocityX: Double = 0.0, velocityY: Double = 0.0) : Sprite(x, y, width, height, mass, velocityX, velocityY)
{
    enum class WALLTYPE
    {
        LEFT, RIGHT, TOP, BOTTOM
    }

    var wallType = WALLTYPE.LEFT

    init
    {
        bCollisionDetect = true
        theShape = ESpecialRectangleShape(x, y, width, height)
    }
}