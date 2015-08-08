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
        double cx = abs(A.centreX - B.left);
        double cy = abs(A.centreY - B.top);

        if (cx > (B.width / 2 + A.radius))
            return false;
        if (cy > (B.height / 2 + A.radius))
            return false;

        if (cx <= (B.width / 2))
            return true;
        if (cy <= (B.height / 2))
            return true;

        double ccx = (cx - B.width / 2);
        double ccy = (cy - B.height / 2);
        double cc = ccx * ccx + ccy * ccy;
        return cc <= A.radius * A.radius;
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
}
