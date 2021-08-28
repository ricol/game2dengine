/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.painter.interfaces

import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

/**
 * @author ricolwang
 */
interface IEngineGraphics
{
    fun drawString(text: String?, x: Float, y: Float)
    fun drawImage(img: Image?, x: Int, y: Int, width: Int, height: Int)
    fun setColor(color: Color?)
    fun fillRect(x: Int, y: Int, width: Int, height: Int)
    fun dispose()
    fun drawRect(x: Int, y: Int, width: Int, height: Int)
    fun drawLine(x0: Int, y0: Int, x1: Int, y1: Int)
    fun fillArc(x: Int, y: Int, width: Int, height: Int, startAngel: Int, endAngel: Int)
    fun drawArc(x: Int, y: Int, width: Int, height: Int, startAngel: Int, endAngel: Int)
    fun setBackground(blackTransparent: Color?)
    fun clearRect(x: Int, y: Int, width: Int, height: Int)
    var transform: AffineTransform?
    fun rotate(theta: Double, x: Float, y: Float)
    fun getComposite(): Composite?
    fun setComposite(ac: AlphaComposite?)
    fun drawImage(image: BufferedImage?, x: Int, y: Int)
    fun setComposite(c: Composite?)
    fun setFont(f: Font?)
    fun fillOval(x: Int, y: Int, width: Int, height: Int)
    fun drawPolygon(p: Polygon?)
    fun setStroke(basicStroke: BasicStroke?)
}