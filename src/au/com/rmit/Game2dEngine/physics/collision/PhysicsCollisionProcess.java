/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.physics.collision;

import au.com.rmit.Game2dEngine.common.Game2dEngineShared;
import au.com.rmit.Game2dEngine.physics.sprites.WallSprite;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.rmit.math.common.MathConsts;
import au.com.rmit.math.equation.CollisionQuadraticEquation;
import au.com.rmit.math.geometry.CircleShape;
import au.com.rmit.math.geometry.ClosureShape;
import au.com.rmit.math.geometry.Point;
import au.com.rmit.math.vector.Vector;
import static java.lang.Math.abs;
import java.util.ArrayList;
import au.com.rmit.Game2dEngine.Shape.Interface.IEShape;

/**
 *
 * @author ricolwang
 */
public class PhysicsCollisionProcess
{

    public static void collisionDetectionBasedOnCategory(ArrayList<Sprite> nodes)
    {
        for (Sprite aSprite : nodes)
        {
            if (!aSprite.bCollisionDetect)
            {
                continue;
            }

            if (aSprite.getCollisionTargetCategory() <= 0)
            {
                continue;
            }

            for (Sprite aTargetSprite : nodes)
            {
                if (aSprite.equals(aTargetSprite))
                {
                    continue;
                }

                //the target allows to be detected
                if (!aTargetSprite.bCollisionDetect)
                {
                    continue;
                }

                //the target belongs to the group
                if (!aSprite.isInTheTargetCollisionCategory(aTargetSprite.getCollisionCategory()))
                {
                    continue;
                }

                //collide with this sprite or not.
                PhysicsCollisionProcess.detectCollision(aSprite, aTargetSprite);
            }
        }
    }

    public static void collisionDetectionArbitrary(ArrayList<Sprite> nodes)
    {
        for (Sprite aSprite : nodes)
        {
            if (!aSprite.bCollisionDetect)
            {
                continue;
            }

            if (!aSprite.bCollisionArbitrary)
            {
                continue;
            }

            for (Sprite aTargetSprite : nodes)
            {
                if (aSprite.equals(aTargetSprite))
                {
                    continue;
                }

                //the target allows to be detected
                if (!aTargetSprite.bCollisionArbitrary)
                {
                    continue;
                }

                //collide with this sprite or not.
                PhysicsCollisionProcess.detectCollision(aSprite, aTargetSprite);
            }
        }
    }

    public static void processCollision(Sprite A, Sprite B)
    {
        IEShape theShapeOfA = A.getTheShape();
        IEShape theShapeOfB = B.getTheShape();

        if (theShapeOfA instanceof CircleShape && theShapeOfB instanceof CircleShape)
        {
            //a circle collide with a circle
            Vector AB = new Vector(B.getCentreX() - A.getCentreX(), B.getCentreY() - A.getCentreY());
            if (AB.getTheMagnitude() <= 0)
            {
                return;
            }

            Vector BC = AB.getPerpendicularUnitVectorClockwise();

            Vector V_A = new Vector(A.getVelocityX(), A.getVelocityY());

            double cosBC_V_A = BC.getCosValueForAngleToVector(V_A);
            if (cosBC_V_A < 0)
            {
                BC = AB.getPerpendicularUnitVectorCounterClockwise();
            }

            Vector UNIT_AB = AB.getTheUnitVector();
            Vector V_A_AB = V_A.getProjectVectorOn(UNIT_AB);

            Vector V_B = new Vector(B.getVelocityX(), B.getVelocityY());
            Vector V_B_AB = V_B.getProjectVectorOn(UNIT_AB);

            double absV_A_AB = V_A_AB.getTheMagnitude();

            if (V_A.getCosValueForAngleToVector(AB) < 0)
            {
                absV_A_AB = -absV_A_AB;
            }

            double absV_B_AB = V_B_AB.getTheMagnitude();

            if (V_B.getCosValueForAngleToVector(AB) < 0)
            {
                absV_B_AB = -absV_B_AB;
            }

            CollisionQuadraticEquation aEquation = new CollisionQuadraticEquation(A.getMass(), B.getMass(), absV_A_AB, absV_B_AB);
            double resultAbsV_A_AB = aEquation.getNewVelocityAlternative();
            double resultAbsV_B_AB = aEquation.getTheOtherObjectVelocityAlternative();

            Vector RESULT_V_A_AB = UNIT_AB.multiplyNumber(resultAbsV_A_AB);
            Vector UNIT_BC = BC.getTheUnitVector();
            Vector V_A_BC = V_A.getProjectVectorOn(UNIT_BC);
            Vector RESULT_V_A = RESULT_V_A_AB.addVector(V_A_BC);

            A.setVelocityX(RESULT_V_A.x);
            A.setVelocityY(RESULT_V_A.y);

            Vector RESULT_V_B_AB = UNIT_AB.multiplyNumber(resultAbsV_B_AB);
            Vector V_B_BC = V_B.getProjectVectorOn(UNIT_BC);
            Vector RESULT_V_B = RESULT_V_B_AB.addVector(V_B_BC);

            B.setVelocityX(RESULT_V_B.x);
            B.setVelocityY(RESULT_V_B.y);

            A.setTargetCollisionProcessed(true);
        } else if (B instanceof WallSprite)
        {
            //any shape collide to a wall
            WallSprite aWall = (WallSprite) B;
            if (aWall.wallType == WallSprite.WALLTYPE.LEFT)
            {
                A.setVelocityX(-A.getVelocityX());
            } else if (aWall.wallType == WallSprite.WALLTYPE.RIGHT)
            {
                A.setVelocityX(-A.getVelocityX());
            } else if (aWall.wallType == WallSprite.WALLTYPE.TOP)
            {
                A.setVelocityY(-A.getVelocityY());
            } else if (aWall.wallType == WallSprite.WALLTYPE.BOTTOM)
            {
                A.setVelocityY(-A.getVelocityY());
            }
        } else
        {
            System.out.println("Warning: Shape Collision <" + theShapeOfA + " VS " + theShapeOfB + " not implemented!");
        }
    }

