/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape;

import com.wang.Game2dEngine.Shape.Interface.IEShape;
import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import com.wang.Game2dEngine.sprite.Node;
import com.wang.math.geometry.CircledShape;

import java.awt.*;

/**
 * @author ricolwang
 */
public class ECircleShape extends CircledShape implements IEShape
{

    Node theNode;

    public ECircleShape(double centreX, double centreY, double radius)
    {
        super(centreX, centreY, radius);
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
    public void refresh(double changeX, double changeY, double changeWidth, double changeHeight)
    {
        super.refresh(changeX, changeY, changeWidth, changeHeight); //To change body of generated methods, choose Tools | Templates.

        this.centre.x = theNode.getCentreX();
        this.centre.y = theNode.getCentreY();
        this.radius = theNode.getWidth() > theNode.getHeight() ? (theNode.getWidth() / 2.0f) : (theNode.getHeight() / 2.0f);
    }

    @Override
    public IEShape getShape()
    {
        return this;
    }

    @Override
    public void draw(IEngineGraphics theGraphicsInTheScene, Color theColor)
    {
        int tmpRadius = (int) radius;
        int tmpX = (int) (centre.x - tmpRadius);
        int tmpY = (int) (centre.y - tmpRadius);
        theGraphicsInTheScene.setColor(theColor);
        theGraphicsInTheScene.drawArc(tmpX, tmpY, 2 * tmpRadius, 2 * tmpRadius, 0, 360);
    }

}
