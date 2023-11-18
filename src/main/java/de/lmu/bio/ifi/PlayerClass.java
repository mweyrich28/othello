package de.lmu.bio.ifi;

import szte.mi.Move;
import szte.mi.Player;

import java.util.ArrayList;
import java.util.Random;

public class PlayerClass implements Player {
        private long timeLeft;
        private Random rnd;
        private OthelloGame othelloGame;
        private int order;
        private int points;

        public long getTimeLeft() {
            return timeLeft;
        }

        public void init(int order, long t, java.util.Random rnd){
            this.othelloGame = new OthelloGame(order);
            this.timeLeft = t;
            this.rnd = rnd;
            this.order = order;
        }


    public void incrementPoints(int points){
            this.points+=points;
        }
    public Move nextMove(Move prevMove, long tOpponent, long t){
        if (prevMove != null){ // append move to game
            othelloGame.makeMove(order==1, prevMove.x, prevMove.y);
        }

        // react to a previous move
        ArrayList<Move> possibleMoves = (ArrayList<Move>) othelloGame.getPossibleMoves(order==0);

        return possibleMoves.get(0);
    }
}
