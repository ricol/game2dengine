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
public class AccelarationYToAction extends AccelarationAction
{

    AccelarationYByAction theAccelarationYByAction;

    double accelarationYTo;
    float accelarationYToDuration;

    public AccelarationYToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.accelarationYTo = this.theSprite.getAccelarationY();
            this.accelarationYToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    public void accelarationYTo(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.accelarationYTo = x;
        this.accelarationYToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theAccelarationYByAction.clearSprite();
        this.theAccelarationYByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theAccelarationYByAction == null)
        {
            theAccelarationYByAction = new AccelarationYByAction();
            double tmpY = this.theSprite.getAccelarationY();
            theAccelarationYByAction.accelarationYBy(accelarationYTo - tmpY, this.accelarationYToDuration / 1000.0f);
            theAccelarationYByAction.setSprite(theSprite);
        }

        theAccelarationYByAction.perform(runningTime);
        if (theAccelarationYByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
