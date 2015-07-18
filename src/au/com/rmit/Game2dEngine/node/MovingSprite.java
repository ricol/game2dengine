/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import au.com.rmit.Game2dEngine.action.Action;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Philology
 */
public class MovingSprite extends Sprite
{

    public boolean bDeadIfNoActions;
    protected double velocityX;
    protected double velocityY;

    protected Set<Action> theSetOfActions = new HashSet<>();
    Set<Action> theSetOfActionsDeleted = new HashSet<>();

    public MovingSprite(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass);

        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    @Override
    public void updateState(double currentTime)
    {
        super.updateState(currentTime); //To change body of generated methods, choose Tools | Templates.

        if (this.isAlive)
        {
            //how much time passed since last update
            double t = (currentTime - this.lastUpdateTime) / 1000.0f; //in seconds

            //update state
            if (this.g != null)
            {
                velocityX += this.g.GX * t;
                velocityY += this.g.GY * t;
            }

            double IncX = velocityX * t;
            double IncY = velocityY * t;

            x += IncX;
            y += IncY;

            //perform actions
            //delete old actions
            this.theSetOfActions.removeAll(this.theSetOfActionsDeleted);
            this.theSetOfActionsDeleted.clear();

            if (this.theSetOfActions.size() <= 0)
            {
                if (this.bDeadIfNoActions)
                {
                    this.isAlive = false;
                }
            } else
            {
                //run actions
                for (Action aAction : this.theSetOfActions)
                {
                    aAction.perform(currentTime - this.lastUpdateTime);

                    if (aAction.bComplete)
                    {
                        this.theSetOfActionsDeleted.add(aAction);
                    }
                }
            }

            this.lastUpdateTime = currentTime;
        }
    }

    public void addAction(Action aAction)
    {
        aAction.setSprite(this);
        this.theSetOfActions.add(aAction);
    }

    public void removeAction(Action aAction)
    {
        aAction.clearSprite();
        this.theSetOfActions.remove(aAction);
    }

    public int getActionCount()
    {
        return this.theSetOfActions.size();
    }
}
