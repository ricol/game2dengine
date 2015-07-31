/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.sprites;

import au.com.rmit.Game2dEngine.sprite.Sprite;
import java.awt.Graphics2D;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class RandomShapeSprite extends Sprite
{

    int angleStart = abs(theRandom.nextInt()) % 360;
    int angleEnd = angleStart + abs(theRandom.nextInt()) % 360;

    public RandomShapeSprite(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
    }

    @Override
    public void updateGUI(Graphics2D g)
    {
        if (this.isAlive())
        {
            g.setColor(this.getColor());

            g.fill3DRect((int) this.x, (int) this.y, (int) this.getWidth(), (int) this.getHeight(), true);
        }
    }

}
