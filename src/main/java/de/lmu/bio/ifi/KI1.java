package de.lmu.bio.ifi;

import java.util.ArrayList;
import java.util.Random;
import szte.mi.Move;
import szte.mi.Player;

public class KI1 implements Player {
    private long timeLeft;
    private Random rnd;
    private OthelloGame othelloGame;
    private int order;

    public long getTimeLeft() {
        return timeLeft;
    }

    public void init(int order, long t, java.util.Random rnd){
        this.othelloGame = new OthelloGame(order);
        this.timeLeft = t;
        this.rnd = rnd;
        this.order = order;
    }
    public Move nextMove(Move prevMove, long tOpponent, long t){
        if (prevMove != null){ // append move to game
            othelloGame.makeMove(order==1, prevMove.x, prevMove.y);
        }

        // react to a previous move
        ArrayList<Move> possibleMoves = (ArrayList<Move>) othelloGame.getPossibleMoves(order==0);
        int randomMove = this.rnd.nextInt(possibleMoves.size()-1);
        Move nextMove = possibleMoves.get(randomMove);
        othelloGame.makeMove(order==0, nextMove.x, nextMove.y);
        return nextMove;
    }
}
