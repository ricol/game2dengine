/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class MoveCentreXByAction extends MoveAction
{

    MoveXByAction theMoveXByAction;

    double moveCentreXBy;
    float moveCentreXByDuration;

    public MoveCentreXByAction()
    {
        this.moveCentreXBy = 0;
        this.moveCentreXByDuration = 0;
    }

    //duratio in seconds
    public void MoveCentreXBy(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = (float) Action.MINIMUM_DURATION;
        }
        this.moveCentreXBy = x;
        this.moveCentreXByDuration = abs(duration * 1000);
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theMoveXByAction == null)
        {
            theMoveXByAction = new MoveXByAction();
            theMoveXByAction.moveXBy(moveCentreXBy, this.moveCentreXByDuration / 1000.0f);
            theMoveXByAction.setSprite(theSprite);
        }

        theMoveXByAction.perform(runningTime);
        if (theMoveXByAction.bComplete)
        {
            bComplete = true;
        }
    }
}
