/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.sprite.other

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics
import com.wang.Game2dEngine.sprite.Sprite
import java.awt.Color

/**
 * @author ricolwang
 */
class LifeBar(width: Int, height: Int, life: Int) : Sprite(0.0, 0.0, width.toDouble(), height.toDouble())
{
    var current: Int = life
    override fun onCustomDraw(theGraphics2D: IEngineGraphics)
    {
        super.onCustomDraw(theGraphics2D) //To change body of generated methods, choose Tools | Templates.
        theGraphics2D.setColor(this.color)
        theGraphics2D.drawRect(0, 0, width.toInt() - 1, height.toInt() - 1)
        if (current <= life && current >= 0)
        {
            val width = (current * 1.0 / life * width * 1.0).toFloat()
            theGraphics2D.setColor(Color.blue)
            theGraphics2D.fillRect(1, 1, width.toInt() - 2, height.toInt() - 2)
        }
    }

    fun decreaseLifeBy(value: Int)
    {
        current -= value
    }

    init
    {
        bCustomDrawing = true
        red = 255
    }
}