/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object.blackjack;

import views.shared.Main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import static views.shared.Main.getScreenSize;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class CardLabel extends JLabel {

    private final BufferedImage IMAGE;
    private final Point LOCATION;
    private final int TIMER_DELAY;
    private final int START_X;
    private final int START_Y;
    private final double END_DEGREES;

    private int timerTarget;
    private boolean slide;
    private double degrees;

    public CardLabel(ImageIcon icon, double degrees, Point location, Dimension size) {
        this(icon, degrees, location, size, false);
    }

    public CardLabel(ImageIcon icon, double degrees, Point location, Dimension size, boolean slide) {
        super();
        this.TIMER_DELAY = 15;
        IMAGE = toBuffered(icon);

        setSize(size);
        this.slide = slide;
        this.LOCATION = location;
        timerTarget = 1000;
        if (slide) {
            this.slide = false;
            START_X = getScreenSize().width / 2 - getSize().width / 2;
            START_Y = getSize().height / 2 * -1;
            END_DEGREES = degrees;
            this.degrees = 0;
            setLocation(START_X, START_Y);
        } else {
            setLocation(location);
            START_X = location.x;
            START_Y = location.y;
            END_DEGREES = this.degrees = degrees;
        }
    }

    public void slide() {
        slide = true;
        getTimer().start();
    }

    private BufferedImage toBuffered(ImageIcon icon) {
        BufferedImage buff = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = buff.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return buff;
    }

    private Timer getTimer() {
        int endX = LOCATION.x;
        int endY = LOCATION.y;

        Timer timer = new Timer(TIMER_DELAY, null);

        ActionListener al = new ActionListener() {
            int i = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                int x = CardLabel.this.getLocation().x;
                int y = CardLabel.this.getLocation().y;

                if (timerTarget <= 0 || x == endX || y > endY) {
                    CardLabel.this.setLocation(endX, endY);
                    slide = false;
                    degrees = END_DEGREES;
                    CardLabel.this.repaint();
                    timer.stop();
                } else {
                    CardLabel.this.repaint();
                    timerTarget -= 250;
                }
            }
        };

        timer.addActionListener(al);
        return timer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (slide) {
            //Move towards final location
            int endX = LOCATION.x;
            int endY = LOCATION.y;

            int dXPerAction = (int) ((endX - START_X) / (1 / 0.015));
            int dYPerAction = (int) ((endY - START_Y) / (1 / 0.015));

            Graphics2D g2d = (Graphics2D) g;
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY));
            g2d.scale(Main.getSizeFactor(), Main.getSizeFactor());
            g2d.drawImage(IMAGE, null, null);

            int xNow = getLocation().x;
            int yNow = getLocation().y;

            //Too far situations
            if (xNow > endX && (xNow + dXPerAction) < endX) {
                slide = false;
            }
            if (xNow < endX && (xNow + dXPerAction) > endX) {
                slide = false;
            }
            if (yNow > endY && (yNow + dYPerAction) < endY) {
                slide = false;
            }
            if (yNow < endY && (yNow + dYPerAction) > endY) {
                slide = false;
            }

            if (slide) {
                setLocation(xNow + dXPerAction, yNow + dYPerAction);
            }
        } else {
            //At final location so turn to final position
            AffineTransform at = new AffineTransform();

            at.translate(getWidth() / 2, getHeight() / 2);

            at.rotate(Math.toRadians(degrees));

            at.translate(-IMAGE.getWidth(null) / 2, -IMAGE.getHeight(null) / 2);

            Graphics2D g2d = (Graphics2D) g;
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY));

            g2d.scale(Main.getSizeFactor(), Main.getSizeFactor());

            g2d.drawImage(IMAGE, at, null);
        }
    }

}
