package com.wang.Game2dEngine.monitor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ricolwang
 */
public class InputMonitor implements MouseListener, KeyListener, MouseMotionListener
{

    public interface IKeyTyped
    {

        void inputMonitorKeyTyped(char key);
    }

    public boolean leftButtonPressed = false;
    public boolean rightButtonPressed = false;
    public boolean middleButtonPressed = false;
    public int MouseX;
    public int MouseY;
    public boolean mouseEntered = false;

    static InputMonitor instance = new InputMonitor();
    HashMap<Integer, Boolean> keyStatus = new HashMap<>();
    Set<IKeyTyped> keytypedobserver = new HashSet<>();

    public static InputMonitor getSharedInstance()
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
        for (IKeyTyped i : keytypedobserver)
        {
            i.inputMonitorKeyTyped(e.getKeyChar());
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        this.updateKeyStatus(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        this.updateKeyStatus(e.getKeyCode(), false);
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

    public boolean isKeyPressed(int key)
    {
        return keyStatus.getOrDefault(key, false);
    }

    void updateKeyStatus(int key, boolean status)
    {
        keyStatus.put(key, status);
    }

    public void addObserverForKeyTyped(IKeyTyped o)
    {
        this.keytypedobserver.add(o);
    }

    public void removeObserverForKeyTyped(IKeyTyped o)
    {
        this.keytypedobserver.remove(o);
    }
}
