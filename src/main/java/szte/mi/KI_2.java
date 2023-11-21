package szte.mi;

import de.lmu.bio.ifi.GameStatus;
import de.lmu.bio.ifi.OthelloGame;
import de.lmu.bio.ifi.PlayerMove;

import java.util.ArrayList;
import java.util.Random;

public class KI_2 implements Player{
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

    public void init(int order, long t, Random rnd){
        this.othelloGame = new OthelloGame(order);
        this.timeLeft = t;
        this.rnd = rnd;
        this.order = order;
    }

    public void incrementPoints(int points){
        this.points+=points;
    }
    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {
        if (prevMove != null) { // append move to local game if there was a last move
            othelloGame.makeMove(othelloGame.isPlayerOne(), prevMove.x, prevMove.y);
            if(othelloGame.gameStatus() != GameStatus.RUNNING){
                return null;
            }
        }

        //if (prevMove == null && othelloGame.gameStatus() == GameStatus.RUNNING)

        if (othelloGame.getPossibleMoves(othelloGame.isPlayerOne()).size() == 0){
            return null;
        }

        if (othelloGame.getCurrPlayer() - 1 != order ) {
            return null;
        }

        long startTime = System.nanoTime(); // Record start time

        Move m = miniMaxDecision();

        long endTime = System.nanoTime(); // Record end time

        // Calculate time taken in milliseconds
        long timeTaken = (endTime - startTime) / 1_000_000; // Convert nanoseconds to milliseconds
        timeLeft-=timeTaken;

        // Log or use the timeTaken value as needed
        // System.out.println("Time taken for the move: " + timeTaken + " milliseconds");
        // System.out.println("Time left: " + this.timeLeft);
        if (m != null) {
            othelloGame.makeMove(othelloGame.getCurrPlayer() % 2 != 0, m.x, m.y);
            return m;
        } else {
            //othelloGame.incrementMovesPassed();
            return null; // if m == null then the player has to pass
        }
    }

    public Move miniMaxDecision(){
        // get possible moves
        ArrayList<Move> possibleMoves = (ArrayList<Move>) othelloGame.getPossibleMoves(othelloGame.getCurrPlayer() % 2 != 0);

        Move bestMove = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        GameStatus status = othelloGame.gameStatus();
        int depth = 4;
        if(possibleMoves.size() <= 10){
           depth = 5;
        }
        // for each move recurse
        for (Move move : possibleMoves) {
            double tempScore = miniMaxValue(applyMove(this.othelloGame, move, status), depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            if (tempScore >= bestScore){
                bestMove = move;
                bestScore = tempScore;
            }
        }
        if(possibleMoves.contains(bestMove)){
            return bestMove;
        } else return null;
    }

    public OthelloGame applyMove(OthelloGame state, Move move, GameStatus status) {
        OthelloGame gameCopy = copyGame(state, status);
        gameCopy.getBoard()[move.x][move.y] = state.getCurrPlayer();
        gameCopy.getMoveHistroy().add(new PlayerMove(move.x, move.y, state.isPlayerOne()));
        gameCopy.flip(move);
        gameCopy.setCurrOpponent(state.getCurrPlayer());
        gameCopy.setCurrPlayer(state.getCurrOpponent());
        state.setPlayerOne(!state.isPlayerOne());
        //gameCopy.makeMove(state.getCurrPlayer() % 2 != 0, move.x, move.y);
        return gameCopy;
    }

    public OthelloGame copyGame(OthelloGame game, GameStatus status) {

        OthelloGame gameCopy = new OthelloGame();
        gameCopy.getMoveHistroy().clear();
        boolean playerSwitch = true;
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
        gameCopy.setStatus(status);


        return gameCopy;
    }

    public double miniMaxValue(OthelloGame state, int depth, double alpha, double beta){
        if(terminalRec(state, depth)){
            return scoringFunc(state);
        }
        else if (state.getCurrPlayer()-1 == order) {
            double maxScore = Double.NEGATIVE_INFINITY;
            ArrayList<Move> possibleMoves = (ArrayList<Move>) state.getPossibleMoves(state.getCurrPlayer() % 2 != 0);
            GameStatus status = state.gameStatus();

            for (Move move : possibleMoves) {
                OthelloGame newState = applyMove(state, move, status);
                double score = miniMaxValue(newState,depth - 1, alpha, beta);
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
            GameStatus status = state.gameStatus();

            for (Move move : possibleMoves) {
                OthelloGame newState = applyMove(state, move, status);
                double score = miniMaxValue(newState,depth - 1, alpha, beta);
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
    // for testing
    public void appendGameState(OthelloGame game){
        this.othelloGame = copyGame(game, game.gameStatus());
    }

    public boolean terminalRec(OthelloGame game, int depth){
        if (depth == 0){
            return true;
        }
        else if (game.gameStatus() != null && !(game.gameStatus() == GameStatus.RUNNING)) {
            return true;
        } else {
            return false;
        }
    }
    public double scoringFunc(OthelloGame game){
        double score = 0;
        int currentPlayer = othelloGame.getCurrPlayer();

        // get all moves that were made
        ArrayList<PlayerMove> moveHistory = game.getMoveHistroy();


        // O(n)
        for (Move m : moveHistory) {
            if(game.getBoard()[m.x][m.y] == currentPlayer){
                score+= scoringMat2[m.y][m.x];
            }
        }
        return score;
    }

}
