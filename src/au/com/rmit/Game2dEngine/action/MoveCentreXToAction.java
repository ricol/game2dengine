/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.action;

import au.com.rmit.Game2dEngine.sprite.Sprite;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class MoveCentreXToAction extends MoveAction
{

    MoveXToAction theMoveXToAction;

    double moveCentreXTo;
    float moveCentreXToDuration;

    public MoveCentreXToAction(Sprite theSprite)
    {
        if (theSprite != null)
        {
            this.setSprite(theSprite);

            this.moveCentreXTo = this.theSprite.getCentreX();
            this.moveCentreXToDuration = 0;

        } else
        {
            bComplete = true;
        }
    }

    public void MoveCentreXTo(double x, float duration)
    {
        if (duration <= 0)
        {
            duration = 0;
        }
        this.moveCentreXTo = x;
        this.moveCentreXToDuration = abs(duration * 1000);
    }

    @Override
    public void perform(double runningTime)
    {
        if (bComplete)
        {
            return;
        }

        if (theMoveXToAction == null)
        {
            theMoveXToAction = new MoveXToAction(this.theSprite);
            theMoveXToAction.moveXTo(moveCentreXTo - this.theSprite.getWidth() / 2.0, this.moveCentreXToDuration / 1000.0f);
            theMoveXToAction.setSprite(theSprite);
        }

        theMoveXToAction.perform(runningTime);
        if (theMoveXToAction.bComplete)
        {
            bComplete = true;
        }
    }

    @Override
    public void clearSprite()
    {
        this.theMoveXToAction.clearSprite();
        this.theMoveXToAction = null;
        this.theSprite = null;
    }
}
