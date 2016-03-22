/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Roulette;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Wheel extends JComponent{
    
    boolean doDelay = true;
    private int delay = 15; //milliseconds
    private final Timer time = new Timer(delay, null);
    Image wheel;
        int angle = 0;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            angle += 1;
            AffineTransform trans = AffineTransform.getRotateInstance(angle / 10.0, wheel.getWidth(null) / 2, wheel.getHeight(null) / 2);
            ((Graphics2D) g).drawImage(wheel, trans, this);
        }

        public Wheel(double endAngle) {
            wheel = new ImageIcon(getClass().getResource("/Img/wheel.png")).getImage();
            ActionListener taskPerformer = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Wheel.this.repaint();
                    if (angle >= endAngle) {
                        time.stop();
                    }
                    if (!time.isRunning()) {
                        try {
                            Thread.sleep(400);
                            Betting.closeWheelFrame();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (angle >= toRadian(1080) * 10) {
                        if (doDelay) {
                            time.setDelay(time.getDelay() + 1);
                            doDelay = false;
                        } else {
                            doDelay = true;
                        }
                    }
                }
            };
            time.addActionListener(taskPerformer);
            time.start();
        }

        private double toRadian(double number) {
            return Math.toRadians(number);
        }
}