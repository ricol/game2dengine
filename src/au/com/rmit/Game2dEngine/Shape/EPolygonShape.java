/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.Shape;

import au.com.rmit.Game2dEngine.painter.interfaces.IEngineGraphics;
import au.com.rmit.Game2dEngine.sprite.Node;
import au.com.rmit.math.geometry.Line;
import au.com.rmit.math.geometry.PolygonShape;
import java.awt.Color;

/**
 *
 * @author ricolwang
 */
public class EPolygonShape extends PolygonShape implements EIShape
{

    Node theNode;

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
