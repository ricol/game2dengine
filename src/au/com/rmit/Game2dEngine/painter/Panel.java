/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.painter;

import au.com.rmit.Game2dEngine.painter.interfaces.IPanelDelegate;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author ricolwang
 */
public class Panel extends JPanel
{

    IPanelDelegate delegate;

    Panel(IPanelDelegate delegate)
    {
        super();
        this.delegate = delegate;
    }

    @Override
    public void paint(Graphics g)
    {
        delegate.update(g);
        
        try
        {
            Thread.sleep(1);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.repaint();
    }
}