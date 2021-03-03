/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.action;

import static java.lang.Math.abs;

/**
 * @author ricolwang
 */
public class MoveCentreYByAction extends MoveAction
{

    MoveYByAction theMoveYByAction;

    double moveCentreYBy;
    float moveCentreYByDuration;

    public MoveCentreYByAction()
    {
        this.moveCentreYBy = 0;
        this.moveCentreYByDuration = 0;
    }

    public void MoveCentreYBy(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.moveCentreYBy = x;
        this.moveCentreYByDuration = abs(duration * 1000);
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theMoveYByAction == null)
        {
            theMoveYByAction = new MoveYByAction();
            theMoveYByAction.moveYBy(moveCentreYBy, this.moveCentreYByDuration / 1000.0f);
            theMoveYByAction.setSprite(theSprite);
        }

        theMoveYByAction.perform(runningTime);
        if (theMoveYByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
