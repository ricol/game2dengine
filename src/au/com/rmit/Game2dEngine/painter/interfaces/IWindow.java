/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.painter.interfaces;

import java.awt.Dimension;

/**
 *
 * @author ricolwang
 */
public interface IWindow
{

    public void setSize(Dimension d);

    public int getWidth();

    public int getHeight();
}
