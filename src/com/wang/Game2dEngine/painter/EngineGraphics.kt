/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.painter

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics
import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

/**
 * @author ricolwang
 */
class EngineGraphics : IEngineGraphics
{
    var theGraphics: Graphics2D? = null
    override fun drawString(text: String?, x: Float, y: Float)
    {
        theGraphics!!.drawString(text, x, y)
    }

    override fun drawImage(img: Image?, x: Int, y: Int,
                           width: Int, height: Int)
    {
        theGraphics!!.drawImage(img, x, y, width, height, null)
    }

    override fun setColor(color: Color?)
    {
        theGraphics!!.color = color
    }

    override fun fillRect(x: Int, y: Int, width: Int, height: Int)
    {
        theGraphics!!.fillRect(x, y, width, height)
    }

    override fun dispose()
    {
        theGraphics!!.dispose()
    }

    override fun drawRect(x: Int, y: Int, width: Int, height: Int)
    {
        theGraphics!!.drawRect(x, y, width, height)
    }

    override fun drawLine(x0: Int, y0: Int, x1: Int, y1: Int)
    {
        theGraphics!!.drawLine(x0, y0, x1, y1)
    }

    override fun fillArc(x: Int, y: Int, width: Int, height: Int, startAngel: Int, endAngel: Int)
    {
        theGraphics!!.fillArc(x, y, width, height, startAngel, endAngel)
    }

    override fun drawArc(x: Int, y: Int, width: Int, height: Int, startAngel: Int, endAngel: Int)
    {
        theGraphics!!.drawArc(x, y, width, height, startAngel, endAngel)
    }

    override fun setBackground(blackTransparent: Color?)
    {
        theGraphics!!.background = blackTransparent
    }

    override fun clearRect(x: Int, y: Int, width: Int, height: Int)
    {
        theGraphics!!.clearRect(x, y, width, height)
    }

    override var transform: AffineTransform?
        get() = theGraphics!!.transform
        set(t)
        {
            theGraphics!!.transform = t
        }

    override fun rotate(theta: Double, x: Float, y: Float)
    {
        theGraphics!!.rotate(theta, x.toDouble(), y.toDouble())
    }

    override fun getComposite(): Composite?
    {
        return theGraphics!!.composite
    }

    override fun setComposite(ac: AlphaComposite?)
    {
        theGraphics!!.composite = ac
    }

    override fun drawImage(image: BufferedImage?, x: Int, y: Int)
    {
        theGraphics!!.drawImage(image, x, y, null)
    }

    override fun setComposite(c: Composite?)
    {
        theGraphics!!.composite = c
    }

    override fun setFont(f: Font?)
    {
        theGraphics!!.font = f
    }

    override fun fillOval(x: Int, y: Int, width: Int, height: Int)
    {
        theGraphics!!.fillOval(x, y, width, height)
    }

    override fun drawPolygon(p: Polygon?)
    {
        theGraphics!!.drawPolygon(p)
    }

    override fun setStroke(basicStroke: BasicStroke?)
    {
        theGraphics!!.stroke = basicStroke
    }
}