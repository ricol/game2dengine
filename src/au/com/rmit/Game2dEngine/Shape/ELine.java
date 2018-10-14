/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.Shape;

import au.com.rmit.Game2dEngine.painter.interfaces.IEngineGraphics;
import au.com.rmit.Game2dEngine.sprite.Node;
import au.com.rmit.math.geometry.Line;
import au.com.rmit.math.geometry.Point;
import java.awt.Color;

/**
 *
 * @author ricolwang
 */
public class ELine extends Line implements EIShape
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
    public EIShape getShape()
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
