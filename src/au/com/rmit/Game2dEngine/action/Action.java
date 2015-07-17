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

    public static final double MINIMUM = 0.001;
    public static final double EQUAL_STANDARD = 0.000000001;

    public Random theRandom = new Random();
    protected MovingSprite theSprite;
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
}
