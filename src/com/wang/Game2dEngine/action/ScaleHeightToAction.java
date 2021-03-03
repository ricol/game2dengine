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
public class ScaleHeightToAction extends ScaleAction
{

    double scaleHeightTo;
    float scaleHeightToDuration;
    ScaleHeightByAction theScaleHeightByAction;

    public ScaleHeightToAction(Sprite theSprite)
    {
        this.setSprite(theSprite);
        this.scaleHeightTo = this.theSprite.getHeight();
        this.scaleHeightToDuration = 0;
    }

    public void ScaleHeightTo(double height, float duration)
    {
        this.scaleHeightTo = height;
        this.scaleHeightToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theScaleHeightByAction.clearSprite();
        this.theScaleHeightByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theScaleHeightByAction == null)
        {
            theScaleHeightByAction = new ScaleHeightByAction();
            double height = this.theSprite.getHeight();
            theScaleHeightByAction.scaleHeightBy(scaleHeightTo - height, this.scaleHeightToDuration / 1000.0f);
            theScaleHeightByAction.setSprite(theSprite);
        }

        theScaleHeightByAction.perform(runningTime);
        if (theScaleHeightByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
