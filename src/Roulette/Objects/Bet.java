/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Roulette.Objects;

import javax.swing.JLabel;

/**
 *
 * @author Davey
 */
public class Bet {
    
    private final int multiplier;
    private final int[] numbers;
    private final double wager;
    private final JLabel label;
    
    public Bet(int multiplier, int[] numbers, double wager, JLabel label){
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

    public double getWager() {
        return wager;
    }
    
    public JLabel getLabel(){
        return label;
    }
    
}
