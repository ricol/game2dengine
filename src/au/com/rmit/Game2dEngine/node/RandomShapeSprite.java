/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import java.awt.Color;
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
            Color theColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha);
            g.setColor(theColor);

//            g.fillArc((int) x, (int) y, (int) width, (int) height, 0, 360);
            g.fill3DRect((int) this.x, (int) this.y, (int) this.width, (int) this.height, true);
//            g.draw3DRect((int)this.x, (int)this.y, (int)this.width, (int)this.height, true);
//            g.fillOval((int)this.x, (int)this.y, (int)this.width, (int)this.height);
        }
    }

}
