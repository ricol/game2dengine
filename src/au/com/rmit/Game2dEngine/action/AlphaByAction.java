/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class AlphaByAction extends AlphaAction
{

    float alphaBy;
    float alphaByDuration;
    float alphaByBySpeed;
    float alphaByCurrent;

    public void alphaBy(float alpha, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
            this.bImmediately = true;
        }
        this.alphaBy = alpha;
        this.alphaByDuration = abs(duration * 1000);
        if (!this.bImmediately)
        {
            this.alphaByBySpeed = alpha / abs(duration * 1000);
        }
        this.alphaByCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bImmediately)
        {
            this.theSprite.setAlpha(this.theSprite.getAlpha() + alphaBy);
            bComplete = true;
        }

        if (bComplete)
        {
            return;
        }

        if (abs(this.alphaBy) > Action.MINIMUM)
        {
            float alpha = this.theSprite.getAlpha();
            double value = this.alphaByBySpeed * runningTime;
            this.alphaByCurrent += value;

            if (this.alphaByBySpeed > 0)
            {
                if (this.alphaByCurrent > this.alphaBy)
                {
                    bComplete = true;
                } else
                {
                    alpha += value;
                }
            } else
            {
                if (this.alphaByCurrent < this.alphaBy)
                {
                    bComplete = true;
                } else
                {
                    alpha += value;
                }
            }
            this.theSprite.setAlpha(alpha);
        } else
        {
            bComplete = true;
        }

    }
}
