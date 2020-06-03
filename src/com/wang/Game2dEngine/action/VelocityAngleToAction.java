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
public class VelocityAngleToAction extends VelocityAngleAction
{

    VelocityAngleByAction theVelocityAngleByAction;

    double velocityAngleTo;
    float velocityAngleToDuration;

    public VelocityAngleToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.velocityAngleTo = this.theSprite.getVelocityAngle();
            this.velocityAngleToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    public void velocityAngleTo(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.velocityAngleTo = x;
        this.velocityAngleToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theVelocityAngleByAction.clearSprite();
        this.theVelocityAngleByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theVelocityAngleByAction == null)
        {
            theVelocityAngleByAction = new VelocityAngleByAction();
            double tmpAngle = this.theSprite.getVelocityAngle();
            theVelocityAngleByAction.velocityAngleBy(velocityAngleTo - tmpAngle, this.velocityAngleToDuration / 1000.0f);
            theVelocityAngleByAction.setSprite(theSprite);
        }

        theVelocityAngleByAction.perform(runningTime);
        if (theVelocityAngleByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
