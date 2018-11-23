/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape;

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import com.wang.Game2dEngine.sprite.Node;
import com.wang.math.geometry.Line;
import com.wang.math.geometry.Point;
import java.awt.Color;
import com.wang.Game2dEngine.Shape.Interface.IEShape;

/**
 *
 * @author ricolwang
 */
public class ELine extends Line implements IEShape
{

    Node theNode;

    public ELine(Point start, Point end)
    {
        super(start, end);
    }

    @Override
    public void draw(IEngineGraphics theGraphicsInTheScene, Color theColor)
    {
        theGraphicsInTheScene.setColor(theColor);
        theGraphicsInTheScene.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);
    }

    @Override
    public IEShape getShape()
    {
        return null;
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
