/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape

import com.wang.Game2dEngine.Shape.Interface.IEShape
import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics
import com.wang.Game2dEngine.sprite.Node
import com.wang.math.geometry.Line
import com.wang.math.geometry.Point
import java.awt.Color

/**
 * @author ricolwang
 */
class ELine(start: Point?, end: Point?) : Line(start, end), IEShape
{
    override var node: Node? = null
    override fun draw(theGraphicsInTheScene: IEngineGraphics, theColor: Color?)
    {
        theGraphicsInTheScene.setColor(theColor)
        theGraphicsInTheScene.drawLine(start.x.toInt(), start.y.toInt(), end.x.toInt(), end.y.toInt())
    }

    override val shape: IEShape?
        get() = null

    override fun setTheNode(theNode: Node?)
    {
        node = theNode
    }
}