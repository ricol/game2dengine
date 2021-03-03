/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape.Interface;

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;

import java.awt.*;

/**
 * @author ricolwang
 */
public interface IEShape extends IENode
{

    public IEShape getShape();

    public void refresh(double changeX, double changeY, double changeWidth, double changeHeight);

    public void draw(IEngineGraphics theGraphicsInTheScene, Color theColor);
}
