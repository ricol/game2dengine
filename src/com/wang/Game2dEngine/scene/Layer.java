/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.scene;

import com.wang.Game2dEngine.sprite.Sprite;

import java.util.ArrayList;

/**
 * @author ricolwang
 */
public class Layer
{

    public Scene theScene;
    public int zOrder;

    public ArrayList<Sprite> AllObjects = new ArrayList<>();
    public ArrayList<Sprite> DeadObjects = new ArrayList<>();
    public ArrayList<Sprite> NewObjects = new ArrayList<>();

    public Layer(int zOrder, Scene theScene)
    {
        this.zOrder = zOrder;
        this.theScene = theScene;
    }

    public void addSprite(Sprite aSprite)
    {
        this.NewObjects.add(aSprite);
        aSprite.theScene = this.theScene;
        aSprite.onAddToLayer(this);
    }
}
