/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

/**
 *
 * @author Philology
 */
public class MovingSprite extends Sprite
{

    protected double velocityX;
    protected double velocityY;

    public MovingSprite(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass);

        this.velocityX = velocityX;
        this.velocityY = velocityY;

//        System.out.println("MovingSprite.Create: " + "x: " + x + "; y: " + y + "; width: " + width + "; height: " + height
//        + "; mass: " + mass + "; velocityX: " + velocityX + "; velocityY: " + velocityY);
    }

    @Override
    public void updateState(double currentTime)
    {
        super.updateState(currentTime); //To change body of generated methods, choose Tools | Templates.

        if (this.isAlive)
        {
            //how much time passed since last update
            double t = (currentTime - this.lastUpdateTime) / 1000.0f; //in seconds

            //update state
            if (this.g != null)
            {
                velocityX += this.g.GX * t;
                velocityY += this.g.GY * t;
            }

            double IncX = velocityX * t;
            double IncY = velocityY * t;
//        System.out.println("IncX: " + IncX + "; IncY: " + IncY);
            x += IncX;
            y += IncY;

            this.lastUpdateTime = currentTime;
        }
    }

}
