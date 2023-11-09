package de.lmu.bio.ifi;
import de.lmu.bio.ifi.basicpackage.BasicBoard;
import szte.mi.Move;

import javax.swing.*;
import java.util.*;

public class OthelloGame extends BasicBoard implements Game {
    final private int GAMESIZE = 8;
    private int movesMade = 0;
    private int currPlayer = 1;
    private int currOpponent = 2;
    // {x, y}                       right  down     up     left     dtl     dbr    dbl      dtr
    private final int[][] directions = {{1,0}, {0,1}, {0,-1}, {-1,0}, {-1,-1}, {1,1}, {1,-1}, {-1,1}};
    private ArrayList<Move> moveHistroy = new ArrayList<>();

    public OthelloGame(int order){
        super.board = new int[GAMESIZE][GAMESIZE];
        initGameBoard(); // fill board with Zeros
        // flip if order is 1
        if (order == 1){
            currPlayer = 2;
            currOpponent = 1;
        }
    }

    public int[] countPiecesOnBoard(){
        int countX = 0;
        int countO = 0;
        for (Move m : moveHistroy) {
            switch (getBoard()[m.x][m.y]){
                case (1):
                    countX++;
                    break;
                case (2):
                    countO++;
                    break;
            }
        }

        return new int[] {countX, countO};
    }

    public int[][] getDirections() {
        return directions;
    }

    public int getCurrPlayer() {
        return currPlayer;
    }

