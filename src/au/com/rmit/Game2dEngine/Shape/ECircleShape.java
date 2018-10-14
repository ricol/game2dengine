/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.Shape;

import au.com.rmit.Game2dEngine.painter.interfaces.IEngineGraphics;
import au.com.rmit.Game2dEngine.sprite.Node;
import au.com.rmit.math.geometry.CircleShape;
import java.awt.Color;

/**
 *
 * @author ricolwang
 */
public class ECircleShape extends CircleShape implements EIShape
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
    public EIShape getShape()
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
