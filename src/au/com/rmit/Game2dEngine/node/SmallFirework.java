/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

/**
 *
 * @author ricolwang
 */
public class SmallFirework extends Firework
{

    public SmallFirework(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
    }

    public SmallFirework()
    {
        super("starSmall.png");
    }
}
