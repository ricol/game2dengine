/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.allScenes;

import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.node.RandomShapeSprite;
import au.com.rmit.Game2dEngine.node.Sprite;
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
public class RandomShapeScene extends Scene
{

    static float GRAVITY_VALUE = 100;
    Gravity g = new Gravity(0, 0);
    float delta = 0;

    Timer theTimer = new Timer(50, new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            createParticles(1, (int) (size().width * (1.0 / 2.0)), (int) (size().height / 2.25f), g);
        }
    });

    Timer theTimerForGravity = new Timer(10, new ActionListener()
    {

        @Override
        public synchronized void actionPerformed(ActionEvent e)
        {
            g.GX = (float) sin(delta) * GRAVITY_VALUE;
            g.GY = (float) cos(delta) * GRAVITY_VALUE;
            delta += 0.1;
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

    public void createParticles(int number, int x, int y, Gravity g)
    {
        for (int i = 0; i < number; i++)
        {
            Sprite aObject;

            aObject = new RandomShapeSprite(x, y, 50, 50, 0, 0, 0);
            aObject.lifetime = 10;
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
