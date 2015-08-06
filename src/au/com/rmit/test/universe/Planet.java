/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.universe;

import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.math.Vector;

/**
 *
 * @author ricolwang
 */
public class Planet extends Entity
{

    Sun theSun;

    public Planet(Sun theSun)
    {
        super();

        this.setGreen(255);
        this.theSun = theSun;
        this.enableGravity();
    }

    @Override
    public void updateState(double currentTime)
    {
        super.updateState(currentTime); //To change body of generated methods, choose Tools | Templates.

        this.adjustGravity();
    }

    void adjustGravity()
    {
        double delX = theSun.getCentreX() - this.getCentreX();
        double delY = theSun.getCentreY() - this.getCentreY();
        double G = 6.67384 * Math.pow(10, -11);
        double M = theSun.getMass();
        Vector DISTANCE = new Vector(delX, delY);
        double distanceAbsolute = DISTANCE.getMagnitude();
        double absoluteGravity = (G * M) / (distanceAbsolute * distanceAbsolute);
        Vector GRAVITY = DISTANCE.getTheUnitVector().multiplyNumber(absoluteGravity);

        Gravity g = new Gravity(GRAVITY.x, GRAVITY.y);
        System.out.println("G: " + g.GX + " - " + g.GY);
        this.applyGravity(g);
    }
}
