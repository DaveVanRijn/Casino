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
    
    private CardList firstSplit;
    private CardList secondSplit;
    private final CardList[] splits;
    private long wager;
    private int multiplier;
    public static final int FIRST_SPLIT = 0;
    public static final int SECOND_SPLIT = 1;
    
    public SplitGame(Card first, Card second, long wager){
        firstSplit.add(first);
        secondSplit.add(second);
        splits = new CardList[2];
        splits[FIRST_SPLIT] = firstSplit;
        splits[SECOND_SPLIT] = secondSplit;
    }
    
    public boolean hasSevenCards(int split){
        return splits[split].getValue() <= 21 && splits[split].size() == 7;
    }
    
    public int[] getValues(){
        return new int[]{firstSplit.getValue(), secondSplit.getValue()};
    }
    
    public int getValue(int split){
        return splits[split].getValue();
    }
    
    /**
     * 
     * @param card The card that is added
     * @param split The split list to add the card to
     * @return false if no event occurs (too much points or 7 cards), true if an
     * event does occur
     */
    public boolean addCard(Card card, int split){
        splits[split].add(card);
        return splits[split].getValue() <= 21 && splits[split].size() < 7;
    }
    
    public long getPayout(){
        return wager * multiplier;
    }
    
    public void clear(){
        firstSplit.clear();
        secondSplit.clear();
        multiplier = 0;
    }
}
