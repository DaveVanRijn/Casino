/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Casino;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Guest {

    private final String name;
    private long money;

    public Guest(String name, long money) {
        this.name = name;
        this.money = money;
    }

    public boolean translateMoney(long diff) {
        money += diff;
        return money > 0;
    }

    public long getMoney() {
        if (money > 0) {
            return money;
        }
        return 0;
    }
    
    /**
     * Get the name of the guest.
     * @return Name of the guest.
     * @deprecated Replaced by the {@link toString()} method.
     */
    @Deprecated
    public String getName(){
        return name;
    }
    
    /**
     * Get the name of the guest.
     * @return The name of the guest.
     */
    @Override
    public String toString(){
        return name;
    }
}
