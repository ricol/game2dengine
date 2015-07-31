/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

import au.com.rmit.Game2dEngine.sprite.Sprite;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class AlphaToAction extends AlphaAction
{

    AlphaByAction theAlphaByAction;

    float alphaTo;
    float alphaToDuration;

    public AlphaToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.alphaTo = this.theSprite.getAlpha();
            this.alphaToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    //duration in seconds
    public void alphaTo(float x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.alphaTo = x;
        this.alphaToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theAlphaByAction.clearSprite();
        this.theAlphaByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theAlphaByAction == null)
        {
            theAlphaByAction = new AlphaByAction();
            float alpha = this.theSprite.getAlpha();
            theAlphaByAction.alphaBy(alphaTo - alpha, this.alphaToDuration / 1000.0f);
            theAlphaByAction.setSprite(theSprite);
        }

        theAlphaByAction.perform(runningTime);
        if (theAlphaByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
