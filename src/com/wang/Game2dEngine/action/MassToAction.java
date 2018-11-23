/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action;

import com.wang.Game2dEngine.sprite.Sprite;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class MassToAction extends MassAction
{

    MassByAction theMassByAction;

    double massTo;
    float massToDuration;

    public MassToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.massTo = this.theSprite.getMass();
            this.massToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    //duration in seconds
    public void massTo(float x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.massTo = x;
        this.massToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theMassByAction.clearSprite();
        this.theMassByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theMassByAction == null)
        {
            theMassByAction = new MassByAction();
            double mass = this.theSprite.getMass();
            theMassByAction.massBy(massTo - mass, this.massToDuration / 1000.0f);
            theMassByAction.setSprite(theSprite);
        }

        theMassByAction.perform(runningTime);
        if (theMassByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
