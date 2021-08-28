/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.painter

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics
import com.wang.Game2dEngine.painter.interfaces.IPainter
import com.wang.Game2dEngine.painter.interfaces.IUserInteraction
import com.wang.Game2dEngine.painter.interfaces.IWindow
import java.awt.Component
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ComponentAdapter
import java.awt.event.MouseListener
import java.awt.image.BufferedImage

/**
 * @author ricolwang
 */
open class Painter : IPainter, IUserInteraction, IWindow
{
    private val panel = Panel()
    protected var theImage: BufferedImage? = null
    protected var theEngineGraphics: IEngineGraphics? = null
    override val height: Int
        get() = panel.height
    override val width: Int
        get() = panel.width
    override val component: Component
        get() = panel

    override fun addMouseListener(listener: MouseListener?)
    {
        panel.addMouseListener(listener)
    }

    override fun addComponentListener(componentAdapter: ComponentAdapter?)
    {
        panel.addComponentListener(componentAdapter)
    }

    override fun setSize(d: Dimension?)
    {
        panel.size = d
    }

    fun render()
    {
        panel.render()
    }

    val renderGraphics: Graphics?
        get() = panel.renderGraphics

    open fun painterSizeDidChanged()
    {
    }
}