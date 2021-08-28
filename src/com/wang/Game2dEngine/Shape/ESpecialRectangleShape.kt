/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape

import com.wang.Game2dEngine.Shape.Interface.IEShape
import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics
import com.wang.Game2dEngine.sprite.Node
import com.wang.math.geometry.SpecialRectangleShape
import java.awt.Color

/**
 * @author ricolwang
 */
class ESpecialRectangleShape(left: Double, top: Double, width: Double, height: Double) : SpecialRectangleShape(left, top, width, height), IEShape
{
    override var node: Node? = null
    override fun refresh(changeX: Double, changeY: Double, changeWidth: Double, changeHeight: Double)
    {
        super.refresh(changeX, changeY, changeWidth, changeHeight)
        left = node!!.x
        top = node!!.y
        width = node!!.width
        height = node!!.height
    }

    override fun setTheNode(theNode: Node?)
    {
        node = theNode
    }

    override val shape: IEShape?
        get() = this

    override fun draw(theGraphicsInTheScene: IEngineGraphics, theColor: Color?)
    {
        theGraphicsInTheScene.setColor(theColor)
        theGraphicsInTheScene.drawRect(left.toInt(), top.toInt(), width.toInt() - 1, height.toInt() - 1)
    }
}