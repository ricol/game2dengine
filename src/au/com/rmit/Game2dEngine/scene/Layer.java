/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.scene;

import au.com.rmit.Game2dEngine.node.Sprite;
import java.util.ArrayList;

/**
 *
 * @author ricolwang
 */
public class Layer
{

    public Scene theScene;
    public int zOrder = -1;

    public ArrayList<Sprite> AllObjects = new ArrayList();
    public ArrayList<Sprite> DeadObjects = new ArrayList();
    public ArrayList<Sprite> NewObjects = new ArrayList();

    public Layer(int zOrder, Scene theScene)
    {
        this.zOrder = zOrder;
        this.theScene = theScene;
    }

    public void addSprite(Sprite aSprite)
    {
        this.NewObjects.add(aSprite);
        aSprite.theScene = this.theScene;
    }
}
