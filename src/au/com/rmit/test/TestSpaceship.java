/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test;

import au.com.rmit.Game2dEngine.action.Action;

/**
 *
 * @author ricolwang
 */
public class TestSpaceship extends Spaceship
{

    public TestSpaceship()
    {
        super("my-spaceship.png");
    }

    @Override
    public void onActionComplete(Action aAction)
    {
        System.out.println("Action complete: " + aAction);
    }
}
