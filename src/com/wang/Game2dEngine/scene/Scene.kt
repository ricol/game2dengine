/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.scene

import com.wang.Game2dEngine.painter.EngineGraphics
import com.wang.Game2dEngine.painter.Painter
import com.wang.Game2dEngine.physics.collision.PhysicsCollisionProcess
import com.wang.Game2dEngine.sprite.Sprite
import java.awt.Color
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.image.BufferedImage
import java.text.NumberFormat
import java.util.*
import javax.swing.Timer

/**
 * @author ricolwang
 */
open class Scene : Painter(), Runnable
{
    var bShowMemoryUsage = true
    var theImageBackground: BufferedImage? = null
    var isScenePaused = false
        private set
    private var bQuit = false
    private var bRunning = false
    private var _red = 0
    private var _green = 0
    private var _blue = 0
    private var theBackgroundColor = Color(red, green, blue)
    private var bEnableCollisionDetect = false
    private var lastFPSTime = System.currentTimeMillis()
    private val FPS_UPDATE_INTERVAL = 500
    private var lastUpdateFPSTime = System.currentTimeMillis()
    private var fps: Long = 0
    private var timeEllapsed = 0f
    private var actionCount: Long = 0
    private var FPS: Long = 200
    private var strMemoryUsage = ""
    private var g: Graphics? = null
    private var text: String? = null
    private var delta: Long = 0
    private var currentTime = System.currentTimeMillis().toDouble()
    protected var theRandom = Random()
    private var theThread: Thread? = null
    private val layers = HashMap<Int, Layer>()
    private val allNodes = ArrayList<Sprite?>()
    private val allInLoop = ArrayList<Sprite?>()
    private val theTimer = Timer(10) { e: ActionEvent? -> timeEllapsed += 0.01f }
    private val theTimerForMemory = Timer(500) { e: ActionEvent? -> collectMemoryInfo() }
    override fun painterSizeDidChanged()
    {
        super.painterSizeDidChanged() //To change body of generated methods, choose Tools | Templates.
        if (theImage == null || width <= 0 || height <= 0) return
        synchronized(this) {
            theImage = null
            if (theEngineGraphics != null)
            {
                theEngineGraphics!!.dispose()
            }
            theEngineGraphics = null
            theImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
            val g = EngineGraphics()
            g.theGraphics = theImage!!.createGraphics()
            theEngineGraphics = g
        }
    }

    open fun start()
    {
        if (width > 0 && height > 0)
        {
            if (theImage == null)
            {
                theImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
                if (theEngineGraphics != null)
                {
                    theEngineGraphics!!.dispose()
                }
                theEngineGraphics = null
                val g = EngineGraphics()
                g.theGraphics = theImage!!.createGraphics()
                theEngineGraphics = g
            }
            isScenePaused = false
            if (theThread == null)
            {
                theThread = Thread(this)
            }
            theThread!!.start()
        }
        didStart()
    }

    open fun didStart()
    {
    }

    override fun run()
    {
        if (bRunning) return
        bRunning = true
        while (!bQuit)
        {
            currentTime = System.currentTimeMillis().toDouble()
            delta = (currentTime - lastFPSTime).toLong()
            if (delta > 1000.0 / FPS)
            {
                if ((currentTime - lastUpdateFPSTime).toLong() >= FPS_UPDATE_INTERVAL)
                {
                    fps = (1000.0 / delta).toLong()
                    lastUpdateFPSTime = currentTime.toLong()
                }
                lastFPSTime = currentTime.toLong()
                try
                {
                    updateModel(currentTime)
                    updateGUI(currentTime)
                    g = this.renderGraphics
                    g!!.drawImage(theImage, 0, 0, null)
                    render()
                } catch (e: Exception)
                {
                    println("Exception: Graphics failure!")
                } finally
                {
                    if (g != null)
                    {
                        g!!.dispose()
                        g = null
                    }
                }
            }
        }
        println("Game Loop Run Quit!")
        bRunning = false
    }

    open fun pause()
    {
        isScenePaused = !isScenePaused
    }

    open fun stop()
    {
        theImage = null
        if (theEngineGraphics != null)
        {
            theEngineGraphics!!.dispose()
        }
        theEngineGraphics = null
        isScenePaused = true
        bQuit = true
    }

    private fun addSprite(aSprite: Sprite, zOrder: Int)
    {
        if (zOrder < MIN_LAYER || zOrder > MAX_LAYER)
        {
            return
        }
        var theLayer = layers[zOrder]
        if (theLayer == null)
        {
            theLayer = Layer(zOrder, this)
            theLayer.addSprite(aSprite)
            layers[zOrder] = theLayer
        } else
        {
            theLayer.addSprite(aSprite)
        }
    }

    fun addSprite(aSprite: Sprite)
    {
        addSprite(aSprite, aSprite.layer)
    }

    private fun updateModel(currentTime: Double)
    {
        allInLoop.clear()
        for (i in MIN_LAYER..MAX_LAYER)
        {
            val aLayer = layers[i] ?: continue
            allInLoop.addAll(aLayer.AllObjects)
        }
        for (i in MIN_LAYER..MAX_LAYER)
        {
            val aLayer = layers[i] ?: continue

            //remove all dead sprites
            for (aSprite in aLayer.DeadObjects)
            {
                aSprite!!.theScene = null
            }
            aLayer.AllObjects.removeAll(aLayer.DeadObjects)
            aLayer.DeadObjects.clear()
            actionCount = 0
            //update sprites states
            for (aSprite in aLayer.AllObjects)
            {
                if (aSprite == null) continue
                aSprite.willUpdateState()
                aSprite.updateState(currentTime)
                aSprite.didUpdateState()
                actionCount += aSprite.actionCount.toLong()
                if (!aSprite.isAlive)
                {
                    aLayer.DeadObjects.add(aSprite)
                    aSprite.onRemovedFromLayer(aLayer)
                }
            }
            aLayer.AllObjects.addAll(aLayer.NewObjects)
            aLayer.NewObjects.clear()
        }
        if (bEnableCollisionDetect)
        {
            collisionProcess()
            for (aSprite in allInLoop)
            {
                if (aSprite == null) continue
                aSprite.didCollisionProcess()
            }
        }
        for (aSprite in allInLoop)
        {
            if (aSprite == null) continue
            aSprite.didFinishUpdateState()
        }
        didUpdateModel()
    }

