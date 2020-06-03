/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.sprite.UI;

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import com.wang.Game2dEngine.sprite.Sprite;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author ricolwang Remember to set width and height of the text
 */
public class SLabel extends Sprite
{

    String text = "";
    public boolean bTextFrame = true;
    Font textFont = new Font("TimesRoman", Font.PLAIN, 15);
    public Color textFrameColor = Color.blue;
    public int textPosX = 10;
    public int textPosY = 10;

    public SLabel(double x, double y, String text, Font theTextFont)
    {
        super(x, y, 0, 0, 0, 0, 0);

        this.bCustomDrawing = true;
        this.text = text;
        if (theTextFont != null)
        {
            this.textFont = theTextFont;
        }
        this.setGreen(255);
    }

    public SLabel(String text, Font theTextFont)
    {
        super(0, 0, 0, 0, 0, 0, 0);

        this.bCustomDrawing = true;
        this.text = text;
        if (theTextFont != null)
        {
            this.textFont = theTextFont;
        }
        this.setGreen(255);
    }

    public SLabel(String text)
    {
        super(0, 0, 0, 0, 0, 0, 0);

        this.bCustomDrawing = true;
        this.text = text;
        this.setGreen(255);
    }

    @Override
    public void onCustomDraw(IEngineGraphics theGraphics2D)
    {
        super.onCustomDraw(theGraphics2D);

        if (bTextFrame)
        {
            theGraphics2D.setColor(textFrameColor);
            theGraphics2D.drawRect(0, 0, (int) getWidth() - 1, (int) getHeight() - 1);
        }

        theGraphics2D.setFont(textFont);
        theGraphics2D.setColor(getColor());
        theGraphics2D.drawString(text, textPosX, textPosY);
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return this.text;
    }

}
