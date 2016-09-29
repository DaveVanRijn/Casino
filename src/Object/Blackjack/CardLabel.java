/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object.Blackjack;

import Views.Shared.Main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class CardLabel extends JLabel {

    private final BufferedImage image;
    private final int degrees;

    public CardLabel(ImageIcon icon, int degrees, Point location) {
        image = toBuffered(icon);
        this.degrees = degrees;
        setLocation(location);
    }

    private BufferedImage toBuffered(ImageIcon icon) {
        BufferedImage buff = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = buff.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return buff;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        AffineTransform at = new AffineTransform();

        at.translate(getWidth() / 2, getHeight() / 2);

        at.rotate(Math.toRadians(degrees));

        at.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);

        Graphics2D g2d = (Graphics2D) g;
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        
        g2d.scale(Main.getSizeFactor(), Main.getSizeFactor());

        g2d.drawImage(image, at, null);
    }

}
