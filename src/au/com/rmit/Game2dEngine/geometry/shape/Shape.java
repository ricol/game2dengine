/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry.shape;

import au.com.rmit.Game2dEngine.sprite.Node;
import java.awt.Color;
import java.awt.Graphics2D;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class Shape
{

    public static enum CircleRectangleCollideDirection
    {
        FROM_TOP, FROM_LEFT, FROM_BOTTOM, FROM_RIGHT, FROM_INSIDE, FROM_OUTSIDE, FROM_TOP_LEFT, FROM_BOTTOM_LEFT, FROM_BOTTOM_RIGHT, FROM_TOP_RIGHT, NO
    };
    Node theNode;

    public void print(String text)
    {

    }

    public void refresh()
    {

    }

    public void draw(Graphics2D theGraphicsInTheScene, Color theColor)
    {

    }

    public static boolean CircleCollideWithCircle(CircleShape A, CircleShape B)
    {
        double delX = A.centreX - B.centreX;
        double delY = A.centreY - B.centreY;
        double distance = Math.sqrt(delX * delX + delY * delY);
        double targetRadius = B.radius;
        double thisRadius = A.radius;
        return distance < targetRadius + thisRadius;
    }

    public static boolean CircleCollideWithRectangle(CircleShape A, RectangleShape B)
    {
        CircleRectangleCollideDirection theDirection = Shape.CircleCollideWithRectangleFromDirection(A, B);
        return theDirection != CircleRectangleCollideDirection.NO;
    }

    public static CircleRectangleCollideDirection CircleCollideWithRectangleFromDirection(CircleShape A, RectangleShape B)
    {
        double a = A.centreX;
        double b = A.centreY;
        double r = A.radius;
        double x = B.left;
        double y = B.top;
        double w = B.width;
        double h = B.height;

        if (abs(y - b) <= r && x <= a && a <= x + w)
            return CircleRectangleCollideDirection.FROM_TOP; //A collide from top to the B
        if (abs(x - a) <= r && y <= b && b <= y + h)
            return CircleRectangleCollideDirection.FROM_LEFT; //A collide from left to the B
        if (abs(y + h - b) <= r && x <= a && a <= x + w)
            return CircleRectangleCollideDirection.FROM_BOTTOM; //A collide from bottom to the B
        if (abs(x + w - a) <= r && y <= b && b <= y + h)
            return CircleRectangleCollideDirection.FROM_RIGHT; //A collide from right to the B
        if ((x <= a) && (a <= x + w) && (y <= b) && (b <= y + h))
            return CircleRectangleCollideDirection.FROM_INSIDE; //A is inside of B
        if ((a <= x) && (b <= y) && (r * r >= (a - x) * (a - x) + (b - y) * (b - y))) //A collide from top left
            return CircleRectangleCollideDirection.FROM_TOP_LEFT;
        if ((a <= x) && (b >= y + h) && (r * r >= (a - x) * (a - x) + (b - y - h) * (b - y - h))) //A collide from bottom left
            return CircleRectangleCollideDirection.FROM_BOTTOM_LEFT;
        if ((a >= x + w) && (b <= y) && (r * r >= (a - x - w) * (a - x - w) + (b - y) * (b - y))) //A collide from top right
            return CircleRectangleCollideDirection.FROM_TOP_RIGHT;
        if ((a >= x + w) && (b >= y + h) && (r * r >= (a - x - w) * (a - x - w) + (b - y - h) * (b - y - h))) //A collide from bottom right
            return CircleRectangleCollideDirection.FROM_BOTTOM_RIGHT;
        return CircleRectangleCollideDirection.NO;
    }

    public static boolean RectangleCollideWithCircle(RectangleShape A, CircleShape B)
    {
        return CircleCollideWithRectangle(B, A);
    }

    public static boolean RectangleCollideWithRectangle(RectangleShape A, RectangleShape B)
    {
        return A.left < B.left + B.width && A.left + A.width > B.left && A.top < B.top + B.height && A.top + A.height > B.top;
    }

    public void setTheNode(Node theNode)
    {
        this.theNode = theNode;
    }
    
    public static void printCode(CircleRectangleCollideDirection theDirection)
    {
        if (theDirection == CircleRectangleCollideDirection.FROM_TOP)
            System.out.println("FROM_TOP");
        else if (theDirection == CircleRectangleCollideDirection.FROM_LEFT)
            System.out.println("FROM_LEFT");
        else if (theDirection == CircleRectangleCollideDirection.FROM_BOTTOM)
            System.out.println("FROM_BOTTOM");
        else if (theDirection == CircleRectangleCollideDirection.FROM_RIGHT)
            System.out.println("FROM_RIGHT");
        else if (theDirection == CircleRectangleCollideDirection.FROM_TOP_LEFT)
            System.out.println("FROM_TOP_LEFT");
        else if (theDirection == CircleRectangleCollideDirection.FROM_TOP_RIGHT)
            System.out.println("FROM_TOP_RIGHT");
        else if (theDirection == CircleRectangleCollideDirection.FROM_BOTTOM_LEFT)
            System.out.println("FROM_BOTTOM_LEFT");
        else if (theDirection == CircleRectangleCollideDirection.FROM_BOTTOM_RIGHT)
            System.out.println("FROM_BOTTOM_RIGHT");
        else if (theDirection == CircleRectangleCollideDirection.FROM_INSIDE)
            System.out.println("FROM_INSIDE");
        else 
            System.out.println("NO");
        //FROM_TOP, FROM_LEFT, FROM_BOTTOM, FROM_RIGHT, FROM_INSIDE, FROM_OUTSIDE, FROM_TOP_LEFT, FROM_BOTTOM_LEFT, FROM_BOTTOM_RIGHT, FROM_TOP_RIGHT, NO
    }
}
