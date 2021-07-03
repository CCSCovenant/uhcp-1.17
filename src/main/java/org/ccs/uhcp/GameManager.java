package org.ccs.uhcp;

public class GameManager {
    static GameManager GM = new GameManager();
    int Gametime;
    boolean gameStates;

    public GameManager(){

    }

    public static GameManager getInstance() {
        if (GM==null){
            GM = new GameManager();
        }
        return GM;
    }
    public void startGames(){
        
    }

}
