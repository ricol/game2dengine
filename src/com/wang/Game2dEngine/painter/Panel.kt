/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.painter

import java.awt.Canvas
import java.awt.Graphics
import java.awt.image.BufferStrategy

/**
 * @author ricolwang
 */
class Panel : Canvas()
{
    var strategy: BufferStrategy? = null
    fun render()
    {
        strategy!!.show()
    }

    val renderGraphics: Graphics
        get()
        {
            if (strategy == null)
            {
                createBufferStrategy(2)
                strategy = bufferStrategy
                ignoreRepaint = true
            }
            return strategy!!.drawGraphics
        }
}