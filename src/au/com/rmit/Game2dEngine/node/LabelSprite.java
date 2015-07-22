/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Philology
 */
public class LabelSprite extends MovingSprite
{

    String text = "";
    public boolean bTextFrame = true;
    Font textFont = new Font("TimesRoman", Font.PLAIN, 15);
    public Color textFrameColor = Color.blue;
    public int textPosX = 10;
    public int textPosY = 10;

    public LabelSprite(double x, double y, String text, Font textFont)
    {
        super(x, y, 0, 0, 0, 0, 0);

        this.bCustomDrawing = true;
        this.text = text;
        if (textFont != null)
        {
            this.textFont = textFont;
        }
        this.setGreen(255);
    }

    @Override
    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        if (bTextFrame)
        {
            theGraphics2D.setColor(textFrameColor);
            theGraphics2D.drawRect(0, 0, (int) width - 1, (int) height - 1);
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
