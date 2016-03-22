/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blackjack.Objects;

import static Blackjack.PlaceBet.getImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Card extends JLabel {

    private final ImageIcon image;
    private int value;
    private final String name;
    private final String suit;
    private final ImageIcon backImage;

    public Card(int value, String name, String suit) {
        this.value = value;
        this.name = name;
        this.suit = suit;

        String imageName = name.toLowerCase() + "_of_" + suit.toLowerCase();
        this.image = new ImageIcon(getImage(imageName));
        backImage = new ImageIcon(getImage("cardBack"));
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

    @Override
    public ImageIcon getIcon() {
        return image;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " of " + suit;
    }
}
