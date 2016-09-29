/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object.Shared;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS-202
 */
public class Player implements Serializable{
    private String username;
    private String password;
    private long money;
    private static Player currentPlayer = null;
    private int rouletteWinCounter;
    private int roulettePlayedCounter;
    private final DecimalFormat deciForm = new DecimalFormat("0.00");
    
    /**
     * Construct a Player
     * 
     * @param username The username of the player
     * @param password The password of the player
     * @param money The amount of ingame money of the player
     */
    private Player(String username, String password, long money){
        this.username = username;
        this.password = password;
        this.money = money;
        rouletteWinCounter = 0;
        roulettePlayedCounter = 0;
    }

    /**
     * Get the username of the player
     * 
     * @return The username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the player
     * @param username The new username of the player
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password of the player
     * 
     * @return The password of the player
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the player
     * 
     * @param password The new password of the player 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the ingame money of the player
     * 
     * @return The ingame money of the player 
     */
    public long getMoney() {
        return money;
    }

    /**
     * Set the ingame money of the player
     * 
     * @param money The new ingame money
     */
    public void setMoney(long money) {
        this.money = money;
    }
    
    public int getRoulettePlayed(){
        return roulettePlayedCounter;
    }
    
    public void addRoulettePlayed(){
        roulettePlayedCounter++;
    }
    
    public int getRouletteWon(){
        return rouletteWinCounter;
    }
    
    public void addRouletteWon(){
        rouletteWinCounter++;
    }
    
    public String getRoulettePercentageWon(){
        if(rouletteWinCounter == 0 || roulettePlayedCounter == 0){
            return deciForm.format(0);
        } else {
            return deciForm.format(((double)rouletteWinCounter / (double)roulettePlayedCounter) * 100);
        }
    }
    
    /**
     * Set currentPlayer to a new instance of Player
     * 
     * @param player The current player
     */
    public static void setCurrentPlayer(Player player){
        currentPlayer = player;
    }
    
    /**
     * Get the current player
     * 
     * @return The current player of the game
     */
    public static Player getCurrentPlayer(){
        return currentPlayer;
    }
    
    /**
     * Nullify the current player
     */
    public static void deleteCurrentPlayer(){
        currentPlayer = null;
    }
    
    /**
     * Get whether or not someone is logged in
     * 
     * @return  True if someone is logged in, false if no one is logged in
     */
    public static boolean getLoggedIn(){
        return currentPlayer != null;
    }
    
    /**
     * Add a new player to the game
     * 
     * @param username The username of the new Player
     * @param password The password of the new player
     * @param money The amount of ingame money of the new player
     * @return The newly constructed player
     */
    public static Player newPlayer(String username, String password, long money){
        return new Player(username, password, money);
    }
}
