/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.Shape;

import au.com.rmit.math.geometry.Shape;
import au.com.rmit.math.geometry.SpecialRectangleShape;
import au.com.rmit.Game2dEngine.sprite.Node;

/**
 *
 * @author ricolwang
 */
public class ESpecialRectangleShape extends SpecialRectangleShape implements EIShape
{

    Node theNode;

    public ESpecialRectangleShape(double left, double top, double width, double height)
    {
        super(left, top, width, height);
    }

    @Override
    public void refresh(double changeX, double changeY, double changeWidth, double changeHeight)
    {
        super.refresh(changeX, changeY, changeWidth, changeHeight);
        this.left = theNode.getX();
        this.top = theNode.getY();
        this.width = theNode.getWidth();
        this.height = theNode.getHeight();
    }

    @Override
    public Node getNode()
    {
        return theNode;
    }

    @Override
    public void setTheNode(Node theNode)
    {
        this.theNode = theNode;
    }

    @Override
    public Shape getShape()
    {
        return this;
    }

}
