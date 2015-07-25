/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.action.RotateByAction;
import au.com.rmit.Game2dEngine.node.Sprite;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class PropelEngine extends Sprite
{
    public PropelEngine()
    {
        RotateByAction aAction = new RotateByAction();
        aAction.rotateBy(-Math.PI * 20, 20);
        this.addAction(aAction);
    }
    
    public void propel()
    {
        int number = abs(theRandom.nextInt()) % 5 + 10;

        for (int i = 0; i < number; i++)
        {
            double tmpVelocityX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * 20 * 4;
            double tmpVelocityY = theRandom.nextFloat() * 100 * 10;

            int size = 6;

            Sprite aFire = new Sprite(0, 0, size, size, 0, 0, 0);

            aFire.setCentreX(this.getCentreX() + this.parent.getX());
            aFire.setCentreY(this.getCentreY() + this.parent.getY());
            
            aFire.setVelocityX(tmpVelocityX);
            aFire.setVelocityY(tmpVelocityY);
            aFire.setRed(255);
            aFire.setGreen(255);
            aFire.setBlue(255);
            aFire.setWidth(size);
            aFire.setHeight(size);
            aFire.bDeadIfNoActions = true;

            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 0.1f);
            aFire.addAction(aAction);
            
            this.parent.theScene.addSprite(aFire);
        }
    }

    @Override
    public void updateState(double currentTime)
    {
        super.updateState(currentTime); //To change body of generated methods, choose Tools | Templates.
        
        System.out.println("Child - CentreX: " + this.getCentreX() + "; CentreY: " + this.getCentreY() + "; Angel: " + this.getAngle());
    }
}
