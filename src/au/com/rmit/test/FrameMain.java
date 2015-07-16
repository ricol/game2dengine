package au.com.rmit.test;

import au.com.rmit.Game2dEngine.allScenes.FireworksScene;
import au.com.rmit.Game2dEngine.allScenes.FountainScene;
import au.com.rmit.Game2dEngine.allScenes.RandomShapeScene;
import au.com.rmit.Game2dEngine.director.Director;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Philology
 */
public class FrameMain extends javax.swing.JFrame
{

    public FrameMain()
    {
        initComponents();

        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent evt)
            {
                int x = 25, y = 50;
                panelGame.setLocation(x, y);
                panelGame.setSize(new Dimension(getWidth() - 2 * x, getHeight() - 2 * y));
                Director.getSharedInstance().updatePosition(0, 0, panelGame.getWidth(), panelGame.getHeight());
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        btnPause = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        panelGame = new javax.swing.JPanel();
        btnFountain = new javax.swing.JButton();
        btnSpiral = new javax.swing.JButton();
        btnFireworks = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                formWindowOpened(evt);
            }
        });

        btnPause.setText("Pause");
        btnPause.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnPauseActionPerformed(evt);
            }
        });

        btnStop.setText("Stop");
        btnStop.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnStopActionPerformed(evt);
            }
        });

        panelGame.setBackground(new java.awt.Color(0, 102, 51));

        javax.swing.GroupLayout panelGameLayout = new javax.swing.GroupLayout(panelGame);
        panelGame.setLayout(panelGameLayout);
        panelGameLayout.setHorizontalGroup(
            panelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelGameLayout.setVerticalGroup(
            panelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );

        btnFountain.setText("Fountain");
        btnFountain.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnFountainActionPerformed(evt);
            }
        });

        btnSpiral.setText("Spiral");
        btnSpiral.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSpiralActionPerformed(evt);
            }
        });

        btnFireworks.setText("Fireworks");
        btnFireworks.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnFireworksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPause)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 339, Short.MAX_VALUE)
                .addComponent(btnFireworks)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFountain)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSpiral))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(panelGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPause)
                    .addComponent(btnStop)
                    .addComponent(btnFountain)
                    .addComponent(btnSpiral)
                    .addComponent(btnFireworks))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        Director.getSharedInstance().setParent(this.panelGame);
    }//GEN-LAST:event_formWindowOpened

    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPauseActionPerformed
    {//GEN-HEADEREND:event_btnPauseActionPerformed
        // TODO add your handling code here:
        if (Director.getSharedInstance().currentScene != null)
        {
            Director.getSharedInstance().currentScene.pause();
        }
    }//GEN-LAST:event_btnPauseActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStopActionPerformed
    {//GEN-HEADEREND:event_btnStopActionPerformed
        // TODO add your handling code here:
        if (Director.getSharedInstance().currentScene != null)
        {
            Director.getSharedInstance().currentScene.stop();
        }
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnFountainActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnFountainActionPerformed
    {//GEN-HEADEREND:event_btnFountainActionPerformed
        // TODO add your handling code here:
        Director.getSharedInstance().showScene(new FountainScene());
    }//GEN-LAST:event_btnFountainActionPerformed

    private void btnSpiralActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSpiralActionPerformed
    {//GEN-HEADEREND:event_btnSpiralActionPerformed
        // TODO add your handling code here:
        Director.getSharedInstance().showScene(new RandomShapeScene());
    }//GEN-LAST:event_btnSpiralActionPerformed

    private void btnFireworksActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnFireworksActionPerformed
    {//GEN-HEADEREND:event_btnFireworksActionPerformed
        // TODO add your handling code here:
        Director.getSharedInstance().showScene(new FireworksScene());
    }//GEN-LAST:event_btnFireworksActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFireworks;
    private javax.swing.JButton btnFountain;
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnSpiral;
    private javax.swing.JButton btnStop;
    private javax.swing.JPanel panelGame;
    // End of variables declaration//GEN-END:variables
}
