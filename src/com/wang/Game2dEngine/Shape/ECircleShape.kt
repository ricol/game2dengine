/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape

import com.wang.Game2dEngine.Shape.Interface.IEShape
import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics
import com.wang.Game2dEngine.sprite.Node
import com.wang.math.geometry.CircledShape
import java.awt.Color

/**
 * @author ricolwang
 */
class ECircleShape(centreX: Double, centreY: Double, radius: Double) : CircledShape(centreX, centreY, radius), IEShape
{
    override var node: Node? = null
    override fun setTheNode(theNode: Node?)
    {
        node = theNode
    }

    override fun refresh(changeX: Double, changeY: Double, changeWidth: Double, changeHeight: Double)
    {
        super.refresh(changeX, changeY, changeWidth, changeHeight) //To change body of generated methods, choose Tools | Templates.
        centre.x = node!!.centreX
        centre.y = node!!.centreY
        radius = if (node!!.width > node!!.height) node!!.width / 2.0f else node!!.height / 2.0f
    }

    override val shape: IEShape?
        get() = this

    override fun draw(theGraphicsInTheScene: IEngineGraphics, theColor: Color?)
    {
        val tmpRadius = radius.toInt()
        val tmpX = (centre.x - tmpRadius).toInt()
        val tmpY = (centre.y - tmpRadius).toInt()
        theGraphicsInTheScene.setColor(theColor)
        theGraphicsInTheScene.drawArc(tmpX, tmpY, 2 * tmpRadius, 2 * tmpRadius, 0, 360)
    }
}