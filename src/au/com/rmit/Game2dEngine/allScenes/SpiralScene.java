/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.allScenes;

import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.node.Firework;
import au.com.rmit.Game2dEngine.node.SmallFirework;
import au.com.rmit.Game2dEngine.scene.Scene;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class SpiralScene extends Scene
{

    static float GRAVITY_VALUE = 100;
    Gravity g1 = new Gravity(0, 0);
    float delta1 = 0;

    Gravity g2 = new Gravity(0, 0);
    float delta2 = 0;

    Timer theTimer = new Timer(10, new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            createParticles(1, (int) (size().width * (1.0 / 4.0)), (int) (size().height / 2.25f), g1);
            createParticles(1, (int) (size().width * (3.0 / 4.0)), (int) (size().height / 2.25f), g1);
        }
    });

    Timer theTimerForGravity = new Timer(10, new ActionListener()
    {

        @Override
        public synchronized void actionPerformed(ActionEvent e)
        {
            g1.GX = (float) sin(delta1) * GRAVITY_VALUE;
            g1.GY = (float) cos(delta1) * GRAVITY_VALUE;
            delta1 += 0.1;

            g2.GX = (float) sin(delta2) * GRAVITY_VALUE;
            g2.GY = (float) cos(delta2) * GRAVITY_VALUE;
            delta2 -= 0.1;
        }

    });

    @Override
    public void start()
    {
        super.start();

        this.theTimer.start();
        this.theTimerForGravity.start();
    }

    @Override
    public void pause()
    {
        super.pause();

        if (bPaused)
        {
            this.theTimer.stop();
            this.theTimerForGravity.stop();
        } else
        {
            this.theTimer.start();
            this.theTimerForGravity.start();
        }
    }

    @Override
    public void stop()
    {
        this.theTimer.stop();
        this.theTimerForGravity.stop();

        super.stop();
    }

    public synchronized void createParticles(int number, int x, int y, Gravity g)
    {
        for (int i = 0; i < number; i++)
        {
            Firework aObject;

            aObject = new SmallFirework(x, y, 10, 10, 0, 0, 0);
            aObject.lifetime = 10;
            int redValue = abs(theRandom.nextInt()) % 255;
            int greenValue = abs(theRandom.nextInt()) % 255;
            int blueValue = abs(theRandom.nextInt()) % 255;
            aObject.color = new Color(redValue, greenValue, blueValue);

            aObject.applyGravity(g);

            this.addSprite(aObject);
        }
    }
}
