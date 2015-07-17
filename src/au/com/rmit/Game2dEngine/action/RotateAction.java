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
public class RotateAction extends Action
{

    public double angleBy;
    public double angleByDuration;

    public double angleTo;
    public double angleToDuration;

    public void rotateBy(double angle, double duration)
    {
        this.angleBy = angle;
        this.angleByDuration = duration;
    }

    public void rotateTo(double angle, double duration)
    {
        this.angleTo = angle;
        this.angleToDuration = duration;
    }

    @Override
    public void perform(double runningTime)
    {
        super.perform(runningTime);
    }
}
