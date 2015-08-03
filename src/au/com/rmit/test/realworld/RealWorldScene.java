/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.realworld;

import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.scene.Scene;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.rmit.test.physicengine.WallSprite;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author ricolwang
 */
public class RealWorldScene extends Scene
{

    boolean bFlag;
    Gravity g = new Gravity(0, 1000);
    float delta = 0;
    float GRAVITY_VALUE = 500;
    boolean bAlreadyRun;

    WallSprite theWall = new WallSprite(0, 0, 100, 100, 0, 0, 0);

    public RealWorldScene()
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

            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (!bAlreadyRun)
                {
                    bAlreadyRun = true;

                    WallSprite aSprite = new WallSprite(0, 0, getWidth(), 5, 0, 0, 0);
                    aSprite.setBlue(255);
                    aSprite.wallType = WallSprite.WALLTYPE.TOP;
                    addSprite(aSprite);

                    aSprite = new WallSprite(0, getHeight() - 5, getWidth(), 5, 0, 0, 0);
                    aSprite.setBlue(255);
                    aSprite.wallType = WallSprite.WALLTYPE.BOTTOM;
                    addSprite(aSprite);

                    aSprite = new WallSprite(0, 0, 5, getHeight(), 0, 0, 0);
                    aSprite.setBlue(255);
                    aSprite.wallType = WallSprite.WALLTYPE.LEFT;
                    addSprite(aSprite);

                    aSprite = new WallSprite(getWidth() - 5, 0, 5, getHeight(), 0, 0, 0);
                    aSprite.setBlue(255);
                    aSprite.wallType = WallSprite.WALLTYPE.RIGHT;
                    addSprite(aSprite);
                }

                float maxmass = 1000;
                float size = 100;

                if (e.getButton() == MouseEvent.BUTTON3)
                {
                    Sprite aCircle = new Circle();

                    aCircle.setBlue(255);

                    aCircle.setCentreX(e.getX());
                    aCircle.setCentreY(e.getY());

//                    aCircle.setMass(abs(theRandom.nextInt()) % 900 + 100);
                    aCircle.setMass(500);
                    aCircle.setWidth((aCircle.getMass() / maxmass) * size);
                    aCircle.setHeight(aCircle.getWidth());

                    addSprite(aCircle);
                    aCircle.applyGravity(g);

                } else if (e.getButton() == MouseEvent.BUTTON1)
                {
                    Sprite aCircle = new Circle();

                    aCircle.setRed(255);

                    aCircle.setCentreX(e.getX());
                    aCircle.setCentreY(e.getY());

//                    aCircle.setMass(abs(theRandom.nextInt()) % 900 + 100);
                    aCircle.setMass(1000);
                    aCircle.setWidth((aCircle.getMass() / maxmass) * size);
                    aCircle.setHeight(aCircle.getWidth());

                    addSprite(aCircle);
                    aCircle.applyGravity(g);
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
