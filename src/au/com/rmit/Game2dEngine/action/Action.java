/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

import au.com.rmit.Game2dEngine.node.MovingSprite;
import java.util.Random;

/**
 *
 * @author ricolwang
 */
public abstract class Action
{

    public String identifer = this.getClass().getName();
    protected boolean bImmediately;

    public static final double MINIMUM = 0.001;
    public static final double EQUAL_STANDARD = 0.000000001;
    public static final double MINIMUM_DURATION = 0.001;

    public Random theRandom = new Random();
    protected MovingSprite theSprite = null;
    public boolean bComplete = false;

    //runningTime in milliseconds
    public void perform(double runningTime)
    {
        bComplete = theRandom.nextBoolean();
    }

    public void setSprite(MovingSprite theSprite)
    {
        this.theSprite = theSprite;
    }

    public void clearSprite()
    {
        this.theSprite = null;
    }

    @Override
    public String toString()
    {
        return "Action: " + this.getClass().getName() + " - id: " + identifer;
    }
}
