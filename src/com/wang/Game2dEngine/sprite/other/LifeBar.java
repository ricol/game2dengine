/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.sprite.other;

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import com.wang.Game2dEngine.sprite.Sprite;

import java.awt.*;

/**
 * @author ricolwang
 */
public class LifeBar extends Sprite
{

    int current;
    int life;

    public LifeBar(int width, int height, int life)
    {
        super(0, 0, width, height);

        this.life = life;
        this.current = life;
        this.bCustomDrawing = true;
        this.setRed(255);
    }

    @Override
    public void onCustomDraw(IEngineGraphics theGraphics2D)
    {
        super.onCustomDraw(theGraphics2D); //To change body of generated methods, choose Tools | Templates.

        theGraphics2D.setColor(this.getColor());
        theGraphics2D.drawRect(0, 0, (int) this.getWidth() - 1, (int) this.getHeight() - 1);

        if (current <= life && current >= 0)
        {
            float width = (float) (((current * 1.0) / life) * this.getWidth() * 1.0);
            theGraphics2D.setColor(Color.blue);
            theGraphics2D.fillRect(1, 1, (int) width - 2, (int) this.getHeight() - 2);
        }
    }

    public void decreaseLifeBy(int value)
    {
        this.current -= value;
    }
}
