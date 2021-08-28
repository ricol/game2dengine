/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.physics.collision

import com.wang.Game2dEngine.common.Game2dEngineShared.TypeCollisionDetection
import com.wang.Game2dEngine.physics.sprites.WallSprite
import com.wang.Game2dEngine.physics.sprites.WallSprite.WALLTYPE
import com.wang.Game2dEngine.sprite.Sprite
import com.wang.math.common.MathConsts
import com.wang.math.equation.CollisionQuadraticEquation
import com.wang.math.geometry.CircledShape
import com.wang.math.geometry.ConfinedShape
import com.wang.math.geometry.Point
import com.wang.math.vector.Vector
import java.util.*

/**
 * @author ricolwang
 */
object PhysicsCollisionProcess
{
    fun collisionDetectionBasedOnCategory(nodes: ArrayList<Sprite?>)
    {
        for (aSprite in nodes)
        {
            if (aSprite == null) continue
            if (!aSprite.bCollisionDetect)
            {
                continue
            }
            if (aSprite.collisionTargetCategory <= 0)
            {
                continue
            }
            for (aTargetSprite in nodes)
            {
                if (aTargetSprite == null) continue
                if (aSprite == aTargetSprite)
                {
                    continue
                }

                //the target allows to be detected
                if (!aTargetSprite.bCollisionDetect)
                {
                    continue
                }

                //the target belongs to the group
                if (!aSprite.isInTheTargetCollisionCategory(aTargetSprite.collisionCategory))
                {
                    continue
                }

                //collide with this sprite or not.
                detectCollision(aSprite, aTargetSprite)
            }
        }
    }

    fun collisionDetectionArbitrary(nodes: ArrayList<Sprite?>)
    {
        for (aSprite in nodes)
        {
            if (aSprite == null) continue
            if (!aSprite.bCollisionDetect)
            {
                continue
            }
            if (!aSprite.bCollisionArbitrary)
            {
                continue
            }
            for (aTargetSprite in nodes)
            {
                if (aTargetSprite == null) continue
                if (aSprite == aTargetSprite)
                {
                    continue
                }

                //the target allows to be detected
                if (!aTargetSprite.bCollisionArbitrary)
                {
                    continue
                }

                //collide with this sprite or not.
                detectCollision(aSprite, aTargetSprite)
            }
        }
    }

    fun processCollision(A: Sprite, B: Sprite)
    {
        val theShapeOfA = A.theShape
        val theShapeOfB = B.theShape
        if (theShapeOfA is CircledShape && theShapeOfB is CircledShape)
        {
            //a circle collide with a circle
            val AB = Vector(B.centreX - A.centreX, B.centreY - A.centreY)
            if (AB.theMagnitude <= 0)
            {
                return
            }
            var BC = AB.perpendicularUnitVectorClockwise
            val V_A = Vector(A.velocityX, A.velocityY)
            val cosBC_V_A = BC.getCosValueForAngleToVector(V_A)
            if (cosBC_V_A < 0)
            {
                BC = AB.perpendicularUnitVectorCounterClockwise
            }
            val UNIT_AB = AB.theUnitVector
            val V_A_AB = V_A.getProjectVectorOn(UNIT_AB)
            val V_B = Vector(B.velocityX, B.velocityY)
            val V_B_AB = V_B.getProjectVectorOn(UNIT_AB)
            var absV_A_AB = V_A_AB.theMagnitude
            if (V_A.getCosValueForAngleToVector(AB) < 0)
            {
                absV_A_AB = -absV_A_AB
            }
            var absV_B_AB = V_B_AB.theMagnitude
            if (V_B.getCosValueForAngleToVector(AB) < 0)
            {
                absV_B_AB = -absV_B_AB
            }
            val aEquation = CollisionQuadraticEquation(A.mass, B.mass, absV_A_AB, absV_B_AB)
            val resultAbsV_A_AB = aEquation.newVelocityAlternative
            val resultAbsV_B_AB = aEquation.theOtherObjectVelocityAlternative
            val RESULT_V_A_AB = UNIT_AB.multiplyNumber(resultAbsV_A_AB)
            val UNIT_BC = BC.theUnitVector
            val V_A_BC = V_A.getProjectVectorOn(UNIT_BC)
            val RESULT_V_A = RESULT_V_A_AB.addVector(V_A_BC)
            A.velocityX = RESULT_V_A.x * B.friction
            A.velocityY = RESULT_V_A.y * B.friction
            val RESULT_V_B_AB = UNIT_AB.multiplyNumber(resultAbsV_B_AB)
            val V_B_BC = V_B.getProjectVectorOn(UNIT_BC)
            val RESULT_V_B = RESULT_V_B_AB.addVector(V_B_BC)
            B.velocityX = RESULT_V_B.x * A.friction
            B.velocityY = RESULT_V_B.y * A.friction
            A.targetCollisionProcessed = true
            B.restorePosition()
            A.restorePosition()
        } else if (B is WallSprite)
        {
            //any shape collide to a wall
            val aWall = B
            if (aWall.wallType == WALLTYPE.LEFT)
            {
                A.velocityX = -A.velocityX * B.friction
            } else if (aWall.wallType == WALLTYPE.RIGHT)
            {
                A.velocityX = -A.velocityX * B.friction
            } else if (aWall.wallType == WALLTYPE.TOP)
            {
                A.velocityY = -A.velocityY * B.friction
            } else if (aWall.wallType == WALLTYPE.BOTTOM)
            {
                A.velocityY = -A.velocityY * B.friction
            }
            A.restorePosition()
        } else
        {
            println("Warning: Shape Collision <$theShapeOfA VS $theShapeOfB not implemented!")
        }
    }

