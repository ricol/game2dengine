/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.spaceship;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.scene.Layer;

/**
 *
 * @author ricolwang
 */
public class TestSpaceship extends Spaceship
{

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

    }

    @Override
    public void onAddToLayer(Layer theLayer)
    {
        super.onAddToLayer(theLayer); //To change body of generated methods, choose Tools | Templates.

        theEngine.setCentreX(this.getWidth() / 2);
        theEngine.setCentreY(this.getHeight());

    }

    @Override
    public void onActionComplete(Action aAction)
    {
    }

}
