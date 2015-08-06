/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.universe;

import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.scene.Scene;
import au.com.rmit.test.physicengine.WallSprite;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 *
 * @author ricolwang
 */
public class GravityDemoScene extends Scene
{

    Sun theSun;
    boolean bFlag;
    Gravity g = new Gravity(0, 0);
    float delta = 0;
    float GRAVITY_VALUE = 500;
    boolean bAlreadyRun;

    WallSprite theWall = new WallSprite(0, 0, 100, 100, 0, 0, 0);

    public GravityDemoScene()
    {
        this.addMouseMotionListener(new MouseMotionListener()
        {

            @Override
            public void mouseDragged(MouseEvent e)
            {
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                theWall.setCentreX(e.getX());
                theWall.setCentreY(e.getY());
            }
        });

        this.addMouseListener(new MouseListener()
        {
            double MaxMass = 10.0f * pow(10, 16);
            double MaxWidth = 150;

            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON3)
                {
                    if (theSun == null)
                    {
                        theSun = new Sun();
                        double mass = MaxMass;
                        theSun.setMass(mass);
                        theSun.setWidth((mass / (MaxMass * 1.0f) * MaxWidth));
                        theSun.setHeight(theSun.getWidth());
                        theSun.setCentreX(e.getX());
                        theSun.setCentreY(e.getY());
                        addSprite(theSun);
                    }
                } else if (e.getButton() == MouseEvent.BUTTON1)
                {
                    if (theSun == null) return;
                    
                    double MaxMassPlanet = MaxMass / 100.0f;
                    double MaxWidthPlanet = MaxWidth / 4.0f;
                    Planet aPlanet = new Planet(theSun);
                    double mass = ((abs(theRandom.nextInt()) * 1.0f) / Integer.MAX_VALUE) * MaxMassPlanet;
                    aPlanet.setMass(mass);
                    aPlanet.setWidth((mass / (MaxMassPlanet * 1.0f)) * MaxWidthPlanet);
                    aPlanet.setHeight(aPlanet.getWidth());
                    aPlanet.setCentreX(e.getX());
                    aPlanet.setCentreY(e.getY());
                    aPlanet.setVelocityY(100);
                    addSprite(aPlanet);
                }
            }

            @Override

            public void mouseReleased(MouseEvent e)
            {
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
            }

        }
        );
    }
}
