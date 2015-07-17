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
public class MoveXYToAction extends MoveAction
{

    MoveXYByAction theMoveXYByAction;

    double moveXTo;
    float moveXToDuration;
    double moveXToSpeed;
    double moveXToCurrent;

    double moveYTo;
    float moveYToDuration;
    double moveYToSpeed;
    double moveYToCurrent;

    //duratio in seconds
    public void moveXTo(double x, float duration)
    {
        this.moveXTo = x;
        this.moveXToDuration = abs(duration * 1000);
        this.moveXToSpeed = x / abs(duration * 1000);
        this.moveXToCurrent = 0;
    }

    //duratio in seconds
    public void moveYTo(double y, float duration)
    {
        this.moveYTo = y;
        this.moveYToDuration = abs(duration * 1000);
        this.moveYToSpeed = y / abs(duration * 1000);
        this.moveYToCurrent = 0;
    }

    @Override
    public void perform(double runningTime)
    {
        if (theMoveXYByAction == null)
        {
            theMoveXYByAction = new MoveXYByAction();
            double tmpX = this.theSprite.getX();
            double tmpY = this.theSprite.getY();
            theMoveXYByAction.moveXBy(moveXTo - tmpX, this.moveXToDuration / 1000.0f);
            theMoveXYByAction.moveYBy(moveYTo - tmpY, this.moveYToDuration / 1000.0f);
            theMoveXYByAction.theSprite = this.theSprite;
        }

        theMoveXYByAction.perform(runningTime);
        if (theMoveXYByAction.bComplete)
        {
            bComplete = true;
        }
    }

    @Override
    public void clearSprite()
    {
        this.theSprite = null;
        this.theMoveXYByAction.clearSprite();
    }
}
