/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.sprite

import com.wang.Game2dEngine.Cache.ResourceCache
import com.wang.Game2dEngine.action.Action
import com.wang.Game2dEngine.common.Game2dEngineShared.TypeCollisionDetection
import com.wang.Game2dEngine.painter.EngineGraphics
import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics
import com.wang.Game2dEngine.physics.collision.PhysicsCollisionProcess
import com.wang.Game2dEngine.physics.gravity.Gravity
import com.wang.Game2dEngine.scene.Layer
import com.wang.Game2dEngine.scene.Scene
import com.wang.math.common.MathConsts
import com.wang.math.geometry.Line
import com.wang.math.vector.Vector
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO

/**
 * @author ricolwang
 */
abstract class Sprite @JvmOverloads constructor(x: Double = 0.0, y: Double = 0.0, width: Double = 0.0, height: Double = 0.0, velocityX: Double = 0.0, velocityY: Double = 0.0) : Node(x, y, width, height)
{
    var bChild = false
    var parent: Sprite? = null
    var theScene: Scene? = null
    var bDrawFrame = false
    var bDrawShape = false
    var bCustomDrawing = false
    var bDeadIfNoActions = false
    var bCollisionArbitrary = false
    var theColorOfFrame = Color.yellow
    var theColorOfTheShape = Color.red
    var theColorOfVelocityVector = Color.white
    var theColorOfGravityVector = Color.green
    var theColorOfAccelarationVector = Color.orange
    var bCollisionDetect = false
    var bEnablePhysics = false
    var bEnableGravity = false
    var bKillWhenOutOfScene = false
    var bDrawVelocityVector = false
    var bDrawGravityVector = false
    var bDrawAccelarationVector = false
    var DrawVelocityBase = 1.0
    var DrawGravityBase = 1.0
    var DrawAccelarationBase = 1.0
    var friction = 0.9
    var hashCollision = WeakHashMap<Sprite?, TypeCollisionDetection?>()
    var targetCollisionProcessed = false
    var layer: Int = Scene.Companion.MIN_LAYER
        set(layer)
        {
            if (layer >= Scene.Companion.MIN_LAYER && layer <= Scene.Companion.MAX_LAYER)
            {
                field = layer
            }
        }
    open var life = EVER.toDouble() //in seconds
    private var lastUpdateTime: Double
    val startTime = System.currentTimeMillis().toDouble()
    private val accelarationChange = Vector(0.0, 0.0)
    private val velocityChange = Vector(0.0, 0.0)
    private val change = Vector(0.0, 0.0)
    var velocityAngle = 0.0
    private var currentLife = 0.0
    var isAlive = true
        private set
    var shouldDie = false
        private set
    var collisionCategory = 0x00
        set(theCollisionCategory)
        {
            if (isValidCategory(theCollisionCategory))
            {
                field = theCollisionCategory
            } else
            {
                println("Invalid collision category!")
            }
        }
    var collisionTargetCategory = 0x00
        private set
    var mass: Double = 0.0
        set(value)
        {
            if (mass >= 0)
            {
                field = value
            }
        }
    //sets for actions and each action will execute in parallel.
    private val theSetOfActionsWillDelete: MutableSet<Action> = HashSet()
    private val theSetOfActionsWillAdd: MutableSet<Action> = HashSet()
    private val theSetOfActions: MutableSet<Action> = HashSet()
    private val theSetOfActionsInQueueWillDelete: MutableSet<Action> = HashSet()

    //queue for sets of actions and each set of actions will execute in sequence.
    private val theQueueOfActions: Queue<MutableSet<Action>> = LinkedList()

    //sets for children that will be drawed inside the sprite.
    private val theSetOfChildrenWillDelete: MutableSet<Sprite> = HashSet()
    private val theSetOfChildrenWillAdd: MutableSet<Sprite> = HashSet()
    private val theSetOfChildren: MutableSet<Sprite> = HashSet()

