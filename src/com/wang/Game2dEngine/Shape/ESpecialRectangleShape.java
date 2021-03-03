/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape;

import com.wang.Game2dEngine.Shape.Interface.IEShape;
import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import com.wang.Game2dEngine.sprite.Node;
import com.wang.math.geometry.SpecialRectangleShape;

import java.awt.*;

/**
 * @author ricolwang
 */
public class ESpecialRectangleShape extends SpecialRectangleShape implements IEShape
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
    public IEShape getShape()
    {
        return this;
    }

    @Override
    public void draw(IEngineGraphics theGraphicsInTheScene, Color theColor)
    {
        theGraphicsInTheScene.setColor(theColor);
        theGraphicsInTheScene.drawRect((int) this.left, (int) this.top, (int) width - 1, (int) height - 1);
    }

}
