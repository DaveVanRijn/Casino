/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blackjack;

import Blackjack.Objects.Card;
import Blackjack.Objects.CardList;
import static Casino.Main.getImage;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class DealGame extends javax.swing.JPanel {

    private JLabel lblPlayer;
    private JLabel lblDealer;
    private JLabel lblSplit;
    private JButton btnHit;
    private JButton btnSplit;
    private JButton btnDouble;
    private JButton btnStop;
    
    private CardList pickedCards;
    private CardList cards;
    private CardList playerCards;
    private CardList splitCards;
    private CardList dealerCards;
    
    private boolean sevenCards;
    private boolean splitSevenCards;
    private boolean blackjack;
    private boolean dealerBlackjack;
    private boolean tooMuch;
    private boolean splitTooMuch;
    private boolean dealerTooMuch;
    
    private double wager;
    /**
     * Creates new form DealGame
     */
    public DealGame() {
        initComponents();
        initComps();
        //initCards();
        //Update van computer
        
        pickedCards = new CardList();
        playerCards = new CardList();
        splitCards = new CardList();
        dealerCards = new CardList();
    }
    
    private void initComps(){
        background.setIcon(new ImageIcon(getImage("backgroundBlackjack")));
        layer.removeAll();
        layer.add(background);
        layer.moveToFront(background);
    }
    
    private void initCards(){
        cards = new CardList();
        int counter = 2;
        int cardAmount = 52;
        String[] names = new String[]{"Jack", "Queen", "King", "Ace"};
        String[] suits = new String[]{"Hearts", "Diamonds", "Spades", "Clubs"};
        for(int i = 0; i < cardAmount; i++){
            String name;
            String suit = suits[i / 13];
            int value;
            if(counter < 11){
                name = Integer.toString(counter);
                value = counter;
            } else if (counter < 14){
                name = names[counter - 11];
                value = 10;
            } else {
                name = names[3];
                value = 11;
            }
            cards.add(new Card(value, name, suit));
            if(++counter == 15){
                counter= 2;
            }
        }
        Collections.shuffle(cards);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layer = new javax.swing.JLayeredPane();
        background = new javax.swing.JLabel();

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/backgroundBlackjack.png"))); // NOI18N

        javax.swing.GroupLayout layerLayout = new javax.swing.GroupLayout(layer);
        layer.setLayout(layerLayout);
        layerLayout.setHorizontalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerLayout.createSequentialGroup()
                .addComponent(background)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layerLayout.setVerticalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layer.setLayer(background, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layer)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layer)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLayeredPane layer;
    // End of variables declaration//GEN-END:variables
}
