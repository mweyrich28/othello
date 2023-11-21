package de.lmu.bio.ifi;

import szte.mi.Move;

public class PlayerMove extends Move {
    private boolean playerOne;
    public PlayerMove(int x, int y, boolean player){
        super(x,y);
        this.playerOne = player;
    }
    public PlayerMove(int x, int y){
        super(x,y);
    }
    public boolean isPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(boolean playerOne) {
        this.playerOne = playerOne;
    }
}
