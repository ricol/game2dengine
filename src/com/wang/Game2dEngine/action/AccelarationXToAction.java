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
public class AccelarationXToAction extends AccelarationAction
{
    AccelarationXByAction theAccelarationXByAction;

    double accelarationXTo;
    float accelarationXToDuration;

    public AccelarationXToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.accelarationXTo = this.theSprite.getAccelarationX();
            this.accelarationXToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    public void AccelarationXToAction(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.accelarationXTo = x;
        this.accelarationXToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theAccelarationXByAction.clearSprite();
        this.theAccelarationXByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theAccelarationXByAction == null)
        {
            theAccelarationXByAction = new AccelarationXByAction();
            double tmpX = this.theSprite.getAccelarationX();
            theAccelarationXByAction.accelarationXBy(accelarationXTo - tmpX, this.accelarationXToDuration / 1000.0f);
            theAccelarationXByAction.setSprite(theSprite);
        }

        theAccelarationXByAction.perform(runningTime);
        if (theAccelarationXByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
