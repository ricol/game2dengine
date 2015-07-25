/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.scene.Layer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class TestSpaceship extends Spaceship implements ActionListener
{

    Timer theTimerForEngine = new Timer(10, this);
    PropelEngine theEngine = new PropelEngine();

    public TestSpaceship()
    {
        super("my-spaceship.png");
        this.addAChild(theEngine);
        
        this.bDrawFrame = true;
        theEngine.bDrawFrame = true;
        theEngine.setRed(255);
        theEngine.setWidth(20);
        theEngine.setHeight(20);
    }

    @Override
    public void onDead()
    {
        super.onDead(); //To change body of generated methods, choose Tools | Templates.
        theTimerForEngine.stop();
    }

    @Override
    public void onAddToLayer(Layer theLayer)
    {
        super.onAddToLayer(theLayer); //To change body of generated methods, choose Tools | Templates.

        theEngine.setCentreX(this.getWidth() / 2);
        theEngine.setCentreY(this.getHeight());

        theTimerForEngine.start();
    }

    @Override
    public void onActionComplete(Action aAction)
    {
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(this.theTimerForEngine))
        {
            theEngine.propel();
        }
    }

    @Override
    public void updateState(double currentTime)
    {
        super.updateState(currentTime); //To change body of generated methods, choose Tools | Templates.

        System.out.println("Parent - CentreX: " + this.getCentreX() + "; CentreY: " + this.getCentreY() + "; Angel: " + this.getAngle());
    }
}
