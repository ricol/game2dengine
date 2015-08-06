/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.universe;

import au.com.rmit.Game2dEngine.sprite.Sprite;
import java.awt.Graphics2D;

/**
 *
 * @author ricolwang
 */
public class Entity extends Sprite
{
    public Entity()
    {
        super(0, 0, 0, 0, 0, 0, 0);
        
        this.bCustomDrawing = true;
    }

    @Override
    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        super.onCustomDraw(theGraphics2D); //To change body of generated methods, choose Tools | Templates.
        
        theGraphics2D.setColor(this.getColor());
        theGraphics2D.fillArc(0, 0, (int)this.getWidth() - 1, (int)this.getHeight() - 1, 0, 360);
    }
    
}
