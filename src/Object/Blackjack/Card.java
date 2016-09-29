/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object.Blackjack;

import static Views.Shared.Main.getImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Card extends JLabel {

    private final ImageIcon image;
    private int value; //Value of the card, J,K,Q are worth 10, A 11 or 1
    private final String face; //The 'number' of the card
    private final String suit;

    /**
     * Initializes card and sets the front image as icon.
     * @param value
     * @param face
     * @param suit 
     */
    public Card(int value, String face, String suit) {
        this(value, face, suit, false);
    }

    /**
     *
     * @param value
     * @param face
     * @param suit
     * @param back Determines if the icon of the card must be the back of the
     * card.
     */
    public Card(int value, String face, String suit, boolean back) {
        this.value = value;
        this.face = face;
        this.suit = suit;

        String imageName = face.toLowerCase() + "_of_" + suit.toLowerCase();
        this.image = new ImageIcon(getImage(imageName));

        if (back) {
            setBack();
        } else {
            setFront();
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setOnetoEleven() {
        if (face.equals("Ace") && value == 1) {
            setValue(11);
        }
    }
    
    public void setElevenToOne(){
        if(face.equals("Ace") && value == 11){
            setValue(1);
        }
    }

    public String getSuit() {
        return suit;
    }

    public ImageIcon getBackIcon() {
        return new ImageIcon(getImage("cardBack"));
    }

    @Override
    public ImageIcon getIcon() {
        return image;
    }

    public String getFace() {
        return face;
    }

    public void setFront() {
        setImage(true);
    }

    public void setBack() {
        setImage(false);
    }

    @Override
    public String toString() {
        return face + " of " + suit;
    }

    private void setImage(boolean front) {
        setIcon(front ? getIcon() : getBackIcon());
    }
}
