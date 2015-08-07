/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.fireworks;

import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.scene.Scene;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class FireworksScene extends Scene
{

    Gravity g = new Gravity(0, 500);

    Timer theTimer = new Timer(200, new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            createParticles(1, (int) (size().width * (2.0 / 4.0)), (int) (size().height * (3.5 / 4.0)), g);
        }
    });

    public FireworksScene()
    {
        super();
        this.setRed(0);
        this.setGreen(0);
        this.setBlue(0);
    }

    @Override
    public void start()
    {
        super.start();

        this.theTimer.start();
    }

    @Override
    public void pause()
    {
        super.pause();

        if (bPaused)
        {
            this.theTimer.stop();
        } else
        {
            this.theTimer.start();
        }
    }

    @Override
    public void stop()
    {
        this.theTimer.stop();

        super.stop();
    }

    public void createParticles(int number, int x, int y, Gravity g)
    {
        for (int i = 0; i < number; i++)
        {
            Firework aObject;

            double velocityX = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * 50.0f;
            double velocityY = -1 * theRandom.nextFloat() * 50.0f - 600.0f;

            if (abs(theRandom.nextInt() % 10) > 5)
            {
                aObject = new BigFirework();
                aObject.setX(x);
                aObject.setY(y);
                aObject.setVelocityX(velocityX);
                aObject.setVelocityY(velocityY);

                aObject.setLifeTime(100);
                aObject.blastTime = (abs(theRandom.nextInt()) % 100) / 80.0 + 0.5;
                ((BigFirework) aObject).subFireworks = 20;

            } else
            {
                aObject = new SmallFirework();
                aObject.setX(x);
                aObject.setY(y);
                aObject.setVelocityX(velocityX);
                aObject.setVelocityY(velocityY);

                aObject.setLifeTime(abs(theRandom.nextInt()) % 5 + 1);
                aObject.setImage("starSmall.png");
            }

            int redValue = abs(theRandom.nextInt()) % 255;
            int greenValue = abs(theRandom.nextInt()) % 255;
            int blueValue = abs(theRandom.nextInt()) % 255;
            aObject.setRed(redValue);
            aObject.setGreen(greenValue);
            aObject.setBlue(blueValue);
            aObject.applyGravity(g);

            this.addSprite(aObject);
        }
    }
}
