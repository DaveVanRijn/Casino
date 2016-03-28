/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blackjack.Objects;

import static Casino.Main.convertSize;
import static Casino.Main.getImage;
import java.awt.Dimension;
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
public class Card {

    private final ImageIcon image;
    private JLabel label;
    private int value;
    private final String name;
    private final String suit;
    private final ImageIcon backImage;

    public Card(int value, String name, String suit, int location) {
        this.value = value;
        this.name = name;
        this.suit = suit;

        String imageName = name.toLowerCase() + "_of_" + suit.toLowerCase();
        image = new ImageIcon(getImage(imageName));
        backImage = new ImageIcon(getImage("cardBack"));

        if (location >= 0 && location < 7) {
            setParams(location);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public ImageIcon getBackIcon() {
        return backImage;
    }

    public ImageIcon getIcon() {
        return image;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " of " + suit;
    }

    private void setParams(int location) {
        final int[] degrees = new int[]{0, 0, -30, -15, 0, 15, 30};
        final Point[] locations = new Point[]{};

        label = new CardLabel(image, degrees[location], locations[location]);
    }

    private ImageIcon turn(ImageIcon image, int degrees, Dimension size) {
        if (degrees == 0) {
            return image;
        }
        BufferedImage buff = new BufferedImage((int) size.getWidth(),
                (int) size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buff.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        AffineTransform trans = AffineTransform.getRotateInstance(Math.toRadians(degrees), image.getIconWidth() / 2, image.getIconHeight() / 2);

        g2d.drawImage(image.getImage(), trans, null);

        return new ImageIcon(buff);
    }
}
