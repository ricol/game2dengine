/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.painter.interfaces;

import java.awt.event.ComponentAdapter;
import java.awt.event.MouseListener;

/**
 *
 * @author ricolwang
 */
public interface IUserInteraction
{

    public void addMouseListener(MouseListener listener);

    public void addComponentListener(ComponentAdapter componentAdapter);
}