    //sets for attached children that will be drawed in the scene and they move as the sprite moves.
    private val theSetOfAttachedWillDelete: MutableSet<Sprite> = HashSet()
    private val theSetOfAttachedWillAdd: MutableSet<Sprite> = HashSet()
    private val theSetOfAttached: MutableSet<Sprite> = HashSet()
    var gravity: Gravity? = null
        private set
    var angle = 0.0
    var color: Color? = null
        get() = Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha)
        private set
    private var theImageCanvas: BufferedImage? = null
    private var theGraphics: Graphics2D? = null
    private var theImage: BufferedImage? = null
    private val blackTransparent = Color(0, 0, 0, 0)
    var theEngineGraphics = EngineGraphics()

    constructor(x: Double, y: Double, width: Double, height: Double, mass: Double, velocityX: Double, velocityY: Double, velocityAngle: Double) : this(x, y, width, height, velocityX, velocityY)
    {
        this.mass = mass
        this.velocityAngle = velocityAngle
    }

    constructor(imagename: String?) : this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    {
        theImage = imagename?.let { ResourceCache.Companion.sharedInstance.getImage(it) }
        if (theImage == null)
        {
            println("$imagename not found!")
        }
        initForImage()
    }

    fun willUpdateState()
    {
    }

    open fun updateState(currentTime: Double)
    {
        val delta = currentTime - lastUpdateTime
        lastUpdateTime = currentTime
        if (theScene != null)
        {
            if (theScene!!.isScenePaused)
            {
                return
            }
        }
        if (shouldDie)
        {
            setDead()
            return
        }

        //how much time passed since last update
        val t = delta / 1000.0f //in seconds
        currentLife += t
        if (currentLife >= life)
        {
            setShouldDie()
        }

        //update state
        if (gravity != null && bEnableGravity)
        {
            velocityChange.x = gravity!!.GX * t
            velocityChange.y = gravity!!.GY * t
            velocity.x += velocityChange.x
            velocity.y += velocityChange.y
        }

        //apply accelaration
        velocityChange.x = acceleration.x * t
        velocityChange.y = acceleration.y * t
        velocity.x += velocityChange.x
        velocity.y += velocityChange.y
        change.x = velocity.x * t
        change.y = velocity.y * t
        x += change.x
        y += change.y
        val IncAngle = velocityAngle * t
        angle += IncAngle

        //perform actions
        //perform a set of actions in the queue one by one in sequence
        val theSetOfQueuedActions = theQueueOfActions.peek()
        if (theSetOfQueuedActions != null)
        {
            if (theSetOfQueuedActions.size > 0)
            {
                for (aAction in theSetOfQueuedActions)
                {
                    onActionRunning(aAction)
                    aAction.perform(delta)
                    if (aAction.bComplete)
                    {
                        theSetOfActionsInQueueWillDelete.add(aAction)
                        onActionComplete(aAction)
                        aAction.clearSprite()
                    }
                }
                if (theSetOfActionsInQueueWillDelete.size > 0)
                {
                    theSetOfQueuedActions.removeAll(theSetOfActionsInQueueWillDelete)
                    theSetOfActionsInQueueWillDelete.clear()
                }
            } else
            {
                theQueueOfActions.remove(theSetOfQueuedActions)
            }
        }

        //perform other actions
        //add new actions
        if (theSetOfActionsWillAdd.size > 0)
        {
            theSetOfActions.addAll(theSetOfActionsWillAdd)
            theSetOfActionsWillAdd.clear()
        }
        if (theSetOfActions.size <= 0 && theQueueOfActions.size <= 0)
        {
            if (bDeadIfNoActions)
            {
                setShouldDie()
            }
        } else
        {
            //run actions
            for (aAction in theSetOfActions)
            {
                onActionRunning(aAction)
                aAction.perform(delta)
                if (aAction.bComplete)
                {
                    theSetOfActionsWillDelete.add(aAction)
                    onActionComplete(aAction)
                    aAction.clearSprite()
                }
            }
        }

        //delete old actions
        if (theSetOfActionsWillDelete.size > 0)
        {
            theSetOfActions.removeAll(theSetOfActionsWillDelete)
            theSetOfActionsWillDelete.clear()
        }

        //add new children
        if (theSetOfChildrenWillAdd.size > 0)
        {
            theSetOfChildren.addAll(theSetOfChildrenWillAdd)
            theSetOfChildrenWillAdd.clear()
        }

        //update its children
        for (aSprite in theSetOfChildren)
        {
            aSprite.willUpdateState()
            aSprite.updateState(currentTime)
            aSprite.didUpdateState()
            if (!aSprite.isAlive)
            {
                theSetOfChildrenWillDelete.add(aSprite)
            }
        }

        //delete old children
        if (theSetOfChildrenWillDelete.size > 0)
        {
            theSetOfChildren.removeAll(theSetOfChildrenWillDelete)
            theSetOfChildrenWillDelete.clear()
        }

        //update its attached children
        //add new attached
        if (theSetOfAttachedWillAdd.size > 0)
        {
            theSetOfAttached.addAll(theSetOfAttachedWillAdd)
            theSetOfAttachedWillAdd.clear()
        }

        //update its attached
        for (aSprite in theSetOfAttached)
        {
            if (!aSprite.isAlive)
            {
                theSetOfAttachedWillDelete.add(aSprite)
            }
        }

        //delete old attached
        if (theSetOfAttachedWillDelete.size > 0)
        {
            theSetOfAttached.removeAll(theSetOfAttachedWillDelete)
            theSetOfAttachedWillDelete.clear()
        }
    }

    open fun didUpdateState()
    {
        if (bKillWhenOutOfScene)
        {
            if (!isAlive)
            {
                return
            }
            val tmpX = x.toInt()
            val tmpY = y.toInt()
            val tmpW = width.toInt()
            val tmpH = height.toInt()
            val tmpSceneWidth: Int
            val tmpSceneHeight: Int
            if (bChild)
            {
                tmpSceneWidth = parent!!.width.toInt()
                tmpSceneHeight = parent!!.height.toInt()
            } else
            {
                tmpSceneWidth = theScene!!.width
                tmpSceneHeight = theScene!!.height
            }
            if (tmpX + tmpW < 0 || tmpY + tmpH < 0)
            {
                setShouldDie()
            }
            if (tmpX > tmpSceneWidth || tmpY > tmpSceneHeight)
            {
                setShouldDie()
            }
            if (Math.abs(tmpW) <= 0.1 || Math.abs(tmpH) <= 0.1)
            {
                setShouldDie()
            }
        }
    }

    fun didCollisionProcess()
    {
    }

    fun didFinishUpdateState()
    {
    }

    fun willUpdateGUI()
    {
    }

    fun updateGUI(theGraphicsInTheScene: IEngineGraphics?)
    {
        if (!isAlive)
        {
            return
        }
        val tmpX = x.toInt()
        val tmpY = y.toInt()
        val tmpW = width.toInt()
        val tmpH = height.toInt()
        val tmpSceneWidth: Int
        val tmpSceneHeight: Int
        if (bChild)
        {
            if (parent == null)
            {
                return
            }
            tmpSceneWidth = parent!!.width.toInt()
            tmpSceneHeight = parent!!.height.toInt()
        } else
        {
            if (theScene == null)
            {
                return
            }
            tmpSceneWidth = theScene!!.width
            tmpSceneHeight = theScene!!.height
        }
        if (tmpX + tmpW < 0 || tmpY + tmpH < 0)
        {
            return
        }
        if (tmpX > tmpSceneWidth || tmpY > tmpSceneHeight)
        {
            return
        }
        if (Math.abs(tmpW) <= 0.1 || Math.abs(tmpH) <= 0.1)
        {
            return
        }
        if (theGraphicsInTheScene == null)
        {
            return
        }
        theEngineGraphics.theGraphics = theImageGraphics
        val theEngineGraphicsI: IEngineGraphics = theEngineGraphics
        if (bCustomDrawing)
        {
            onCustomDraw(theEngineGraphicsI)
        } else
        {
            //clear background
            theEngineGraphicsI.setBackground(blackTransparent)
            theEngineGraphicsI.clearRect(0, 0, tmpW, tmpH)
            val old = theEngineGraphicsI.transform

            //rotate the angle
            theEngineGraphicsI.rotate(angle, tmpW / 2.0f, tmpH / 2.0f)

            //draw itself
            if (theImage != null)
            {
                //draw the image
                val tmpImageWidth = theImage!!.width
                val tmpImageHeight = theImage!!.height
                val tmpImagePosX = ((width - tmpImageWidth) / 2.0f).toInt()
                val tmpImagePosY = ((height - tmpImageHeight) / 2.0f).toInt()
                theEngineGraphicsI.drawImage(theImage, tmpImagePosX, tmpImagePosY, tmpImageWidth, tmpImageHeight)
            } else
            {
                //fill
                theEngineGraphicsI.setColor(color)
                theEngineGraphicsI.drawRect(0, 0, tmpW - 1, tmpH - 1)
            }

            //draw its children
            for (aSprite in theSetOfChildren)
            {
                aSprite.willUpdateGUI()
                aSprite.updateGUI(theEngineGraphicsI)
                aSprite.didUpdateGUI()
            }

            //restore
            theEngineGraphicsI.transform = old
        }

        //draw its attached children
        for (aSprite in theSetOfAttached)
        {
            aSprite.updateGUI(theGraphicsInTheScene)
        }
        drawFrame(theEngineGraphicsI)
        drawShape(theGraphicsInTheScene)
        drawVelocityVector(theGraphicsInTheScene)
        drawGravityVector(theGraphicsInTheScene)
        drawAccelarationVector(theGraphicsInTheScene)
        onCustomDrawInTheScene(theGraphicsInTheScene)
        if (theImageCanvas != null)
        {
            val old = theGraphicsInTheScene.getComposite()
            val ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)
            theGraphicsInTheScene.setComposite(ac)
            theGraphicsInTheScene.drawImage(theImageCanvas, x.toInt(), y.toInt())
            theGraphicsInTheScene.setComposite(old)
        }
    }

    fun didUpdateGUI()
    {
    }

    private fun initForImage()
    {
        width = theImage!!.width.toDouble()
        height = theImage!!.height.toDouble()
    }

    private fun getTheImageCanvas(): BufferedImage
    {
        if (theImageCanvas == null)
        {
            theImageCanvas = BufferedImage(Math.abs(width.toInt()), Math.abs(height.toInt()), BufferedImage.TYPE_INT_ARGB)
        }
        return theImageCanvas!!
    }

    private val theImageGraphics: Graphics2D?
        private get()
        {
            if (theGraphics == null)
            {
                theGraphics = getTheImageCanvas().createGraphics()
            }
            return theGraphics
        }

    private fun releaseTheImageCanvas()
    {
        theGraphics = null
        theImageCanvas = null
    }

    private fun drawFrame(theGraphics2D: IEngineGraphics)
    {
        if (bDrawFrame)
        {
            theGraphics2D.setColor(theColorOfFrame)
            theGraphics2D.drawRect(0, 0, width.toInt() - 1, height.toInt() - 1)
        }
    }

    private fun drawShape(theGraphics2D: IEngineGraphics)
    {
        if (bDrawShape)
        {
            val theShape = theShape
            theShape?.draw(theGraphics2D, theColorOfTheShape)
        }
    }

    private fun drawVelocityVector(theGraphics2D: IEngineGraphics)
    {
        if (bDrawVelocityVector)
        {
            theGraphics2D.setColor(theColorOfVelocityVector)
            val v = velocity.multiplyNumber(DrawVelocityBase)
            if (v.theMagnitude <= MathConsts.Minimum)
            {
                return
            }
            v.start.x = this.centreX
            v.start.y = this.centreY
            val aLine = Line(v.start, v.theEndPoint)
            val lines = aLine.getArrowLines(10.0, Math.PI / 4.0)
            lines.add(aLine)
            for (line in lines)
            {
                theGraphics2D.drawLine(line.start.x.toInt(), line.start.y.toInt(), line.end.x.toInt(), line.end.y.toInt())
            }
        }
    }

    private fun drawGravityVector(theGraphics2D: IEngineGraphics)
    {
        if (bDrawGravityVector)
        {
            theGraphics2D.setColor(theColorOfGravityVector)
            if (gravity == null)
            {
                return
            }
            val G = Vector(gravity!!.GX, gravity!!.GY)
            val v = G.multiplyNumber(DrawGravityBase)
            if (v.theMagnitude <= 0)
            {
                return
            }
            v.start.x = this.centreX
            v.start.y = this.centreY
            val aLine = Line(v.start, v.theEndPoint)
            val lines = aLine.getArrowLines(10.0, Math.PI / 4.0)
            lines.add(aLine)
            for (line in lines)
            {
                theGraphics2D.drawLine(line.start.x.toInt(), line.start.y.toInt(), line.end.x.toInt(), line.end.y.toInt())
            }
        }
    }

    private fun drawAccelarationVector(theGraphics2D: IEngineGraphics)
    {
        if (bDrawAccelarationVector)
        {
            theGraphics2D.setColor(theColorOfAccelarationVector)
            val A = acceleration.multiplyNumber(DrawAccelarationBase)
            if (A.theMagnitude <= 0)
            {
                return
            }
            A.start.x = this.centreX
            A.start.y = this.centreY
            val aLine = Line(A.start, A.theEndPoint)
            val lines = aLine.getArrowLines(10.0, Math.PI / 4.0)
            lines.add(aLine)
            for (line in lines)
            {
                theGraphics2D.drawLine(line.start.x.toInt(), line.start.y.toInt(), line.end.x.toInt(), line.end.y.toInt())
            }
        }
    }

    private fun setDead()
    {
        if (!isAlive)
        {
            return
        }
        isAlive = false
        onDead()
    }

    protected fun setShouldDie()
    {
        if (shouldDie)
        {
            return
        }
        shouldDie = true
        for (aSprite in theSetOfChildren)
        {
            aSprite.setShouldDie()
        }
        for (aSprite in theSetOfAttached)
        {
            aSprite.setShouldDie()
        }
        onWillDead()
    }

    fun applyGravity(theG: Gravity?)
    {
        gravity = if (theG != null)
        {
            Gravity(theG.GX, theG.GY)
        } else
        {
            null
        }
    }

    fun addAction(aAction: Action)
    {
        aAction.setSprite(this)
        theSetOfActionsWillAdd.add(aAction)
    }

    fun enQueueActions(aSetOfActions: MutableSet<Action>)
    {
        for (aAction in aSetOfActions)
        {
            aAction.setSprite(this)
        }
        theQueueOfActions.add(aSetOfActions)
    }

    fun removeActions(aSetOfActions: MutableSet<Action>)
    {
        for (aAction in aSetOfActions)
        {
            aAction.setSprite(null)
        }
        theQueueOfActions.remove(aSetOfActions)
    }

    val actionCount: Int
        get() = theSetOfActions.size + theQueueOfActions.size
    var velocityX: Double
        get() = velocity.x
        set(value)
        {
            velocity.x = value
        }
    var velocityY: Double
        get() = velocity.y
        set(value)
        {
            velocity.y = value
        }

    var velocity: Vector = Vector(0.0, 0.0)
        set(value)
        {
            field.x = value.x
            field.y = value.y
        }

    var acceleration: Vector = Vector(0.0, 0.0)
        set(value)
        {
            field.x = value.x
            field.y = value.y
        }

    var accelarationX: Double
        get() = acceleration.x
        set(value)
        {
            acceleration.x = value
        }
    var accelarationY: Double
        get() = acceleration.y
        set(value)
        {
            acceleration.y = value
        }

    fun gravityEnabled(): Boolean
    {
        return bEnableGravity
    }

    fun setImage(imageName: String?)
    {
        theImage = try
        {
            ImageIO.read(File(imageName))
        } catch (e: IOException)
        {
            null
        }
    }

    override var x: Double
        set(value)
        {
            if (theSetOfAttached != null)
            {
                for (aSprite in theSetOfAttached)
                {
                    aSprite.x = aSprite.x + (value - this.x)
                }
            }
            super.x = value
        }
        get() = super.x

    override var y: Double
        set(value)
        {
            if (theSetOfAttached != null)
            {
                for (aSprite in theSetOfAttached)
                {
                    aSprite.y = aSprite.y + (y - this.y)
                }
            }
            super.y = value
        }
        get() = super.y

    override var width: Double
        set(value)
        {
            if (value >= 0)
            {
                super.width = value
                releaseTheImageCanvas()
            }
        }
        get() = super.width

    override var height: Double
        set(value)
        {
            if (value >= 0)
            {
                super.height = value
                releaseTheImageCanvas()
            }
        }
        get() = super.height

    var alpha: Float = 1.0f
        set(value)
        {
            if (value in 0.0..1.0)
            {
                field = value
                color = Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha)
            }
        }
        get() = field

    var red: Int = 0
        set(value)
        {
            if (value in 0..255)
            {
                field = value
                color = Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha)
            }
        }
        get() = field

    var green: Int = 0
        set(value)
        {
            if (value in 0..255)
            {
                field = value
                color = Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha)
            }
        }
        get() = field

    var blue: Int = 0
        set(value)
        {
            if (value in 0..255)
            {
                field = value
                color = Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha)
            }
        }
        get() = field

    fun setLifeTime(life: Double)
    {
        this.life = life
    }

    fun addAttached(aSprite: Sprite)
    {
        theSetOfAttachedWillAdd.add(aSprite)
        aSprite.layer = layer
        if (theScene != null)
        {
            if (aSprite.theScene == null)
            {
                theScene!!.addSprite(aSprite)
            }
        }
    }

    fun removeAttached(aSprite: Sprite)
    {
        theSetOfAttachedWillDelete.add(aSprite)
    }

    fun addAChild(aSprite: Sprite)
    {
        theSetOfChildrenWillAdd.add(aSprite)
        aSprite.parent = this
        aSprite.bChild = true
    }

    fun removeAChild(aSprite: Sprite)
    {
        theSetOfChildrenWillDelete.add(aSprite)
        aSprite.parent = null
        aSprite.bChild = false
    }

    fun addTargetCollisionCategory(theTargetCollisionCategory: Int)
    {
        if (isValidCategory(theTargetCollisionCategory))
        {
            collisionTargetCategory = collisionTargetCategory or theTargetCollisionCategory
        }
    }

    fun removeTargetCollisionCategory(theTargetCollisionCategory: Int)
    {
        if (isInTheTargetCollisionCategory(theTargetCollisionCategory))
        {
            collisionTargetCategory = collisionTargetCategory xor theTargetCollisionCategory
        }
    }

    fun isInTheTargetCollisionCategory(theTargetCollisionCategory: Int): Boolean
    {
        return isValidCategory(theTargetCollisionCategory) && collisionTargetCategory and theTargetCollisionCategory > 0
    }

    private fun isValidCategory(aCagegory: Int): Boolean
    {
        return aCagegory >= 0 && (aCagegory % 2 == 0 || aCagegory == 1)
    }

    fun clearChildren()
    {
        theSetOfChildrenWillDelete.addAll(theSetOfChildren)
    }

    open fun onCollideWith(target: Sprite)
    {
        if (bEnablePhysics)
        {
            PhysicsCollisionProcess.processCollision(this, target)
        }
    }

    fun onNotCollideWith(target: Sprite?)
    {
    }

    open fun onAddToLayer(theLayer: Layer?)
    {
    }

    fun onRemovedFromLayer(theLayer: Layer?)
    {
    }

    fun onActionRunning(theAction: Action?)
    {
    }

    open fun onActionComplete(theAction: Action?)
    {
    }

    open fun onCustomDraw(theGraphics2D: IEngineGraphics)
    {
        theGraphics2D.setBackground(blackTransparent)
        theGraphics2D.clearRect(0, 0, width.toInt(), height.toInt())
    }

    fun onCustomDrawInTheScene(theGraphics2D: IEngineGraphics?)
    {
    }

    fun onWillDead()
    {
    }

    open fun onDead()
    {
        hashCollision.clear()
        theSetOfActions.clear()
        theSetOfActionsInQueueWillDelete.clear()
        theSetOfActionsWillAdd.clear()
        theSetOfActionsWillDelete.clear()
        theSetOfChildren.clear()
        theSetOfChildrenWillAdd.clear()
        theSetOfChildrenWillDelete.clear()
        theSetOfAttached.clear()
        theSetOfAttachedWillAdd.clear()
        theSetOfAttachedWillDelete.clear()
        theQueueOfActions.clear()
        releaseTheImageCanvas()
        parent = null
        theScene = null
    }

    override fun toString(): String
    {
        return "class: " + this.javaClass + "; identifier: " + identifier
    }

    fun print(title: String)
    {
        println(title + this)
    }

    fun restoreVelocityX()
    {
        velocity.x -= velocityChange.x
        velocityChange.x = 0.0
    }

    fun restoreVelocityY()
    {
        velocity.y -= velocityChange.y
        velocityChange.y = 0.0
    }

    fun restoreX()
    {
        x -= change.x
        change.x = 0.0
    }

    fun restoreY()
    {
        y -= change.y
        change.y = 0.0
    }

    fun restorePosition()
    {
        restoreX()
        restoreY()
    }

    fun restoreVelocity()
    {
        restoreVelocityX()
        restoreVelocityY()
    }

    fun restoreAccelaration()
    {
        restoreAccelarationX()
        restoreAccelarationY()
    }

    fun restoreAccelarationX()
    {
        accelarationX -= accelarationChange.x
        accelarationChange.x = 0.0
    }

    fun restoreAccelarationY()
    {
        accelarationY -= accelarationChange.y
        accelarationChange.y = 0.0
    }

    companion object
    {
        const val EVER = Long.MAX_VALUE
    }

    init
    {
        velocity.x = velocityX
        velocity.y = velocityY
        lastUpdateTime = System.currentTimeMillis().toDouble()
    }
}