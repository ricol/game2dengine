/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.geometry;

import au.com.rmit.Game2dEngine.geometry.Point;

/**
 *
 * @author ricolwang
 */
public class Line
{
    public Point start;
    public Point end;
    
    public Line(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }
}
