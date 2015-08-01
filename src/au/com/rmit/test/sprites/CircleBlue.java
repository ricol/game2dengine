/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.sprites;

import au.com.rmit.test.TestCommon;

/**
 *
 * @author Philology
 */
public class CircleBlue extends CircleSprite
{

    public CircleBlue()
    {
        this.setBlue(255);
        this.setWidth(100);
        this.setHeight(this.getWidth());

        this.bCustomDrawing = true;
        this.bCollisionDetect = true;
        this.bDrawCircle = true;
        this.identifier = "CircleBlue";

        this.setCollisionCategory(TestCommon.CATEGORY_CIRCLE_BLUE);

        this.addTargetCollisionCategory(TestCommon.CATEGORY_WALL);
        this.addTargetCollisionCategory(TestCommon.CATEGORY_CIRCLE_RED);
    }

    @Override
    public Object getACopy()
    {
        Object aCopy = new CircleBlue();
        this.copyContent(aCopy);
        return aCopy;
    }
}
