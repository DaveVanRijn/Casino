/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blackjack.Objects;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class PlayerGame {
    
    private final CardList cards;
    private long wager;
    private int multiplier;
    private final boolean blackjack;
    
    public PlayerGame(Card first, Card second){
        cards = new CardList();
        cards.add(first);
        cards.add(second);
        blackjack = getValue() == 21;
    }
    
    public final int getValue(){
        return cards.getValue();
    }
    
    public long getWager(){
        return wager;
    }
    
    public boolean tooMuch(){
        return getValue() > 21;
    }
    
    public boolean isBlackjack(){
        return blackjack;
    }
    
    public boolean addCard(Card c){
        cards.add(c);
        return getValue() <= 21 && cards.size() == 7;
    }
}