    public void initGameBoard(){
        for (int i = 0; i < GAMESIZE; i++) {
            for (int j = 0; j < GAMESIZE; j++) {
                this.board[i][j] = 0;
            }
        }
        this.board[3][3] = 2;
        this.board[3][4] = 1;
        this.board[4][3] = 1;
        this.board[4][4] = 2;
        for (int i = 0; i < 2; i++) {
           moveHistroy.add(new Move(3, 3+i));
           moveHistroy.add(new Move(4, 3+i));
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < GAMESIZE; i++) {
            for (int j = 0; j < GAMESIZE; j++) {
                switch (this.board[j][i]){
                    case 0:
                        sb.append(".");
                        break;
                    case 1:
                        sb.append("X");
                        break;
                    case 2:
                        sb.append("O");
                        break;
                }
                // we don't want spaces on the right side of the board
                if (j != GAMESIZE-1){
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    /**
     * Make a move for the given player at the given position.
     *
     * @param playerOne true if player 1, else player 2.
     * @param y the y coordinate of the move.
     * @param x the x coordinate of the move.
     * @return true if the move was valid, else false.
     */
    @Override
    public boolean makeMove(boolean playerOne, int x, int y){
        ArrayList<Move> validMoves = (ArrayList<Move>) getPossibleMoves(playerOne);
        if (validMoves == null){
            return false;
        }
        if (validMoves.size() == 0) {
            // switch player state
            int tempVar = currOpponent;
            currOpponent = currPlayer;
            currPlayer = tempVar;
            return false;
        }

        for (Move move : validMoves) {
            if (move.x == x && move.y == y){

                // increment moves made
                movesMade++;

                getBoard()[x][y] = currPlayer;

                // flip all
                flip(move);
                System.out.println(this.toString());


                // add to history
                moveHistroy.add(move);

                // switch player state
                int tempVar = currOpponent;
                currOpponent = currPlayer;
                currPlayer = tempVar;

                //System.out.println(gameStatus().toString());
                return true;
            }
        }
        return false;

    }

    /**
     * Check and return the status of the game, if there is a winner, a draw or still running.
     *
     * @return the current game status.
     */
    @Override
    public GameStatus gameStatus(){
        ArrayList<Move> movesX = (ArrayList<Move>) getPossibleMoves(true);
        ArrayList<Move> movesO = (ArrayList<Move>) getPossibleMoves(false);

        if (movesX == null) { movesX = new ArrayList<>(); }
        if (movesO == null) { movesO = new ArrayList<>(); }

        if(movesX.size() == 0 && movesO.size() == 0){ // no more moves possible
            int[] pieceCount = countPiecesOnBoard(); // [countX, countO]
            if (pieceCount[0] > pieceCount[1]){ // xCount > oCount?
               return GameStatus.PLAYER_1_WON;
            }
            else if (pieceCount[0] < pieceCount[1]) {
                return GameStatus.PLAYER_2_WON;
            }
            else {
                return GameStatus.DRAW;
            }
        }

        return GameStatus.RUNNING;
    }

    public void flip(Move move){
        ArrayList<Integer[]> toFlip = new ArrayList<>();

        for (int[] dir : directions) {
            ArrayList<Integer[]> currChain = new ArrayList<>();

            // peak one field ahead
            int newX = move.x + dir[0];
            int newY = move.y + dir[1];

            boolean hitOpponentSwitch = false;

            // if peaked field was opponent we continue in this direction until we either hit 0 or our piece
            while (stillOnBoard(newX, newY)) {
                if (getBoard()[newX][newY] == 0) {
                    break;
                } else if (getBoard()[newX][newY] == currOpponent) {
                    // add field to be flipped (if we hit ur player again
                    currChain.add(new Integer[]{newX, newY});

                    // increment coords
                    newX += dir[0];
                    newY += dir[1];
                    // set switch
                    hitOpponentSwitch = true;
                } else if (getBoard()[newX][newY] == currPlayer && hitOpponentSwitch) {
                    toFlip.addAll(currChain); // return true if we hit player again after hitting opponents
                    break;
                }
                else {
                    break;
                }
            }
        }

        for (Integer[] moveToFlip : toFlip) {
            getBoard()[moveToFlip[0]][moveToFlip[1]] = currPlayer;
            // flip(new Move(moveToFlip[0], moveToFlip[1]));
        }
    }


    // Get all empty fields next to opponent
    public ArrayList<Integer[]> getAnchorNodes(boolean playerOne){
        ArrayList<Integer[]> anchorNodes = new ArrayList<>();
        HashSet<String> seenCoordinates = new HashSet<>();
        for (Move madeMove : moveHistroy) {

            int x = madeMove.x;
            int y = madeMove.y;

            if(getBoard()[x][y] == currOpponent) {

                for (int[] direction : directions) {

                    int newY = y + direction[1];
                    int newX = x + direction[0];

                    if (stillOnBoard(newX, newY)){ // check if we're still on board
                        if (getBoard()[newX][newY] == 0) { // cech for unoccupied field
                            String stringRepOfCoordinate = newX + "," + newY;
                            if (!seenCoordinates.contains(stringRepOfCoordinate)){
                                anchorNodes.add(new Integer[]{newX, newY});
                                seenCoordinates.add(stringRepOfCoordinate);

                            }
                        }
                    }
                }

            }
        }
        return anchorNodes;
    }

    public boolean checkForPieces(int x, int y, int[] direction){

        // peak one field ahead
        int newX = x + direction[0];
        int newY = y + direction[1];

        boolean hitOpponentSwitch = false;

        // if peaked field was opponent we continue in this direction until we either hit 0 or our piece
        while(stillOnBoard(newX, newY)){
            if (getBoard()[newX][newY] == 0){
                break;
            } else // we only hit this block if we found curr player again
                if (getBoard()[newX][newY] == currOpponent){
                newX+=direction[0];
                newY+=direction[1];
                hitOpponentSwitch = true;
            } else return getBoard()[newX][newY] == currPlayer && hitOpponentSwitch; // return true if we hit player again after hitting opponents
        }
        return false;
    }

    /**
     * Get all possible moves for the current player.
     * Return null if it is not the turn of the given player.
     * The list is empty if there are no possible moves.
     *
     * @return a list of all possible moves.
     */

    @Override
    public List<Move> getPossibleMoves(boolean playerOne){
        if (playerOne && currPlayer != 1){
            return null;
        }

        if (!playerOne && currPlayer != 2){
            return null;
        }

        ArrayList<Move> possibleMoves = new ArrayList<>();
        ArrayList<Integer[]> moveAnchors = getAnchorNodes(playerOne);
        HashSet<String> seenCoordinates = new HashSet<>(); // TODO: refactor duplicate code

        for (Integer[] cord : moveAnchors) {
            for (int[] direction : getDirections()) {
                String cordinate = cord[0] + "," + cord[1];
                if (checkForPieces(cord[0], cord[1], direction) && !seenCoordinates.contains(cordinate)) {
                    seenCoordinates.add(cordinate);
                    possibleMoves.add(new Move(cord[0], cord[1]));
                }
            }
        }


        return possibleMoves;
    }

    public boolean stillOnBoard(int x, int y){
        return ((x < GAMESIZE && y < GAMESIZE) && (x >= 0 && y >= 0));
    }

}
