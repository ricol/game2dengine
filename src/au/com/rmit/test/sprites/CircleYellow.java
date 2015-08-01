/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.sprites;

import au.com.rmit.test.TestCommon;

/**
 *
 * @author ricolwang
 */
public class CircleYellow extends CircleSprite
{

    public CircleYellow()
    {
        this.setRed(255);
        this.setGreen(255);
        this.setWidth(100);
        this.setHeight(this.getWidth());

        this.bCustomDrawing = true;
        this.bCollisionDetect = true;
        this.bDrawCircle = true;
        this.identifier = "CircleYellow";

        this.setCollisionCategory(TestCommon.CATEGORY_CIRCLE_RED);

        this.addTargetCollisionCategory(TestCommon.CATEGORY_WALL);
        this.addTargetCollisionCategory(TestCommon.CATEGORY_CIRCLE_BLUE);
    }

    @Override
    public Object getACopy()
    {
        Object aCopy = new CircleYellow();
        this.copyContent(aCopy);
        return aCopy;
    }

}
