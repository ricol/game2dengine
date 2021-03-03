/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape;

import com.wang.Game2dEngine.Shape.Interface.IEShape;
import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import com.wang.Game2dEngine.sprite.Node;
import com.wang.math.geometry.Line;
import com.wang.math.geometry.PolygonShape;

import java.awt.*;

/**
 * @author ricolwang
 */
public class EPolygonShape extends PolygonShape implements IEShape
{

    Node theNode;

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

    @Override
    public void draw(IEngineGraphics theGraphicsInTheScene, Color theColor)
    {
        for (Line aLine : this.sides)
        {
            if (aLine instanceof ELine)
            {
                ((ELine) aLine).draw(theGraphicsInTheScene, theColor);
            }
        }
    }

}
