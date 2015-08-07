/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.universe;

import au.com.rmit.Game2dEngine.scene.Scene;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ricolwang
 */
public class GravityDemoScene extends Scene
{
    public GravityDemoScene()
    {
        this.addMouseListener(new MouseListener()
        {
            double MassOfSun = 10.0f * pow(10, 17);
            
            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON3)
                {
                    Sun aSun = new Sun();
                    aSun.setMass(MassOfSun);
                    aSun.setWidth(50);
                    aSun.setHeight(aSun.getWidth());
                    aSun.setCentreX(e.getX());
                    aSun.setCentreY(e.getY());
                    aSun.setLayer(1);
                    addSprite(aSun);
                    
                    Set<Entity> all = getAllEntities();
                    
                    for (Entity aEntity : all)
                    {
                        if (aEntity instanceof Planet)
                        {
                            Planet aPlanet = (Planet)aEntity;
                            aPlanet.addEntity(aSun);
                        }
                    }
                    
                } else if (e.getButton() == MouseEvent.BUTTON1)
                {
                    double MaxMassOfPlanet = MassOfSun / 10.0f;
                    double MaxWidthPlanet = 50;

                    Planet aPlanet = new Planet();
                    double mass = ((abs(theRandom.nextInt()) * 1.0f) / Integer.MAX_VALUE) * MaxMassOfPlanet;
                    aPlanet.setMass(mass);
                    aPlanet.setWidth((mass / (MaxMassOfPlanet * 1.0f)) * MaxWidthPlanet);
                    aPlanet.setHeight(aPlanet.getWidth());
                    aPlanet.setCentreX(e.getX());
                    aPlanet.setCentreY(e.getY());
                    aPlanet.setVelocityY(500);
                    aPlanet.setLayer(2);
                    
                    aPlanet.addEntities(getAllEntities());
                    
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
    
    public Set<Entity> getAllEntities()
    {
        Set<Entity> allEntities = new HashSet<>();
        ArrayList<Sprite> all = this.getAllSprites();
        for (Sprite aSprite : all)
        {
            if (aSprite instanceof Entity)
            {
                allEntities.add((Entity)aSprite);
            }
        }
        
        return allEntities;
    }

}
