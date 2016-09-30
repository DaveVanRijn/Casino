/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources.java.shared;

import java.awt.Cursor;
import views.shared.Main;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Dave
 */
public class ImageLabel {

    private static final String SUFFIX = ".png";

    public static ImageIcon getImageIcon(String imageId) {
        URL url = ImageLabel.class.getResource("/resources/img/" + imageId + SUFFIX);
        if (url != null) {
            return new ImageIcon(getImage(imageId));
        }
        return null;
    }

    public static JLabel getLabel(String imageId) {
        ImageIcon image = getImageIcon(imageId);
        JLabel lbl = new JLabel(image);
        lbl.setSize(image.getIconWidth(), image.getIconHeight());
        return lbl;
    }

    public static JButton getButton(String imageId) {
        ImageIcon image = getImageIcon(imageId);
        JButton btn = new JButton(image);
        btn.setPressedIcon(getImageIcon(imageId + "Pressed"));
        btn.setSize(image.getIconWidth(), image.getIconHeight());
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private static BufferedImage getImage(String name) {
        Image image = new ImageIcon(Main.class.getResource("/resources/img/" + name + ".png")).getImage();
        int width;
        int height;

        if (name.equals("backgroundBlackjack")) {
            Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            width = (int) screenSize.getWidth();
            height = (int) screenSize.getHeight();
        } else {
            width = (int) (image.getWidth(null) * Main.getSizeFactor());
            height = (int) (image.getHeight(null) * Main.getSizeFactor());
        }

        BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buff.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        boolean done = g2d.drawImage(image, 0, 0, width, height, null);
        return buff;
    }
}
