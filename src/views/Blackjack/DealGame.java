/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.blackjack;

import exception.shared.ResourceNotExistingException;
import object.blackjack.Card;
import object.blackjack.CardLabel;
import resources.java.blackjack.AllCards;
import object.blackjack.CardList;
import static resources.java.shared.ImageLabel.getImageIcon;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class DealGame extends javax.swing.JPanel {

    private final int PLAYER = 0, SPLIT = 1, DEALER = 2;
    private final Point FIRST_COORD = new Point(512, 553),//512,553 > 375, 426
            SECOND_COORD = new Point(692, 553), //692, 553 > 507, 426
            THIRD_COORD = new Point(602, 128), //602, 128 > 441, 99
            FOURTH_COORD = new Point(295, 149), //295, 149 > 209, 115
            FIFTH_COORD = new Point(857, 149), //857, 149 > 589, 106
            SIXTH_COORD = new Point(4, 250), //4, 250 > -3, 185
            SEVENTH_COORD = new Point(1112, 250);//1112, 250 > 843, 167
    private final Point[] COORDS = new Point[]{FIRST_COORD, SECOND_COORD,
        THIRD_COORD, FOURTH_COORD, FIFTH_COORD, SIXTH_COORD, SEVENTH_COORD};

    private int dealingTo;

    private CardList cards;
    private final CardList PLAYER_CARDS;
    private final CardList SPLIT_CARDS;
    private final CardList DEALER_CARDS;

    private boolean splitGame;
    private boolean forcedStop;
    private boolean splitForcedStop;

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

        splitGame = false;
        forcedStop = false;
        splitForcedStop = false;

        beginGame();
    }

    private void beginGame() {
        //Dealing the cards
        PLAYER_CARDS.add(getRandomCard());
        DEALER_CARDS.add(getRandomCard());
        PLAYER_CARDS.add(getRandomCard());
        DEALER_CARDS.add(getRandomCard());

        Card c1 = PLAYER_CARDS.get(0);
        Card c2 = PLAYER_CARDS.get(1);

        CardLabel cl1 = new CardLabel(c1.getIcon(), 0, COORDS[0], new Dimension(160, 232));
        CardLabel cl2 = new CardLabel(c2.getIcon(), 0, COORDS[1], new Dimension(160, 232));
        CardLabel cl3 = new CardLabel(c2.getIcon(), 0, COORDS[2], new Dimension(160, 232));
        CardLabel cl4 = new CardLabel(c1.getIcon(), -13.75, COORDS[3], new Dimension(212, 264));
        CardLabel cl5 = new CardLabel(c2.getIcon(), 13.75, COORDS[4], new Dimension(212, 264));
        CardLabel cl6 = new CardLabel(c1.getIcon(), -27.5, COORDS[5], new Dimension(250, 280));
        CardLabel cl7 = new CardLabel(c2.getIcon(), 27.5, COORDS[6], new Dimension(250, 280));

        layer.add(cl1);
        layer.add(cl2);
        layer.add(cl3);
        layer.add(cl4);
        layer.add(cl5);
        layer.add(cl6);
        layer.add(cl7);
        layer.moveToFront(cl1);
        layer.moveToFront(cl2);
        layer.moveToFront(cl3);
        layer.moveToFront(cl4);
        layer.moveToFront(cl5);
        layer.moveToFront(cl6);
        layer.moveToFront(cl7);

        //Check dealer has blackjack
        if (DEALER_CARDS.getValue() == 21) {
            multiplier = 0;
            endGame();
            return;
        }
        if (PLAYER_CARDS.getValue() == 21) {
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
            forcedStop = true;
            multiplier--;
            if (splitGame) {
                if (splitForcedStop) {
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
            forcedStop = true;
            if (multiplier == 0) {
                multiplier = 3;
            } else {
                multiplier += 2;
            }

            if (splitGame) {
                if (splitForcedStop) {
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
            splitForcedStop = true;
            multiplier--;
            if (forcedStop) {
                endGame();
                return;
            }
        }

        //Check if split has seven cards
        cardCount = SPLIT_CARDS.size();
        if (cardCount == 7) {
            splitForcedStop = true;
            if (multiplier == 0) {
                multiplier = 3;
            } else {
                multiplier += 2;
            }
            if (forcedStop) {
                endGame();
            }
        }

        //Check for split TODO
    }

    private void dealDealer() {
        int total;
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
            } else if (splitGame) {
                if (!forcedStop) {
                    multiplier++;
                }
                if (!splitForcedStop) {
                    multiplier++;
                }
            }
            endGame();
            return;
        }
        if (total >= 17) {
            if (!forcedStop) {
                if (total >= PLAYER_CARDS.getValue()) {
                    multiplier--;
                } else if (multiplier == 0) {
                    multiplier = 2;
                } else {
                    multiplier++;
                }
            }
            if (splitGame && !splitForcedStop) {
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

    private void endGame() {
        multiplier = multiplier < 0 ? 0 : multiplier;
        int payout = wager * multiplier;
    }

    /**
     * Get a random card from the deck and remove this card from the deck.
     *
     * @return The randomly picked card.
     */
    private Card getRandomCard() {
        Random r = new Random();
        int random = r.nextInt(cards.size());
        return cards.remove(random);
    }

    /**
     * Clear the cards and reinitialize the form.
     */
    private void clearGame() {
        PLAYER_CARDS.clear();
        SPLIT_CARDS.clear();
        DEALER_CARDS.clear();
        cards = AllCards.getShuffledCards();

        initComps();
    }

    /**
     * Convert the coordinates to fit the fullscreen, only needed when using
     * non-converted coordinates.
     */
    private void convertCoords() {
        int loc = 1;
        for (Point p : COORDS) {
            int x = (int) (p.x * 1.366);
            int y = (int) (p.y * 1.30);
            p.setLocation(x, y);
            System.out.println("Location " + loc + ": " + p);
            loc++;
        }
    }

    private void initComps() {
//        Only needed when using non-converted coordinates
//        convertCoords();

        background.setIcon(getImageIcon("backgroundBlackjack"));
        layer.removeAll();
        layer.add(background);
        layer.moveToFront(background);
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

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/backgroundBlackjack.png"))); // NOI18N

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
