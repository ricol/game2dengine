/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.awt.Color;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class BigFirework extends Firework
{

    public int subFireworks = abs(theRandom.nextInt()) % 50;

    public BigFirework(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);

        this.bShouldBlast = true;
        this.blastTime = abs(theRandom.nextInt()) % 3 + 1;
    }

    @Override
    public void updateState(double currentTime)
    {
        super.updateState(currentTime); //To change body of generated methods, choose Tools | Templates.

        if (this.bDidBlast)
        {
            for (int i = 0; i < this.subFireworks; i++)
            {
                Firework aObject;

                double tmpMass = theRandom.nextFloat() / 3.0f;
                double tmpVelocityX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * 500.0f + this.velocityX;
                double tmpVelocityY = -1 * theRandom.nextFloat() * 500.0f + this.velocityY;

                if (abs(theRandom.nextInt() % 10) > 8)
                {
                    aObject = new BigFirework(this.x, this.y, 15, 15, tmpMass, tmpVelocityX, tmpVelocityY);
                    aObject.lifetime = 1;
                    aObject.blastTime = (abs(theRandom.nextInt()) % 100) / 100.0 + 0.5;
                    ((BigFirework) aObject).subFireworks = 10;
                    aObject.setImage("starBig.png");
                } else
                {
                    aObject = new SmallFirework(this.x, this.y, 5, 5, tmpMass, tmpVelocityX, tmpVelocityY);
                    aObject.lifetime = abs(theRandom.nextInt()) % 5 + 1;
                    aObject.setImage("starSmall.png");
                }

                aObject.applyGravity(this.g);

                int redValue = abs(theRandom.nextInt()) % 255;
                int greenValue = abs(theRandom.nextInt()) % 255;
                int blueValue = abs(theRandom.nextInt()) % 255;
                aObject.setRed(redValue);
                aObject.setGreen(greenValue);
                aObject.setBlue(blueValue);

                this.theScene.addSprite(aObject);
            }
        }
    }
}
