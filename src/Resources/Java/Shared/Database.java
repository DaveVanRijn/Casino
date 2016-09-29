/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources.Java.Shared;

import Object.Shared.Player;
import java.io.IOException;
import java.util.List;
import nexus.common.utils.DataUtil;

/**
 *
 * @author Dave
 */
public class Database {

    public static final int PLAYERS = 0, CURRENT_PLAYER = 1, REM_CRED = 2;

    private final DataUtil DATA_UTIL;

    public Database() throws IOException {
        String dir = "Casino", file = ".cdb";
        DATA_UTIL = new DataUtil(dir, file);
    }

    public void putCurrentPlayer(Player p) throws IOException {
        put(CURRENT_PLAYER, p);
    }

    public void putPlayers(List<Player> lp) throws IOException {
        put(PLAYERS, lp);
    }
    
    public void putRemCred(Player p) throws IOException{
        putRemCred(new String[]{p.getUsername(), p.getPassword()});
    }

    public Player getCurrentPlayer() {
        return (Player) get(CURRENT_PLAYER);
    }

    public List<Player> getPlayers() {
        return (List<Player>) get(PLAYERS);
    }
    
    public String[] getRemCred(){
        return (String[]) get(REM_CRED);
    }

    public boolean addPlayer(Player p) throws IOException {
        List<Player> players = getPlayers();
        boolean succes = players.add(p);
        putPlayers(players);
        
        return succes;
    }

    public Player editPlayer(Player p) throws IOException {
        List<Player> players = getPlayers();

        for (Player pl : players) {
            if (pl.getUsername().equals(p.getUsername())) {
                pl.setMoney(p.getMoney());
                pl.setPassword(p.getPassword());
                
                putPlayers(players);
                return pl;
            }
        }
        
        return null;
    }

    public boolean removePlayer(Player p) throws IOException {
        List<Player> players = getPlayers();
        boolean succes = false;

        for (Player pl : players) {
            if (pl.getUsername().equals(p.getUsername())) {
                succes = players.remove(pl);
                putPlayers(players);
            }
        }
        
        return succes;
    }
    
    public void removeRemCred() throws IOException{
        DATA_UTIL.remove(REM_CRED);
    }
    
    public boolean isCurrentPlayer(Player p){
        return getCurrentPlayer().getUsername().equals(p.getUsername());
    }
    
    private void putRemCred(String[] cred) throws IOException{
        put(REM_CRED, cred);
    }

    private void put(int id, Object o) throws IOException {
        DATA_UTIL.put(id, o);
    }

    private Object get(int id) {
        return DATA_UTIL.get(id);
    }

}
