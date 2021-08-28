/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Shape.Interface

import com.wang.Game2dEngine.sprite.Node

/**
 * @author ricolwang
 */
interface IENode
{
    val node: Node?
    fun setTheNode(theNode: Node?)
}