    protected fun didUpdateModel()
    {
    }

    private fun updateGUI(currentTime: Double)
    {
        //update GUI
        if (theEngineGraphics == null)
        {
            allInLoop.clear()
            return
        }
        if (theImageBackground != null)
        {
            theEngineGraphics!!.drawImage(theImageBackground, 0, 0, this.width, this.height)
        } else
        {
            theEngineGraphics!!.setColor(theBackgroundColor)
            theEngineGraphics!!.fillRect(0, 0, this.width, this.height)
        }
        for (aSprite in allInLoop)
        {
            if (aSprite == null) continue
            aSprite.willUpdateGUI()
            aSprite.updateGUI(theEngineGraphics)
            aSprite.didUpdateGUI()
        }

        //draw fps
        text = "FPS: $fps"
        theEngineGraphics!!.setColor(Color.RED)
        theEngineGraphics!!.drawString(text, LEFT_TEXT.toFloat(), TOP_TEXT.toFloat())

        //draw sprites count
        val totalNodes = allInLoop.size
        text = "NODES: $totalNodes"
        theEngineGraphics!!.drawString(text, LEFT_TEXT.toFloat(), TOP_TEXT + GAP_TEXT.toFloat())

        //draw total actions
        text = "ACTIONS: $actionCount"
        theEngineGraphics!!.drawString(text, LEFT_TEXT.toFloat(), TOP_TEXT + GAP_TEXT * 2.toFloat())

        //draw total layers
        var totalLayers = 0
        for (i in MIN_LAYER..MAX_LAYER)
        {
            val aLayer = layers[i] ?: continue
            totalLayers++
        }
        text = "LAYERS: $totalLayers"
        theEngineGraphics!!.drawString(text, LEFT_TEXT.toFloat(), TOP_TEXT + GAP_TEXT * 3.toFloat())

        //draw current time ellapsed
        text = String.format("TIME: %.2f", timeEllapsed)
        theEngineGraphics!!.drawString(text, LEFT_TEXT.toFloat(), this.height - TOP_TEXT.toFloat())
        if (bShowMemoryUsage) theEngineGraphics!!.drawString(strMemoryUsage, LEFT_TEXT.toFloat(), this.height - TOP_TEXT - GAP_TEXT.toFloat())
        allInLoop.clear()
    }

    var red: Int
        set(value)
        {
            if (value >= 0 && value <= 255)
            {
                _red = value
                theBackgroundColor = Color(_red, _green, _blue)
            }
        }
        get() = _red

    var green: Int
        set(value)
        {
            if (value >= 0 && value <= 255)
            {
                _green = value
                theBackgroundColor = Color(_red, _green, blue)
            }
        }
        get() = _green

    var blue: Int
        set(value)
        {
            if (value >= 0 && value <= 255)
            {
                _blue = value
                theBackgroundColor = Color(_red, _green, _blue)
            }
        }
        get() = _blue

    fun collisionDetectEnabled(): Boolean
    {
        return bEnableCollisionDetect
    }

    fun enableCollisionDetect()
    {
        bEnableCollisionDetect = true
    }

    fun disableCollisionDetect()
    {
        bEnableCollisionDetect = false
    }

    val allSprites: ArrayList<Sprite?>
        get()
        {
            val all = ArrayList<Sprite?>()
            for (i in MIN_LAYER..MAX_LAYER)
            {
                val aLayer = layers[i] ?: continue
                all.addAll(aLayer.AllObjects)
            }
            return all
        }

    fun getAllSprites(layer: Int): ArrayList<Sprite?>
    {
        val all = ArrayList<Sprite?>()
        val aLayer = layers[layer]
        if (aLayer != null)
        {
            all.addAll(aLayer.AllObjects)
        }
        return all
    }

    private fun collectMemoryInfo()
    {
        val runtime = Runtime.getRuntime()
        val format = NumberFormat.getInstance()
        val allocatedMemory = runtime.totalMemory()
        strMemoryUsage = "MEM: " + format.format(allocatedMemory / (1024 * 1024)) + " MB"
    }

    private fun collisionProcess()
    {
        allNodes.clear()
        for (i in MIN_LAYER..MAX_LAYER)
        {
            val aLayer = layers[i] ?: continue
            allNodes.addAll(aLayer.AllObjects)
        }
        PhysicsCollisionProcess.collisionDetectionBasedOnCategory(allNodes)
        PhysicsCollisionProcess.collisionDetectionArbitrary(allNodes)
        allNodes.clear()
    }

    fun setFps(fps: Int)
    {
        if (fps > 150) FPS = 150 else FPS = Math.max(fps, 50).toLong()
    }

    companion object
    {
        var MIN_LAYER = 0
        var MAX_LAYER = 9
        private const val LEFT_TEXT: Long = 30
        private const val TOP_TEXT: Long = 45
        private const val GAP_TEXT: Long = 20
    }

    init
    {
        collectMemoryInfo()
        theTimer.start()
        theTimerForMemory.start()
        addComponentListener(object : ComponentAdapter()
        {
            override fun componentResized(evt: ComponentEvent)
            {
                painterSizeDidChanged()
            }
        })
    }
}