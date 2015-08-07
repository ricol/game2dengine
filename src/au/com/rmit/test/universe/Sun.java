/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.universe;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.scene.Layer;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class Sun extends Star implements ActionListener
{

    Timer theTimer = new Timer(100, this);

    @Override
    public void onAddToLayer(Layer theLayer)
    {
        super.onAddToLayer(theLayer); //To change body of generated methods, choose Tools | Templates.

        theTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int number = abs(theRandom.nextInt()) % 20 + 20;
        double v = 15;

        for (int i = 0; i < number; i++)
        {
            double tmpVelocityX = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * v * sin(i);
            double tmpVelocityY = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * v * cos(i);

            int size = 5;

            Sprite aFire = new Flame();

            aFire.setWidth(size);
            aFire.setHeight(size);
            aFire.setCentreX(this.getCentreX());
            aFire.setCentreY(this.getCentreY());

            aFire.setVelocityX(tmpVelocityX);
            aFire.setVelocityY(tmpVelocityY);
            aFire.setRed(255);
            aFire.setBlue(255);
            aFire.setGreen(255);

            aFire.bDeadIfNoActions = true;
            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 4f);
            aFire.addAction(aAction);

            if (this.theScene != null)
            {
                this.theScene.addSprite(aFire);
            }
        }
    }
}
