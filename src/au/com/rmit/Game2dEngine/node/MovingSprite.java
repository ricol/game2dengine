/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import au.com.rmit.Game2dEngine.action.Action;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author ricolwang
 */
public class MovingSprite extends Sprite
{

    public boolean bDeadIfNoActions;
    protected double velocityX;
    protected double velocityY;

    protected Set<Action> theSetOfActions = new HashSet<>();
    Set<Action> theSetOfActionsDeleted = new HashSet<>();
    Set<Action> theSetOfActionsAdded = new HashSet<>();
    
    protected Queue<Action> theQueueOfActions = new LinkedList<>();

    public MovingSprite(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass);

        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
    
    public MovingSprite(String imagename)
    {
        super(imagename);
    }

    @Override
    public void updateState(double currentTime)
    {
        super.updateState(currentTime); //To change body of generated methods, choose Tools | Templates.

        if (this.isAlive())
        {
            //how much time passed since last update
            double delta = currentTime - this.lastUpdateTime;
            double t = delta / 1000.0f; //in seconds

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
            
            //perform a action in the queue one by one in sequence
            Action theHeadAction = this.theQueueOfActions.peek();
            if (theHeadAction != null)
            {
                this.onActionRunning(theHeadAction);
                theHeadAction.perform(delta);
                
                if (theHeadAction.bComplete)
                {
                    this.deQueueAction(theHeadAction);
                    this.onActionComplete(theHeadAction);
                }
            }
            
            //perform other actions
           
            //delete old actions
            this.theSetOfActions.removeAll(this.theSetOfActionsDeleted);
            this.theSetOfActionsDeleted.clear();

            //add new actions
            this.theSetOfActions.addAll(this.theSetOfActionsAdded);
            this.theSetOfActionsAdded.clear();

            if (this.theSetOfActions.size() <= 0)
            {
                if (this.bDeadIfNoActions)
                {
                    this.setDead();
                }
            } else
            {
                //run actions
                for (Action aAction : this.theSetOfActions)
                {
                    this.onActionRunning(aAction);
                    aAction.perform(delta);

                    if (aAction.bComplete)
                    {
                        this.theSetOfActionsDeleted.add(aAction);
                        this.onActionComplete(aAction);
                    }
                }
            }

            this.lastUpdateTime = currentTime;
        }
    }

    public void addAction(Action aAction)
    {
        aAction.setSprite(this);
        this.theSetOfActionsAdded.add(aAction);
    }

    public void removeAction(Action aAction)
    {
        aAction.clearSprite();
        this.theSetOfActionsDeleted.remove(aAction);
    }
    
    public void enQueueAction(Action aAction)
    {
        aAction.setSprite(this);
        this.theQueueOfActions.add(aAction);
    }
    
    public void deQueueAction(Action aAction)
    {
        aAction.clearSprite();
        this.theQueueOfActions.remove(aAction);
    }

    public int getActionCount()
    {
        return this.theSetOfActions.size();
    }
    
    public int getQueueActionCount()
    {
        return this.theQueueOfActions.size();
    }

    public void setVelocityX(double value)
    {
        this.velocityX = value;
    }

    public void setVelocityY(double value)
    {
        this.velocityY = value;
    }

    public double getVelocityX()
    {
        return this.velocityX;
    }

    public double getVelocityY()
    {
        return this.velocityY;
    }

    public void onActionRunning(Action theAction)
    {
    }

    public void onActionComplete(Action theAction)
    {
    }
}
