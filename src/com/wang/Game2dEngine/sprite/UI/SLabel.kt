/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.sprite.UI

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics
import com.wang.Game2dEngine.sprite.Sprite
import java.awt.Color
import java.awt.Font

/**
 * @author ricolwang Remember to set width and height of the text
 */
class SLabel : Sprite
{
    var text = ""
    var bTextFrame = true
    var textFont = Font("TimesRoman", Font.PLAIN, 15)
    var textFrameColor = Color.blue
    var textPosX = 10
    var textPosY = 10

    constructor(x: Double, y: Double, text: String, theTextFont: Font?) : super(x, y, 0.0, 0.0, 0.0, 0.0)
    {
        bCustomDrawing = true
        this.text = text
        if (theTextFont != null)
        {
            textFont = theTextFont
        }
        green = 255
    }

    constructor(text: String, theTextFont: Font?) : super(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    {
        bCustomDrawing = true
        this.text = text
        if (theTextFont != null)
        {
            textFont = theTextFont
        }
        green = 255
    }

    constructor(text: String) : super(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    {
        bCustomDrawing = true
        this.text = text
        green = 255
    }

    override fun onCustomDraw(theGraphics2D: IEngineGraphics)
    {
        super.onCustomDraw(theGraphics2D)
        if (bTextFrame)
        {
            theGraphics2D.setColor(textFrameColor)
            theGraphics2D.drawRect(0, 0, width.toInt() - 1, height.toInt() - 1)
        }
        theGraphics2D.setFont(textFont)
        theGraphics2D.setColor(color)
        theGraphics2D.drawString(text, textPosX.toFloat(), textPosY.toFloat())
    }
}