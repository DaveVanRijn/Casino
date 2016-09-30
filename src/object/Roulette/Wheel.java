/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object.roulette;

import views.roulette.Betting;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.Timer;
import static resources.java.shared.ImageLabel.getImageIcon;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Wheel extends JComponent {

    boolean doDelay = true;
    private final int DELAY;
    private final Timer TIME;
    private Image wheel;
    private int angle = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        angle += 1;
        AffineTransform trans = AffineTransform.getRotateInstance(angle / 10.0, wheel.getWidth(null) / 2, wheel.getHeight(null) / 2);
        ((Graphics2D) g).drawImage(wheel, trans, this);
    }

    public Wheel(double endAngle) {
        this.DELAY = 15;
        this.TIME = new Timer(DELAY, null);
        wheel = getImageIcon("wheel").getImage();
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (angle >= endAngle) {
                    TIME.stop();
                    angle = (int) (endAngle - 1);
                    wheel = getImageIcon("wheel").getImage();
                    Wheel.this.repaint();
                }
                if (!TIME.isRunning()) {
                    try {
                        Thread.sleep(400);
                        Betting.closeWheelFrame();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Wheel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Wheel.this.repaint();
                if (angle >= toRadian(1080) * 10) {
                    if (doDelay) {
                        TIME.setDelay(TIME.getDelay() + 1);
                        doDelay = false;
                    } else {
                        doDelay = true;
                    }
                }
            }

        };
        TIME.addActionListener(taskPerformer);
        TIME.start();
    }

    private double toRadian(double number) {
        return Math.toRadians(number);
    }
}
