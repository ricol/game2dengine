/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.sprite

import com.wang.Game2dEngine.Shape.ECircleShape
import com.wang.Game2dEngine.Shape.Interface.IEShape
import java.util.*

/**
 * @author ricolwang
 */
open class Node(x: Double, y: Double, width: Double, height: Double)
{
    var identifier: String? = null
    private var _x: Double = 0.0
    open var x
        set(value)
        {
            val oldX = this.x
            _x = value
            refreshShape(value - oldX, 0.0, 0.0, 0.0)
        }
        get() = _x
    private var _y: Double = 0.0
    open var y
        set(value)
        {
            val oldY = this.y
            _y = value
            refreshShape(0.0, value - oldY, 0.0, 0.0)
        }
        get() = _y
    private var _width: Double = 0.0
    open var width
        set(value)
        {
            val oldWidth = this.width
            _width = value
            refreshShape(0.0, 0.0, value - oldWidth, 0.0)
        }
        get() = _width
    private var _height: Double = 0.0
    open var height
        set(value)
        {
            val oldHeight = this.height
            _height = value
            refreshShape(0.0, 0.0, 0.0, value - oldHeight)
        }
        get() = _height
    protected var theRandom = Random()
    private var _theShape: IEShape? = null
    var theShape: IEShape?
        set(value)
        {
            _theShape = value
            value?.setTheNode(this)
            onShapeAdded(value)
        }
        get() = _theShape

    var centreX: Double
        get() = x + width / 2.0
        set(value)
        {
            x = value - width / 2.0
        }
    var centreY: Double
        get() = y + height / 2.0
        set(value)
        {
            y = value - height / 2.0
        }

    fun onShapeAdded(theShape: IEShape?)
    {
    }

    fun refreshShape(changeX: Double, changeY: Double, changeWidth: Double, changeHeight: Double)
    {
        if (theShape == null)
        {
            val aCircleShape = ECircleShape(centreX, centreY, if (width > height) width / 2.0f else height / 2.0f)
            theShape = aCircleShape
        }
        theShape!!.refresh(changeX, changeY, changeWidth, changeHeight)
    }

    init
    {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }
}