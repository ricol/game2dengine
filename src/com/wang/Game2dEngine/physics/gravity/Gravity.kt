/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.physics.gravity

/**
 * @author ricolwang
 */
class Gravity
{
    var GX: Double
    var GY: Double

    constructor(gx: Double, gy: Double)
    {
        GX = gx
        GY = gy
    }

    constructor(gx: Int, gy: Int)
    {
        GX = gx.toDouble()
        GY = gy.toDouble()
    }
}