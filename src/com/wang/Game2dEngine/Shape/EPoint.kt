/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape

import com.wang.Game2dEngine.Shape.Interface.IEShape
import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics
import com.wang.Game2dEngine.sprite.Node
import com.wang.math.geometry.Point
import java.awt.Color

/**
 * @author ricolwang
 */
class EPoint(x: Double, y: Double) : Point(x, y), IEShape
{
    override var node: Node? = null
    override val shape: IEShape?
        get() = null

    override fun draw(theGraphicsInTheScene: IEngineGraphics, theColor: Color?)
    {
        theGraphicsInTheScene.setColor(theColor)
        theGraphicsInTheScene.fillArc(x.toInt(), y.toInt(), 2, 2, 0, 360)
    }

    override fun setTheNode(theNode: Node?)
    {
        node = theNode
    }
}