/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action;

import com.wang.Game2dEngine.sprite.Sprite;

import static java.lang.Math.abs;

/**
 * @author ricolwang
 */
public class RotateToAction extends RotateAction
{

    RotateByAction theRotateByAction;
    double rotateTo;
    double rotateToDuration;

    public RotateToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.rotateTo = this.theSprite.getAngle();
            this.rotateToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    public void rotateTo(double angle, double duration)
    {
        this.rotateTo = angle;
        this.rotateToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theRotateByAction.clearSprite();
        this.theRotateByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theRotateByAction == null)
        {
            theRotateByAction = new RotateByAction();
            double angle = this.theSprite.getAngle();
            theRotateByAction.rotateBy(rotateTo - angle, rotateToDuration / 1000.0f);
            theRotateByAction.setSprite(theSprite);
        }

        theRotateByAction.perform(runningTime);
        if (theRotateByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
