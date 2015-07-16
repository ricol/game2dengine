/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import au.com.rmit.Game2dEngine.scene.Scene;
import java.util.Random;

/**
 *
 * @author ricolwang
 */
public class Node
{

    public Scene theScene;
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    protected Random theRandom = new Random();

    public Node(double x, double y, double width, double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
