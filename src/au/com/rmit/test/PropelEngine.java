/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.node.Sprite;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class PropelEngine extends Sprite implements ActionListener
{

    Timer theTimerForEngine = new Timer(10, this);

    public PropelEngine()
    {
//        RotateByAction aAction = new RotateByAction();
//        aAction.rotateBy(-Math.PI * 20, 20);
//        this.addAction(aAction);

        theTimerForEngine.start();
    }

    @Override
    public void onDead()
    {
        super.onDead(); //To change body of generated methods, choose Tools | Templates.
        theTimerForEngine.stop();
        this.parent = null;
        this.theScene = null;
    }

    public void propel()
    {
        int number = abs(theRandom.nextInt()) % 5 + 10;

        for (int i = 0; i < number; i++)
        {
            double tmpVelocityX = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * 20 * 4;
            double tmpVelocityY = theRandom.nextFloat() * 100 * 10;

            int size = 6;

            Sprite aFire = new FireSprite(0, 0, size, size, 0, 0, 0);

            aFire.setCentreX(this.getCentreX());
            aFire.setCentreY(this.getCentreY());

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

            if (this.parent == null)
            {
                break;
            }
            this.parent.addAChild(aFire);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(this.theTimerForEngine))
        {
            propel();
        }
    }
}
