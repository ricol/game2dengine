package au.com.rmit.Game2dEngine.monitor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ricolwang
 */
public class MouseMonitor implements MouseListener, KeyListener, MouseMotionListener
{

    public boolean leftButtonPressed = false;
    public boolean rightButtonPressed = false;
    public boolean middleButtonPressed = false;
    public int MouseX;
    public int MouseY;
    public boolean mouseEntered = false;

    static MouseMonitor instance = new MouseMonitor();

    public static MouseMonitor getSharedInstance()
    {
        return instance;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        switch (e.getButton())
        {
            case MouseEvent.BUTTON1:
                leftButtonPressed = true;
                break;
            case MouseEvent.BUTTON2:
                middleButtonPressed = true;
                break;
            case MouseEvent.BUTTON3:
                rightButtonPressed = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        switch (e.getButton())
        {
            case MouseEvent.BUTTON1:
                leftButtonPressed = false;
                break;
            case MouseEvent.BUTTON2:
                middleButtonPressed = false;
                break;
            case MouseEvent.BUTTON3:
                rightButtonPressed = false;
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        mouseEntered = true;
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        mouseEntered = false;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        MouseX = e.getX();
        MouseY = e.getY();
    }
}
