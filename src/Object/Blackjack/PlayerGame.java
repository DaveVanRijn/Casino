/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object.blackjack;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class PlayerGame {
    
    private final CardList cards;
    private int wager;
    private int multiplier;
    private boolean blackjack;
    
    public PlayerGame(Card first, Card second, int wager){
        cards = new CardList();
        cards.add(first);
        cards.add(second);
        this.wager = wager;
        blackjack = getValue() == 21;
    }
    
    public final int getValue(){
        return cards.getValue();
    }
    
    public boolean hasSevenCards(){
        return getValue() <= 21 && cards.size() == 7;
    }
    
    public boolean isBlackjack(){
        return blackjack;
    }
    
    /**
     * 
     * @param c The card that is added
     * @return false if no event occurs (too much points or 7 cards), true if an
     * event does occur
     */
    public boolean addCard(Card c){
        cards.add(c);
        return getValue() <= 21 && cards.size() < 7;
    }
    
    public Card removeLast(){
        return cards.remove(cards.size() - 1);
    }
    
    public Card removeCard(Card card){
        return cards.remove(cards.indexOf(card));
    }
    
    public int getPayout(){
        return wager * multiplier;
    }
    
    public void clear(){
        cards.clear();
        blackjack = false;
        multiplier = 0;
    }
}
