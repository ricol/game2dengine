/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.test.universe;

/**
 *
 * @author ricolwang
 */
public class Planet extends Entity
{
    public Planet()
    {
        super();
        
        this.setGreen(255);
        this.enableGravity();
    }
}
