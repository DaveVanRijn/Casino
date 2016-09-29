/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources.Java.Blackjack;

import Object.Blackjack.Card;
import Object.Blackjack.CardList;

/**
 *
 * @author Dave
 */
public class AllCards {

    private static final String[] SUITS = new String[]{"Hearts", "Diamonds",
        "Clubs", "Spades"};
    private static final String[] NAME_FACES = new String[]{"Jack", "Queen", "King"};
    private static final String ACE = "Ace";

    public static CardList getShuffledCards() {
        CardList cards = getCards();

        cards.shuffle();
        return cards;
    }

    public static CardList getCards() {
        CardList cards = new CardList();

        for (String suit : SUITS) {
            int value = 2;
            for (; value < 11; value++) {
                String face = Integer.toString(value);
                cards.add(new Card(value, face, suit));
            }
            for (String face : NAME_FACES) {
                value = 10;
                cards.add(new Card(value, face, suit));
            }
            value = 11;
            cards.add(new Card(value, ACE, suit));
        }
        
        return cards;
    }
}
