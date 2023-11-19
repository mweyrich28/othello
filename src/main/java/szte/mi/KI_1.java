package szte.mi;

import de.lmu.bio.ifi.GameStatus;
import de.lmu.bio.ifi.OthelloGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.atomic.DoubleAccumulator;

public class KI_1 implements Player{
    private long timeLeft;
    private Random rnd;
    private OthelloGame othelloGame;
    private int order;
    private int points;
    private final double[][] scoringMatrix = {
            {1.00, -0.25, 0.10, 0.05, 0.05, 0.10, -0.25, 1.00},
            {-0.25, -0.25, 0.01, 0.01, 0.01, 0.01, -0.25, -0.25},
            {0.10, 0.01, 0.05, 0.02, 0.02, 0.05, 0.01, 0.1},
            {0.05, 0.01, 0.02, 0.01, 0.01, 0.02, 0.01, 0.0},
            {0.05, 0.01, 0.02, 0.01, 0.01, 0.02, 0.01, 0.0},
            {0.10, 0.01, 0.05, 0.02, 0.02, 0.05, 0.01, 0.1},
            {-0.25, -0.25, 0.01, 0.01, 0.01, 0.01, -0.25, -0.2},
            {1.00, -0.25, 0.10, 0.05, 0.05, 0.10, -0.25, 1.0}
    };

    private final double[][] scoringMat2 =
            {
                    {1.01, -0.43, 0.38, 0.07, 0.00, 0.42, -0.20, 1.02},
                    {-0.27, -0.74, -0.16, -0.14, -0.13, -0.25, -0.65, -0.39},
                    {0.56, -0.30, 0.12, 0.05, -0.04, 0.07, -0.15, 0.48},
                    {0.01, -0.08, 0.01, -0.01, -0.04, -0.02, -0.12, 0.03},
                    {-0.10, -0.08, 0.01, -0.01, -0.03, 0.02, -0.04, -0.20},
                    {0.59, -0.23, 0.06, 0.01, 0.04, 0.06, -0.19, 0.35},
                    {-0.06, -0.55, -0.18, -0.08, -0.15, -0.31, -0.82, -0.58},
                    {0.96, -0.42, 0.67, -0.02, -0.03, 0.81, -0.51, 1.01},
            };

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
    public Move nextMove(Move prevMove, long tOpponent, long t) {
        if (prevMove != null) { // append move to local game if there was a last move
            othelloGame.makeMove(othelloGame.getCurrPlayer() % 2 != 0, prevMove.x, prevMove.y);
        }

        Move m = miniMaxDecision();
        if (m != null) {
            othelloGame.makeMove(othelloGame.getCurrPlayer() % 2 != 0, m.x, m.y);
            return m;
        } else {
            return prevMove; // if m == null then the player has to pass
        }
    }

    public Move miniMaxDecision(){
        // get possible moves
        ArrayList<Move> possibleMoves = (ArrayList<Move>) othelloGame.getPossibleMoves(othelloGame.getCurrPlayer() % 2 != 0);

        Move bestMove = null;
        double bestScore = Double.NEGATIVE_INFINITY;



        // for each move recurse
        for (Move move : possibleMoves) {
            double tempScore = miniMaxValue(applyMove(this.othelloGame, move), this.othelloGame, 2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            if (tempScore >= bestScore){
                bestMove = move;
                bestScore = tempScore;
            }
        }
        if(possibleMoves.contains(bestMove)){
            return bestMove;
        } else return null;
    }

    public OthelloGame applyMove(OthelloGame state, Move move) {
        OthelloGame gameCopy = copyGame(state);
        gameCopy.makeMove(state.getCurrPlayer() % 2 != 0, move.x, move.y);
        return gameCopy;
    }

    public OthelloGame copyGame(OthelloGame game) {
        OthelloGame gameCopy = new OthelloGame(order);
        for (Move m: game.getMoveHistroy()) {
            gameCopy.makeMove(gameCopy.getCurrPlayer() % 2 != 0, m.x, m.y);
        }
        return gameCopy;
    }

    public double miniMaxValue(OthelloGame state, OthelloGame game, int depth, double alpha, double beta){
        if(terminalRec(state, depth)){
            return scoringFunc(state);
        }
        else if (state.getCurrPlayer()-1 == order) {
            double maxScore = Double.NEGATIVE_INFINITY;
            ArrayList<Move> possibleMoves = (ArrayList<Move>) state.getPossibleMoves(state.getCurrPlayer() % 2 != 0);

            for (Move move : possibleMoves) {
                OthelloGame newState = applyMove(state, move);
                double score = miniMaxValue(newState, game, depth - 1, alpha, beta);
                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, score);
                if (beta <= alpha) {
                    break; // Beta cut-off
                }
            }

            // System.out.println("Max score " + maxScore);
            return maxScore;
        }

        else {
            double minScore = Double.POSITIVE_INFINITY;
            ArrayList<Move> possibleMoves = (ArrayList<Move>) state.getPossibleMoves(state.getCurrPlayer() % 2 != 0);

            for (Move move : possibleMoves) {
                OthelloGame newState = applyMove(state, move);
                double score = miniMaxValue(newState, game, depth - 1, alpha, beta);
                minScore = Math.min(minScore, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break; // Alpha cut-off
                }
            }

            // System.out.println("Min score " + minScore);
            return minScore;
        }

    }

    public boolean terminalRec(OthelloGame game, int depth){
        if (depth == 0){
            return true;
        }
        else if (game.gameStatus() == GameStatus.PLAYER_1_WON || game.gameStatus() == GameStatus.PLAYER_2_WON || game.gameStatus() == GameStatus.DRAW) {
            return true;
        } else {
            return false;
        }
    }
    public double scoringFunc(OthelloGame game){
        double score = 0;
        int currentPlayer = othelloGame.getCurrPlayer();

        // get all moves that were made
        ArrayList<Move> moveHistory = game.getMoveHistroy();


        // O(n)
        for (Move m : moveHistory) {
            if(game.getBoard()[m.x][m.y] == currentPlayer){
                score+= scoringMat2[m.y][m.x];
            }
        }
        return score;
    }

}
