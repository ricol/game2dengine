/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

/**
 *
 * @author ricolwang
 */
public class ScaleWidthHeightToAction extends Action
{

    double scaleTo;
    float scaleToDuration;
    double scaleToSpeed;
    double scaleToCurrent;
    boolean scaleToComplete;

    public ScaleWidthHeightToAction()
    {
        this.scaleTo = 0;
        this.scaleToDuration = 0;
        this.scaleToSpeed = 0;
        this.scaleToCurrent = 0;
        this.scaleToComplete = false;
    }

    public void scaleTo(double time, float duration)
    {
        if (duration <= 0)
        {
            duration = (float) Action.MINIMUM_DURATION;
        }
        this.scaleTo = time;
        this.scaleToDuration = duration;
    }

    @Override
    public void perform(double runningTime)
    {

    }
}
