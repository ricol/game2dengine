/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

import au.com.rmit.Game2dEngine.node.MovingSprite;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class ScaleWidthToAction extends ScaleAction
{

    double scaleWidthTo;
    float scaleWidthToDuration;
    ScaleWidthByAction theScaleWidthByAction;

    public ScaleWidthToAction(MovingSprite theSprite)
    {
        this.setSprite(theSprite);
        this.scaleWidthTo = this.theSprite.getWidth();
        this.scaleWidthToDuration = 0;
    }
    
    public void ScaleWidthTo(double width, float duration)
    {
        this.scaleWidthTo = width;
        this.scaleWidthToDuration = abs(duration * 1000);
    }

    @Override
    public void clearSprite()
    {
        this.theScaleWidthByAction.clearSprite();
        this.theScaleWidthByAction = null;
        this.theSprite = null;
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theScaleWidthByAction == null)
        {
            theScaleWidthByAction = new ScaleWidthByAction();
            double width = this.theSprite.getWidth();
            theScaleWidthByAction.scaleWidthBy(scaleWidthTo - width, this.scaleWidthToDuration / 1000.0f);
            theScaleWidthByAction.setSprite(theSprite);
        }

        theScaleWidthByAction.perform(runningTime);
        if (theScaleWidthByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
