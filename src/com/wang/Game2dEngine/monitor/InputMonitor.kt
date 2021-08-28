package com.wang.Game2dEngine.monitor

import java.awt.event.*
import java.util.*

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
/**
 * @author ricolwang
 */
class InputMonitor : MouseListener, KeyListener, MouseMotionListener
{
    interface IKeyTyped
    {
        fun inputMonitorKeyTyped(key: Char)
    }

    var leftButtonPressed = false
    var rightButtonPressed = false
    var middleButtonPressed = false
    var MouseX = 0
    var MouseY = 0
    var mouseEntered = false
    var keyStatus = HashMap<Int, Boolean>()
    var keytypedobserver: MutableSet<IKeyTyped> = HashSet()
    override fun mouseClicked(e: MouseEvent)
    {
    }

    override fun mousePressed(e: MouseEvent)
    {
        when (e.button)
        {
            MouseEvent.BUTTON1 -> leftButtonPressed = true
            MouseEvent.BUTTON2 -> middleButtonPressed = true
            MouseEvent.BUTTON3 -> rightButtonPressed = true
            else ->
            {
            }
        }
    }

    override fun mouseReleased(e: MouseEvent)
    {
        when (e.button)
        {
            MouseEvent.BUTTON1 -> leftButtonPressed = false
            MouseEvent.BUTTON2 -> middleButtonPressed = false
            MouseEvent.BUTTON3 -> rightButtonPressed = false
            else ->
            {
            }
        }
    }

    override fun mouseEntered(e: MouseEvent)
    {
        mouseEntered = true
    }

    override fun mouseExited(e: MouseEvent)
    {
        mouseEntered = false
    }

    override fun keyTyped(e: KeyEvent)
    {
        for (i in keytypedobserver)
        {
            i.inputMonitorKeyTyped(e.keyChar)
        }
    }

    override fun keyPressed(e: KeyEvent)
    {
        updateKeyStatus(e.keyCode, true)
    }

    override fun keyReleased(e: KeyEvent)
    {
        updateKeyStatus(e.keyCode, false)
    }

    override fun mouseDragged(e: MouseEvent)
    {
    }

    override fun mouseMoved(e: MouseEvent)
    {
        MouseX = e.x
        MouseY = e.y
    }

    fun isKeyPressed(key: Int): Boolean
    {
        return keyStatus.getOrDefault(key, false)
    }

    fun updateKeyStatus(key: Int, status: Boolean)
    {
        keyStatus[key] = status
    }

    fun addObserverForKeyTyped(o: IKeyTyped)
    {
        keytypedobserver.add(o)
    }

    fun removeObserverForKeyTyped(o: IKeyTyped)
    {
        keytypedobserver.remove(o)
    }

    companion object
    {
        var sharedInstance = InputMonitor()
    }
}