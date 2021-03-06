/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object.Roulette;

import javax.swing.JLabel;

/**
 *
 * @author Davey
 */
public class Bet {
    
    private final int multiplier;
    private final int[] numbers;
    private final int wager;
    private final JLabel label;
    
    public Bet(int multiplier, int[] numbers, int wager, JLabel label){
        this.multiplier = multiplier;
        this.numbers = numbers;
        this.wager = wager;
        this.label = label;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public int getWager() {
        return wager;
    }
    
    public JLabel getLabel(){
        return label;
    }
    
}
