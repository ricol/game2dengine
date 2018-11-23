/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape;

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import com.wang.Game2dEngine.sprite.Node;
import com.wang.math.geometry.Point;
import java.awt.Color;
import com.wang.Game2dEngine.Shape.Interface.IEShape;

/**
 *
 * @author ricolwang
 */
public class EPoint extends Point implements IEShape
{
    Node theNode;
    
    public EPoint(double x, double y)
    {
        super(x, y);
    }

    @Override
    public IEShape getShape()
    {
        return null;
    }

    @Override
    public void draw(IEngineGraphics theGraphicsInTheScene, Color theColor)
    {
        theGraphicsInTheScene.setColor(theColor);
        theGraphicsInTheScene.fillArc((int) x, (int) y, 2, 2, 0, 360);
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
    
}
