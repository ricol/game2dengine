/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.painter.interfaces

import java.awt.Dimension

/**
 * @author ricolwang
 */
interface IWindow
{
    fun setSize(d: Dimension?)
    val width: Int
    val height: Int
}