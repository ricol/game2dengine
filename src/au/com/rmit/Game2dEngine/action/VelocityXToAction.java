/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

import au.com.rmit.Game2dEngine.node.Sprite;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class VelocityXToAction extends VelocityAction
{

    VelocityXByAction theVelocityXByAction;

    double velocityXTo;
    float velocityXToDuration;

    public VelocityXToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.velocityXTo = this.theSprite.getVelocityX();
            this.velocityXToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    public void velocityXTo(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.velocityXTo = x;
        this.velocityXToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theVelocityXByAction.clearSprite();
        this.theVelocityXByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theVelocityXByAction == null)
        {
            theVelocityXByAction = new VelocityXByAction();
            double tmpX = this.theSprite.getVelocityX();
            theVelocityXByAction.velocityXBy(velocityXTo - tmpX, this.velocityXToDuration / 1000.0f);
            theVelocityXByAction.setSprite(theSprite);
        }

        theVelocityXByAction.perform(runningTime);
        if (theVelocityXByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
