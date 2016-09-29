/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object.Blackjack;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class SplitGame {
    
    public static final int FIRST_SPLIT = 0;
    public static final int SECOND_SPLIT = 1;
    
    private final CardList[] SPLITS;
    
    private CardList firstSplit;
    private CardList secondSplit;
    private int wager;
    private int multiplier;
    
    public SplitGame(Card first, Card second, int wager){
        firstSplit.add(first);
        secondSplit.add(second);
        SPLITS = new CardList[2];
        SPLITS[FIRST_SPLIT] = firstSplit;
        SPLITS[SECOND_SPLIT] = secondSplit;
        
        this.wager = wager;
    }
    
    public boolean hasSevenCards(int split){
        return SPLITS[split].getValue() <= 21 && SPLITS[split].size() == 7;
    }
    
    public int[] getValues(){
        return new int[]{firstSplit.getValue(), secondSplit.getValue()};
    }
    
    public int getValue(int split){
        return SPLITS[split].getValue();
    }
    
    /**
     * 
     * @param card The card that is added
     * @param split The split list to add the card to
     * @return false if no event occurs (too much points or 7 cards), true if an
     * event does occur
     */
    public boolean addCard(Card card, int split){
        SPLITS[split].add(card);
        return SPLITS[split].getValue() <= 21 && SPLITS[split].size() < 7;
    }
    
    public int getPayout(){
        return wager * multiplier;
    }
    
    public void clear(){
        firstSplit.clear();
        secondSplit.clear();
        multiplier = 0;
    }
}
