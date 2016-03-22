/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Roulette;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Edit extends JFrame {

    boolean doDelay = true;
    private int delay = 15; //milliseconds
    private final Timer time = new Timer(delay, null);

    public Edit(double endAngle) {
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new ImageRotationComponent(endAngle), BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    //Maak gewoon een class van hieronder, class wheel doet verder helemaal niks
    //en dan kan je vsnuit betting gewoon een frame maken met deze class erin,
    //zodat je m vanuit betting ook weer kan sluiten.

    private class ImageRotationComponent extends JComponent {

        Image wheel;
        int angle = 0;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            angle += 1;
            AffineTransform trans = AffineTransform.getRotateInstance(angle / 10.0, wheel.getWidth(null) / 2, wheel.getHeight(null) / 2);
            ((Graphics2D) g).drawImage(wheel, trans, this);
        }

        public ImageRotationComponent(double endAngle) {
            wheel = new ImageIcon(getClass().getResource("/Img/wheel.png")).getImage();
            ActionListener taskPerformer = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    ImageRotationComponent.this.repaint();
                    if (angle >= endAngle) {
                        time.stop();
                    }
                    if (!time.isRunning()) {
                        
                    }
                    if (angle >= toRadian(1440) * 10) {
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
}
