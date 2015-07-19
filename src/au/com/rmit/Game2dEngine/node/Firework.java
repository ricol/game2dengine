/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public abstract class Firework extends MovingSprite
{

    public boolean bShouldBlast = false;
    public boolean bDidBlast = false;
    public double blastTime = abs(theRandom.nextInt()) % 10;

    public Firework(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
    }

    @Override
    public void updateState(double currentTime)
    {
        super.updateState(currentTime); //To change body of generated methods, choose Tools | Templates.

        if (this.isAlive() && this.bShouldBlast && !this.bDidBlast)
        {
            if (currentTime - this.starttime >= blastTime * 1000)
            {
                this.bDidBlast = true;
                this.setDead();
            }
        }
    }

}