    fun isCollide(theSprite: Sprite, theTarget: Sprite): Boolean
    {
        val theShape = theSprite.theShape
        val theTargetShape = theTarget.theShape
        var bResult = false
        if (theShape is ConfinedShape && theTargetShape is ConfinedShape)
        {
            bResult = (theShape as ConfinedShape).collideWith(theTargetShape as ConfinedShape)
        }
        return bResult
    }

    fun detectCollision(theSprite: Sprite, theTarget: Sprite)
    {
        val value: TypeCollisionDetection
        if (isCollide(theSprite, theTarget))
        {
            //collide
            value = TypeCollisionDetection.COLLIDED
            if (theSprite.hashCollision[theTarget] != value)
            {
                theSprite.onCollideWith(theTarget)
                theSprite.hashCollision[theTarget] = value
            }
            if (theTarget.hashCollision[theSprite] != value)
            {
                theTarget.hashCollision[theSprite] = value
                if (!theSprite.targetCollisionProcessed)
                {
                    theTarget.onCollideWith(theSprite)
                }
                theSprite.targetCollisionProcessed = false
            }
        } else
        {
            //uncollide
            value = TypeCollisionDetection.UNCOLLIDED
            if (theSprite.hashCollision[theTarget] != value)
            {
                theSprite.onNotCollideWith(theTarget)
                theSprite.hashCollision[theTarget] = value
            }
            if (theTarget.hashCollision[theSprite] != value)
            {
                theTarget.hashCollision[theSprite] = value
                if (!theSprite.targetCollisionProcessed)
                {
                    theTarget.onNotCollideWith(theSprite)
                }
                theSprite.targetCollisionProcessed = false
            }
        }
    }

    fun getCollisionPointsForCircle(A: CircledShape, B: CircledShape): ArrayList<Point>
    {
        val points = ArrayList<Point>()
        if (A.centre == B.centre && Math.abs(A.radius - B.radius) < MathConsts.Minimum)
        {
            return points
        }
        val r1 = A.radius
        val r2 = B.radius
        val r = A.centre.getDistanceFrom(B.centre)
        val p = (r1 + r2 + r) / 2.0
        val s = Math.sqrt(Math.abs(p * (p - r1) * (p - r2) * (p - r)))
        val sin = 2 * s / (r1 * r)
        val angel = Math.asin(sin)
        val V_AB = Vector(B.centre.x - A.centre.x, B.centre.y - A.centre.y)
        val V_AB_ROTATE_CLOCK_WISE = V_AB.getVectorRotateByInClockwise(angel)
        val V_AB_ROTATE_CLOCK_WISE_UNIT = V_AB_ROTATE_CLOCK_WISE.theUnitVector
        val V_AC_CLOCK_WISE = V_AB_ROTATE_CLOCK_WISE_UNIT.multiplyNumber(r1)
        V_AC_CLOCK_WISE.start = A.centre
        val p1 = V_AC_CLOCK_WISE.theEndPoint
        val V_AB_ROTATE_COUNTER_CLOCK_WISE = V_AB.getVectorRotateByInCounterClockwise(angel)
        val V_AB_ROTATE_COUNTER_CLOCK_WISE_UNIT = V_AB_ROTATE_COUNTER_CLOCK_WISE.theUnitVector
        val V_AC_COUNTER_CLOCK_WISE = V_AB_ROTATE_COUNTER_CLOCK_WISE_UNIT.multiplyNumber(r1)
        V_AC_COUNTER_CLOCK_WISE.start = A.centre
        val p2 = V_AC_COUNTER_CLOCK_WISE.theEndPoint
        points.add(p1)
        val t = Vector(p2.x - p1.x, p2.y - p1.y)
        if (t.theMagnitude > 1e-1)
        {
            points.add(p2)
        }
        return points
    }
}