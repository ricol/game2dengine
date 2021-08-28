/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape.Interface

import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics
import java.awt.Color

/**
 * @author ricolwang
 */
interface IEShape : IENode
{
    val shape: IEShape?
    fun refresh(changeX: Double, changeY: Double, changeWidth: Double, changeHeight: Double)
    fun draw(theGraphicsInTheScene: IEngineGraphics, theColor: Color?)
}