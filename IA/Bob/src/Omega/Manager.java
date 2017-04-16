package Omega;

import java.util.HashMap;

public class Manager {
    private HashMap<String, Player> map;

    private static Manager ourInstance = new Manager();

    public static Manager getInstance() {
        return ourInstance;
    }

    private Manager() {
        map = new HashMap<>();
    }

    public void registerPlayer(Player p){
        map.put(p.getName(), p);
    }

    public Player getPlayer(String s){
        return map.get(s);
    }

    public void removePlayer(Player p){
        map.remove(p);
    }
}
