package szte.mi;

import java.util.ArrayList;
import java.util.Random;

import de.lmu.bio.ifi.OthelloGame;
import de.lmu.bio.ifi.PlayerMove;

public class KI_Random implements Player {
    private long timeLeft;
    private Random rnd;
    private OthelloGame othelloGame;
    private int order;
    private int points;

    public long getTimeLeft() {
        return timeLeft;
    }

    public OthelloGame getOthelloGame() {
        return othelloGame;
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
        if (prevMove != null){ // append move to local game
            othelloGame.makeMove(othelloGame.getCurrPlayer() % 2 != 0, prevMove.x, prevMove.y);
        }
        if(this.order != othelloGame.getCurrPlayer()-1) {
           return prevMove;
        }


        // react to a previous move
        ArrayList<Move> possibleMoves = (ArrayList<Move>) othelloGame.getPossibleMoves(othelloGame.getCurrPlayer() % 2 != 0);
        int randomMove;
        if(possibleMoves != null && possibleMoves.size() >= 1) {
            randomMove = this.rnd.nextInt(possibleMoves.size());
            Move nextMove = possibleMoves.get(randomMove);
            othelloGame.makeMove(order==0, nextMove.x, nextMove.y);
            return nextMove;
        } else {
            return prevMove;
        }
    }
    public OthelloGame copyGame(OthelloGame game) {

        OthelloGame gameCopy = new OthelloGame();
        // gameCopy.getMoveHistroy().clear();
        boolean playerSwitch = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int currField = game.getBoard()[j][i];
                if(currField != 0){
                    gameCopy.getBoard()[j][i] = currField;
                    gameCopy.getMoveHistroy().add(new PlayerMove(j, i, playerSwitch));
                    playerSwitch = !playerSwitch;
                }
            }
        }
        gameCopy.setCurrOpponent(game.getCurrOpponent());
        gameCopy.setCurrPlayer(game.getCurrPlayer());
        gameCopy.setStatus(game.gameStatus());


        return gameCopy;
    }
    // for testing
    public void appendGameState(OthelloGame game){
        this.othelloGame = copyGame(game);
    }
}
