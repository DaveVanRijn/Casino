/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object.Shared;

import Resources.Java.Shared.Database;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS-202
 */
public class Player implements Serializable {

    private final String USERNAME;
    
    private String password;
    private int money;
    private int rouletteWinCounter;
    private int roulettePlayedCounter;
    private final DecimalFormat DECI_FORM = new DecimalFormat("0.00");

    /**
     * Construct a Player
     *
     * @param username The USERNAME of the player
     * @param password The password of the player
     * @param money The amount of ingame money of the player
     */
    private Player(String username, String password, int money) {
        this.USERNAME = username;
        this.password = password;
        this.money = money;
        rouletteWinCounter = 0;
        roulettePlayedCounter = 0;
    }

    /**
     * Get the USERNAME of the player
     *
     * @return The USERNAME of the player
     */
    public String getUsername() {
        return USERNAME;
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
        try {
            this.password = password;

            Database db = new Database();
            if (db.isCurrentPlayer(this)) {
                db.putCurrentPlayer(this);
            }
            db.editPlayer(this);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the ingame money of the player
     *
     * @return The ingame money of the player
     */
    public int getMoney() {
        return money;
    }

    /**
     * Set the ingame money of the player
     *
     * @param money The new ingame money
     */
    public void setMoney(int money) {
        try {
            this.money = money;

            Database db = new Database();
            if (db.isCurrentPlayer(this)) {
                db.putCurrentPlayer(this);
            }
            db.editPlayer(this);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getRoulettePlayed() {
        return roulettePlayedCounter;
    }

    public void addRoulettePlayed() {
        try {
            roulettePlayedCounter++;

            Database db = new Database();
            if (db.isCurrentPlayer(this)) {
                db.putCurrentPlayer(this);
            }
            db.editPlayer(this);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getRouletteWon() {
        return rouletteWinCounter;
    }

    public void addRouletteWon() {
        try {
            rouletteWinCounter++;
            
            Database db = new Database();
            if (db.isCurrentPlayer(this)) {
                db.putCurrentPlayer(this);
            }
            db.editPlayer(this);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRoulettePercentageWon() {
        if (rouletteWinCounter == 0 || roulettePlayedCounter == 0) {
            return DECI_FORM.format(0);
        } else {
            return DECI_FORM.format(((double) rouletteWinCounter / (double) roulettePlayedCounter) * 100);
        }
    }

    /**
     * Add a new player to the game
     *
     * @param username The USERNAME of the new Player
     * @param password The password of the new player
     * @param money The amount of ingame money of the new player
     * @return The newly constructed player
     */
    public static Player newPlayer(String username, String password, int money) {
        return new Player(username, password, money);
    }
}
