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
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class FountainScene extends Scene
{

    Gravity g = new Gravity(0, 500);

    Timer theTimer = new Timer(10, new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            createParticles(10, (int) (size().width * (2.0 / 4.0)), (int) (size().height * (3.5 / 4.0)), g);
        }
    });

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

    public synchronized void createParticles(int number, int x, int y, Gravity g)
    {
        for (int i = 0; i < number; i++)
        {
            Firework aObject;

            double mass = theRandom.nextFloat() / 3.0f;
            double velocityX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * 200.0f;
            double velocityY = -1 * theRandom.nextFloat() * 50.0f - 500.0f;

            float size = abs(theRandom.nextInt()) % 7 + 3;
            aObject = new SmallFirework(x, y, size, size, mass, velocityX, velocityY);
            aObject.lifetime = (abs(theRandom.nextInt()) % 100) / 50.0;

            int redValue = abs(theRandom.nextInt()) % 255;
            int greenValue = abs(theRandom.nextInt()) % 255;
            int blueValue = abs(theRandom.nextInt()) % 255;
            aObject.color = new Color(redValue, greenValue, blueValue);

            aObject.applyGravity(g);

            this.addSprite(aObject);
        }
    }
}
