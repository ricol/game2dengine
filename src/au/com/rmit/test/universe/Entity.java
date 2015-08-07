/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.universe;

import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.math.Vector;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ricolwang
 */
public class Entity extends Sprite
{

    Set<Entity> otherObjects = new HashSet<>();

    public Entity()
    {
        super(0, 0, 0, 0, 0, 0, 0);

        this.bCustomDrawing = true;
    }

    @Override
    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        super.onCustomDraw(theGraphics2D); //To change body of generated methods, choose Tools | Templates.

        theGraphics2D.setColor(this.getColor());
        theGraphics2D.fillArc(0, 0, (int) this.getWidth() - 1, (int) this.getHeight() - 1, 0, 360);
    }

    @Override
    public void updateState(double currentTime)
    {
        super.updateState(currentTime); //To change body of generated methods, choose Tools | Templates.

        if (this.gravityEnabled())
        {
            this.adjustGravity();
        }
    }

    synchronized void adjustGravity()
    {
        Vector GRAVITY_TOTAL = new Vector(0, 0);

        for (Entity aObject : otherObjects)
        {
            if (aObject.equals(this))
            {
                continue;
            }

            double delX = aObject.getCentreX() - this.getCentreX();
            double delY = aObject.getCentreY() - this.getCentreY();
            double G = 6.67384 * Math.pow(10, -11);
            double M = aObject.getMass();
            Vector DISTANCE = new Vector(delX, delY);
            double distanceAbsolute = DISTANCE.getMagnitude();
            double absoluteGravity = (G * M) / (distanceAbsolute * distanceAbsolute);
            Vector GRAVITY = DISTANCE.getTheUnitVector().multiplyNumber(absoluteGravity);
            GRAVITY_TOTAL = GRAVITY.addVector(GRAVITY_TOTAL);
        }

        Gravity g = new Gravity(GRAVITY_TOTAL.x, GRAVITY_TOTAL.y);
        this.applyGravity(g);
    }

    synchronized public void addEntity(Entity aEntity)
    {
        this.otherObjects.add(aEntity);
    }

    synchronized public void removeEntity(Entity aEntity)
    {
        this.otherObjects.remove(aEntity);
    }

    synchronized public void addEntities(Set<Entity> all)
    {
        this.otherObjects.addAll(all);
    }

    synchronized public void removeEntities(Set<Entity> all)
    {
        this.otherObjects.removeAll(all);
    }

}
