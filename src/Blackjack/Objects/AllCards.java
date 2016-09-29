/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blackjack.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Dave
 */
public class AllCards {

    private static final String[] suits = new String[]{"Hearts", "Diamonds",
        "Clubs", "Spades"};
    private static final String[] nameFaces = new String[]{"Jack", "Queen", "King"};
    private static final String ACE = "Ace";

    public static List<Card> getShuffledCards() {
        List<Card> cards = getCards();

        Collections.shuffle(cards);
        return cards;
    }

    public static List<Card> getCards() {
        List<Card> cards = new ArrayList<>();

        for (String suit : suits) {
            int value = 2;
            for (; value < 11; value++) {
                String face = Integer.toString(value);
                cards.add(new Card(value, face, suit));
            }
            for (String face : nameFaces) {
                value = 10;
                cards.add(new Card(value, face, suit));
            }
            value = 11;
            cards.add(new Card(value, ACE, suit));
        }
        
        return cards;
    }
}
