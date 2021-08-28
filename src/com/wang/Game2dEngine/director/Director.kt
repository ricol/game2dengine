/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.director

import com.wang.Game2dEngine.monitor.InputMonitor
import com.wang.Game2dEngine.scene.Scene
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * @author ricolwang
 */
class Director private constructor()
{
    var currentScene: Scene? = null
    var parent: JPanel? = null
    fun start()
    {
        if (currentScene != null)
        {
            currentScene!!.start()
        }
    }

    fun pause()
    {
        if (currentScene != null)
        {
            currentScene!!.pause()
        }
    }

    fun stop()
    {
        if (currentScene != null)
        {
            currentScene!!.stop()
        }
    }

    @JvmName("setParent1")
    fun setParent(parent: JPanel?)
    {
        this.parent = parent
        this.parent!!.layout = BorderLayout()
    }

    fun showScene(scene: Scene?)
    {
        if (scene == null)
        {
            return
        }
        if (currentScene != null)
        {
            parent?.remove(currentScene!!.component)
            currentScene!!.stop()
            currentScene = null
        }
        currentScene = scene
        if (parent != null)
        {
            scene.component.setLocation(0, 0)
            scene.component.size = Dimension(parent!!.width, parent!!.height)
            parent!!.add(scene.component, BorderLayout.CENTER)
            parent!!.repaint()

            //fix menu hide behind frame issue
            var o: Component = parent!!.parent
            while (o !is JFrame)
            {
                o = o.parent
            }
            o.setSize(o.getSize().width, o.getSize().height + 1)
            o.setSize(o.getSize().width, o.getSize().height - 1)
        }
        scene.start()

        //add InputMonitor as the input listener
        val allListeners = scene.component.keyListeners
        var bAdded = false
        for (l in allListeners)
        {
            if (l === InputMonitor.Companion.sharedInstance)
            {
                bAdded = true
                break
            }
        }
        if (!bAdded)
        {
            scene.component.addKeyListener(InputMonitor.Companion.sharedInstance)
        }
    }

    fun updatePosition(x: Int, y: Int, width: Int, height: Int)
    {
        if (currentScene != null)
        {
            currentScene!!.component.setLocation(x, y)
            currentScene!!.component.size = Dimension(width, height)
        }
    }

    companion object
    {
        var sharedInstance = Director()
    }

    init
    {
        System.setProperty("sun.java2d.opengl", "True")
    }
}