    public static boolean isCollide(Sprite theSprite, Sprite theTarget)
    {
        IEShape theShape = theSprite.getTheShape();
        IEShape theTargetShape = theTarget.getTheShape();

        boolean bResult = false;
        if ((theShape instanceof ClosureShape) && (theTargetShape instanceof ClosureShape))
        {
            bResult = ((ClosureShape) theShape).collideWith((ClosureShape) theTargetShape);
        }

        return bResult;
    }

    static void detectCollision(Sprite theSprite, Sprite theTarget)
    {
        Game2dEngineShared.TypeCollisionDetection value;

        if (PhysicsCollisionProcess.isCollide(theSprite, theTarget))
        {
            //collide
            value = Game2dEngineShared.TypeCollisionDetection.COLLIDED;
            if (theSprite.hashCollision.get(theTarget) != value)
            {
                theSprite.onCollideWith(theTarget);
                theSprite.hashCollision.put(theTarget, value);
            }

            if (theTarget.hashCollision.get(theSprite) != value)
            {
                theTarget.hashCollision.put(theSprite, value);

                if (!theSprite.getTargetCollisionProcessed())
                {
                    theTarget.onCollideWith(theSprite);
                }

                theSprite.setTargetCollisionProcessed(false);
            }
        } else
        {
            //uncollide
            value = Game2dEngineShared.TypeCollisionDetection.UNCOLLIDED;
            if (theSprite.hashCollision.get(theTarget) != value)
            {
                theSprite.onNotCollideWith(theTarget);
                theSprite.hashCollision.put(theTarget, value);
            }

            if (theTarget.hashCollision.get(theSprite) != value)
            {
                theTarget.hashCollision.put(theSprite, value);

                if (!theSprite.getTargetCollisionProcessed())
                {
                    theTarget.onNotCollideWith(theSprite);
                }

                theSprite.setTargetCollisionProcessed(false);
            }
        }
    }

    public static ArrayList<Point> getCollisionPointsForCircle(CircleShape A, CircleShape B)
    {
        ArrayList<Point> points = new ArrayList<>();

        if (A.centre.equals(B.centre) && abs(A.radius - B.radius) < MathConsts.Minimum)
        {
            return points;
        }

        double r1 = A.radius;
        double r2 = B.radius;
        double r = A.centre.getDistanceFrom(B.centre);

        double cos = (r2 * r2 - r1 * r1 - r * r) / (-2 * r1 * r);
        double angel = Math.acos(cos);
        Vector V_AB = new Vector(B.centre.x - A.centre.x, B.centre.y - A.centre.y);
        Vector V_AB_ROTATE_CLOCK_WISE = V_AB.getVectorRotateByInClockwise(angel);
        Vector V_AB_ROTATE_CLOCK_WISE_UNIT = V_AB_ROTATE_CLOCK_WISE.getTheUnitVector();
        Vector V_AC_CLOCK_WISE = V_AB_ROTATE_CLOCK_WISE_UNIT.multiplyNumber(r1);
        V_AC_CLOCK_WISE.start = A.centre;
        Point p1 = V_AC_CLOCK_WISE.getTheEndPoint();

        Vector V_AB_ROTATE_COUNTER_CLOCK_WISE = V_AB.getVectorRotateByInCounterClockwise(angel);
        Vector V_AB_ROTATE_COUNTER_CLOCK_WISE_UNIT = V_AB_ROTATE_COUNTER_CLOCK_WISE.getTheUnitVector();
        Vector V_AC_COUNTER_CLOCK_WISE = V_AB_ROTATE_COUNTER_CLOCK_WISE_UNIT.multiplyNumber(r1);
        V_AC_COUNTER_CLOCK_WISE.start = A.centre;
        Point p2 = V_AC_COUNTER_CLOCK_WISE.getTheEndPoint();

        points.add(p1);
        if (!p1.equals(p2) && abs(r1 + r2 - r) > MathConsts.Minimum)
        {
            points.add(p2);
        }

        return points;
    }
}
