/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.Blackjack;

import Object.Blackjack.Card;
import Resources.Java.Blackjack.AllCards;
import Object.Blackjack.CardList;
import static Views.Shared.Main.getImage;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class DealGame extends javax.swing.JPanel {

    private final int PLAYER = 0, SPLIT = 1, DEALER = 2;
    
    private int dealingTo;

    private CardList cards;
    private final CardList PLAYER_CARDS;
    private final CardList SPLIT_CARDS;
    private final CardList DEALER_CARDS;

    private boolean split;
    private boolean stopped;
    private boolean splitStopped;

    private int wager;
    private int multiplier;

    /**
     * Creates new form DealGame
     */
    public DealGame(int wager) {
        initComponents();
        initComps();

        this.wager = wager;
        multiplier = 0;

        cards = AllCards.getShuffledCards();

        PLAYER_CARDS = new CardList();
        SPLIT_CARDS = new CardList();
        DEALER_CARDS = new CardList();

        split = false;
        stopped = false;
        splitStopped = false;
        
        beginGame();
    }
    
    private void beginGame(){
        //Dealing the cards
        PLAYER_CARDS.add(getRandomCard());
        DEALER_CARDS.add(getRandomCard());
        PLAYER_CARDS.add(getRandomCard());
        DEALER_CARDS.add(getRandomCard());
        
        //Check dealer has blackjack
        if(DEALER_CARDS.getValue() == 21){
            multiplier = 0;
            endGame();
            return;
        }
        if(PLAYER_CARDS.getValue() == 21){
            multiplier = 3;
            endGame();
            return;
        }
        
        // No Blackjack, begin game
        dealingTo = PLAYER;
        
        //Check if Player can play a split game
        boolean hasSplit = PLAYER_CARDS.get(0).getFace().equals(PLAYER_CARDS.get(1).getFace());
        
        //Activate buttons
        //btnSplit.setEnabled(hasSplit);
        //btnHit.setEnabled(true);
        //btnDouble.setEnabled(true);
        //btnStop.setEnabled(true);
    }

    private void deal() {
        switch (dealingTo) {
            case PLAYER:
                dealPlayer();
                break;
            case SPLIT:
                dealSplit();
                break;
            case DEALER:
                dealDealer();
                break;
            default:
                System.out.println("Invalid person number. 0 = PLAYER, 1 = SPLIT, 2 = DEALER");
        }
    }

    private void dealPlayer() {
        int total, cardCount;
        PLAYER_CARDS.add(getRandomCard());

        //Check if player has too much points and can flip aces
        total = PLAYER_CARDS.getValue();
        while (total > 21 && PLAYER_CARDS.containsAce()) {
            PLAYER_CARDS.getAce().setElevenToOne();
            total = PLAYER_CARDS.getValue();
        }
        if (total > 21) {
            stopped = true;
            multiplier--;
            if (split) {
                if (splitStopped) {
                    endGame();
                    return;
                }
            } else {
                endGame();
                return;
            }
        }
        //Check if player has seven cards
        cardCount = PLAYER_CARDS.size();
        if (cardCount == 7) {
            stopped = true;
            if (multiplier == 0) {
                multiplier = 3;
            } else {
                multiplier += 2;
            }

            if (split) {
                if (splitStopped) {
                    endGame();
                }
            } else {
                endGame();
            }
        }
    }

    private void dealSplit() {
        int total, cardCount;
        SPLIT_CARDS.add(getRandomCard());

        //Check if split has too much points and can flip aces
        total = SPLIT_CARDS.getValue();
        while (total > 21 && SPLIT_CARDS.containsAce()) {
            SPLIT_CARDS.getAce().setElevenToOne();
            total = SPLIT_CARDS.getValue();
        }
        if (total > 21) {
            splitStopped = true;
            multiplier--;
            if (stopped) {
                endGame();
                return;
            }
        }

        //Check if split has seven cards
        cardCount = SPLIT_CARDS.size();
        if (cardCount == 7) {
            splitStopped = true;
            if (multiplier == 0) {
                multiplier = 3;
            } else {
                multiplier += 2;
            }
            if (stopped) {
                endGame();
            }
        }

        //Check for split TODO
    }

    private void dealDealer() {
        int total, cardCount;
        DEALER_CARDS.add(getRandomCard());
        total = DEALER_CARDS.getValue();

        //Check if dealer has too much points
        while (total > 21 && DEALER_CARDS.containsAce()) {
            DEALER_CARDS.getAce().setElevenToOne();
            total = DEALER_CARDS.getValue();
        }
        if (total > 21) {
            if (multiplier == 0) {
                multiplier = 2;
            } else if (split) {
                if (!stopped) {
                    multiplier++;
                }
                if (!splitStopped) {
                    multiplier++;
                }
            }
            endGame();
            return;
        }
        if (total >= 17) {
            if (!stopped) {
                if (total >= PLAYER_CARDS.getValue()) {
                    multiplier--;
                } else if (multiplier == 0) {
                    multiplier = 2;
                } else {
                    multiplier++;
                }
            }
            if (split && !splitStopped) {
                if (total >= SPLIT_CARDS.getValue()) {
                    multiplier--;
                } else if (multiplier == 0) {
                    multiplier = 2;
                } else {
                    multiplier++;
                }
            }
            endGame();
        }
    }

    private Card getRandomCard() {
        Random r = new Random();
        int random = r.nextInt(cards.size());
        return cards.remove(random);
    }

    private void endGame() {
        multiplier = multiplier < 0 ? 0 : multiplier;
        int payout = wager * multiplier;
    }

    private void doubleBet() {
        wager *= 2;
    }

    private void initComps() {
        background.setIcon(new ImageIcon(getImage("backgroundBlackjack")));
        layer.removeAll();
        layer.add(background);
        layer.moveToFront(background);
    }

    private void clearGame() {
        PLAYER_CARDS.clear();
        SPLIT_CARDS.clear();
        DEALER_CARDS.clear();
        cards = AllCards.getShuffledCards();
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

        layer.setLayer(background, javax.swing.JLayeredPane.DEFAULT_LAYER);

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

    // Swing components
    private JLabel lblPlayer;
    private JLabel lblDealer;
    private JLabel lblSplit;
    private JButton btnHit;
    private JButton btnSplit;
    private JButton btnDouble;
    private JButton btnStop;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLayeredPane layer;
    // End of variables declaration//GEN-END:variables
}
