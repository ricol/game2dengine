/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test;

import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.scene.Scene;
import au.com.rmit.test.WallSprite.WALLTYPE;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class TestScene extends Scene
{

    Gravity g = new Gravity(0, 0);
    float delta = 0;
    float GRAVITY_VALUE = 500;
    boolean bAlreadyRun;
    
    WallSprite theWall = new WallSprite(0, 0, 100, 100, 0, 0, 0);

    public TestScene()
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
                    aSprite.wallType = WALLTYPE.TOP;
                    addSprite(aSprite);

                    aSprite = new WallSprite(0, getHeight() - 5, getWidth(), 5, 0, 0, 0);
                    aSprite.setBlue(255);
                    aSprite.wallType = WALLTYPE.BOTTOM;
                    addSprite(aSprite);

                    aSprite = new WallSprite(0, 0, 5, getHeight(), 0, 0, 0);
                    aSprite.setBlue(255);
                    aSprite.wallType = WALLTYPE.LEFT;
                    addSprite(aSprite);

                    aSprite = new WallSprite(getWidth() - 5, 0, 5, getHeight(), 0, 0, 0);
                    aSprite.setBlue(255);
                    aSprite.wallType = WALLTYPE.RIGHT;
                    addSprite(aSprite);
                    
                    theWall.setRed(255);
                    theWall.setLayer(1);
                    addSprite(theWall);
                }

                CircleSprite aSprite = new CircleSprite();
                aSprite.setCentreX(e.getX());
                aSprite.setCentreY(e.getY());
                aSprite.setVelocityX(abs(theRandom.nextInt()) % 500);
                aSprite.setVelocityY(abs(theRandom.nextInt()) % 500);
                addSprite(aSprite);
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
