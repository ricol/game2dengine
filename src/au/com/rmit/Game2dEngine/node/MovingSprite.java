/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import au.com.rmit.Game2dEngine.action.Action;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Philology
 */
public class MovingSprite extends Sprite
{
    public boolean bDeadIfNoActions;
    protected double angle;
    protected double velocityX;
    protected double velocityY;

    protected Queue<Action> theQueueOfActions = new LinkedList<>();

    public MovingSprite(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass);

        this.velocityX = velocityX;
        this.velocityY = velocityY;

        this.angle = 0;
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
            Action aAction = this.theQueueOfActions.peek();

            if (aAction != null)
            {
                aAction.perform(currentTime - this.lastUpdateTime);

                if (aAction.bComplete)
                {
                    this.removeAction(aAction);
                }
            }else
            {
                if (this.bDeadIfNoActions)
                {
                    this.isAlive = false;
                }
            }

            this.lastUpdateTime = currentTime;
        }
    }

    @Override
    public void updateGUI(Graphics g)
    {
        if (this.isAlive)
        {
            if (this.theImage != null)
            {
                g.drawImage(theImage, (int) x, (int) y, (int) this.width, (int) this.height, null);
            } else
            {
                if (color == null)
                {
                    g.setColor(Color.RED);
                } else
                {
                    g.setColor(color);
                }

                g.fillArc((int) x, (int) y, (int) width, (int) height, 0, 360);
            }
        }
    }

    public void addAction(Action aAction)
    {
        aAction.setSprite(this);
        this.theQueueOfActions.add(aAction);
//        System.out.println("Action added." + this.theQueueOfActions.size());
    }

    public void removeAction(Action aAction)
    {
        aAction.clearSprite();
        this.theQueueOfActions.remove(aAction);
//        System.out.println("Action removed. " + this.theQueueOfActions.size());
    }
    
    public int getActionCount()
    {
        return this.theQueueOfActions.size();
    }
}
