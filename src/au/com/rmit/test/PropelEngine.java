/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.node.MovingSprite;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class PropelEngine extends MovingSprite
{
    public void propel()
    {
        int number = abs(theRandom.nextInt()) % 5 + 10;

        for (int i = 0; i < number; i++)
        {
            double tmpX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * 20 * 4;
            double tmpY = theRandom.nextFloat() * 100 * 10;

            int size = 6;

            MovingSprite aFire = new MovingSprite(0, 0, size, size, 0, 0, 0);

            aFire.setX(this.getCentreX() - size / 2);
            aFire.setY(this.getCentreY() + this.getHeight() / 2);
            aFire.setVelocityX(tmpX);
            aFire.setVelocityY(tmpY);
            aFire.setRed(255);
            aFire.setGreen(255);
            aFire.setBlue(255);
            aFire.setWidth(size);
            aFire.setHeight(size);
            aFire.bDeadIfNoActions = true;

            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 0.1f);
            aFire.addAction(aAction);

            this.theScene.addSprite(aFire);
        }
    }
}
