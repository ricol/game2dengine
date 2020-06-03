/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action;

import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class ExpandByAction extends Action
{

    float expandBy;
    float expandByDuration;
    boolean bTheMoveXByActionComplete;
    boolean bTheMoveYByActionComplete;
    boolean bTheScaleWidthByActionComplete;
    boolean bTheScaleHeightByActionComplete;

    MoveXByAction theMoveXByAction;
    MoveYByAction theMoveYByAction;
    ScaleWidthByAction theScaleWidthByAction;
    ScaleHeightByAction theScaleHeightByAction;

    public void expandBy(float expand, float duration)
    {
        this.expandBy = expand;
        this.expandByDuration = abs(duration * 1000);
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (!bTheMoveXByActionComplete)
        {

            if (theMoveXByAction == null)
            {
                theMoveXByAction = new MoveXByAction();
                theMoveXByAction.moveXBy(-this.expandBy, this.expandByDuration / 1000.0f);
                theMoveXByAction.setSprite(theSprite);
            }

            theMoveXByAction.perform(runningTime);

            if (theMoveXByAction.bComplete)
            {
                bTheMoveXByActionComplete = true;
            }
        }

        if (!bTheMoveYByActionComplete)
        {

            if (theMoveYByAction == null)
            {
                theMoveYByAction = new MoveYByAction();
                theMoveYByAction.moveYBy(-this.expandBy, this.expandByDuration / 1000.0f);
                theMoveYByAction.setSprite(theSprite);
            }

            theMoveYByAction.perform(runningTime);

            if (theMoveYByAction.bComplete)
            {
                bTheMoveYByActionComplete = true;
            }
        }

        if (!bTheScaleWidthByActionComplete)
        {

            if (theScaleWidthByAction == null)
            {
                theScaleWidthByAction = new ScaleWidthByAction();
                theScaleWidthByAction.scaleWidthBy(2 * this.expandBy, this.expandByDuration / 1000.0f);
                theScaleWidthByAction.setSprite(theSprite);
            }

            theScaleWidthByAction.perform(runningTime);

            if (theScaleWidthByAction.bComplete)
            {
                bTheScaleWidthByActionComplete = true;
            }
        }

        if (!bTheScaleHeightByActionComplete)
        {

            if (theScaleHeightByAction == null)
            {
                theScaleHeightByAction = new ScaleHeightByAction();
                theScaleHeightByAction.scaleHeightBy(2 * this.expandBy, this.expandByDuration / 1000.0f);
                theScaleHeightByAction.setSprite(theSprite);
            }

            theScaleHeightByAction.perform(runningTime);

            if (theScaleHeightByAction.bComplete)
            {
                bTheScaleHeightByActionComplete = true;
            }
        }

        if (bTheMoveXByActionComplete && bTheMoveYByActionComplete && bTheScaleWidthByActionComplete && bTheScaleHeightByActionComplete)
        {
            bComplete = true;
        }
    }

    @Override
    public void clearSprite()
    {
        this.theMoveXByAction.clearSprite();
        this.theMoveXByAction = null;

        this.theMoveYByAction.clearSprite();
        this.theMoveYByAction = null;

        this.theScaleWidthByAction.clearSprite();
        this.theScaleWidthByAction = null;

        this.theScaleHeightByAction.clearSprite();
        this.theScaleHeightByAction = null;

        this.theSprite = null;
    }

    public float getExpandBy()
    {
        return this.expandBy;
    }

    public float getExpandByDuration()
    {
        return this.expandByDuration;
    }
}
