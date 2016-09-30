/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object.roulette;

/**
 *
 * @author Davey
 */
public class Clickable {
    
    private final int maxX;
    private final int minX;
    private final int maxY;
    private final int minY;
    private final int[] numbers;
    private final String name;
    private final int multiplier;
    
    public Clickable(String name, int minX, int maxX, int minY, int maxY, 
            int[] numbers, int multiplier){
        this.maxX = maxX;
        this.minX = minX;
        this.maxY = maxY;
        this.minY = minY;
        this.numbers = numbers;
        this.name = name;
        this.multiplier = multiplier;
    }
    
    public int getMaxX(){
        return maxX;
    }
    
    public int getMinX(){
        return minX;
    }
    
    public int getMaxY(){
        return maxY;
    }
    
    public int getMinY(){
        return minY;
    }
    
    public int[] getNumbers(){
        return numbers;
    }
    
    public String getName(){
        return name;
    }

    public int getMultiplier() {
        return multiplier;
    }
    
    
}
