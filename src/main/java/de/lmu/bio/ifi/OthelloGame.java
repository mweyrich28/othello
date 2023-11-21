package de.lmu.bio.ifi;

import de.lmu.bio.ifi.basicpackage.BasicBoard;
import szte.mi.Move;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OthelloGame extends BasicBoard implements Game {
    final private int GAMESIZE = 8;
    private int currPlayer = 1;
    private GameStatus status;
    private int order;
    private int currOpponent = 2;
    private int movesPassed = 0;
    private boolean playerOne = true;
    // {x, y}                       right  down     up     left     dtl     dbr    dbl      dtr
    private final int[][] directions = {{1,0}, {0,1}, {0,-1}, {-1,0}, {-1,-1}, {1,1}, {1,-1}, {-1,1}};
    private ArrayList<PlayerMove> moveHistroy = new ArrayList<>();

    public OthelloGame(int order){
        super.board = new int[][] {
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,2,1,0,0,0},
                {0,0,0,1,2,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        //initGameBoard(); // fill board with Zeros
        moveHistroy.add(new PlayerMove(3, 3));
        moveHistroy.add(new PlayerMove(3, 4));
        moveHistroy.add(new PlayerMove(3, 3));
        moveHistroy.add(new PlayerMove(4, 4));

        this.order = order;
    }
    public OthelloGame(){
        //super.board = new int[GAMESIZE][GAMESIZE];
        //initGameBoard(); // fill board with Zeros
        super.board = new int[][] {
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,2,1,0,0,0},
                {0,0,0,1,2,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        //initGameBoard(); // fill board with Zeros
        moveHistroy.add(new PlayerMove(3, 3));
        moveHistroy.add(new PlayerMove(3, 4));
        moveHistroy.add(new PlayerMove(3, 3));
        moveHistroy.add(new PlayerMove(4, 4));
    }

    public void setBoard(int[][] newBoard) {
        this.board = newBoard;
    }

    public boolean isPlayerOne() {
        return playerOne;
    }

    public int getCurrOpponent() {
        return currOpponent;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }


    public void setCurrOpponent(int currOpponent) {
        this.currOpponent = currOpponent;
    }

    public void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
    }

    public PlayerMove getPrevMove(){
        return this.moveHistroy.get(this.moveHistroy.size()-1);
    }

    public void setPlayerOne(boolean playerOne) {
        this.playerOne = playerOne;
    }

    public ArrayList<PlayerMove> getMoveHistroy() {
        return moveHistroy;
    }

    public int getOrder() {
        return order;
    }

    public int[] countPiecesOnBoard(){
        int countX = 0;
        int countO = 0;
        for (Move m : moveHistroy) {
            if (m.x != -1) {
                switch (getBoard()[m.x][m.y]){
                    case (1):
                        countX++;
                        break;
                    case (2):
                        countO++;
                        break;
                }
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
        else if (validMoves.size() == 0){
            moveHistroy.add(new PlayerMove(-1,-1, playerOne)); // here we add a move to mark a pass

            int temp = this.currPlayer;
            this.currPlayer = this.currOpponent;
            this.currOpponent = temp;
            this.playerOne = !playerOne;
            return false;
        }
        else
        {
            for (Move move : validMoves) {
                // move is valid
                if (move.x == x && move.y == y) {

                    getBoard()[x][y] = currPlayer; // update board
                    flip(move); // flip all

                    // add to history
                    moveHistroy.add(new PlayerMove(move.x, move.y, playerOne));
                    movesPassed = 0; // reset counter (for end game condition)
                    status = gameStatus();

                    // after we made the move, owe update player and opponent
                    int temp = this.currPlayer;
                    this.currPlayer = this.currOpponent;
                    this.currOpponent = temp;
                    this.playerOne = !playerOne;

                    // check if next player has valid moves
                    if (this.getPossibleMoves(this.isPlayerOne()).size() == 0) {
                        temp = this.currPlayer;
                        this.currPlayer = this.currOpponent;
                        this.currOpponent = temp;
                        this.playerOne = !this.playerOne;

                    }

                    return true;
                }
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
        // get the last two moves
        PlayerMove lastMove = moveHistroy.get(moveHistroy.size()-1);
        PlayerMove lastLastMove = moveHistroy.get(moveHistroy.size()-2);

        HashSet<Integer> set = new HashSet<>();
        set.add(lastMove.x);
        set.add(lastMove.y);
        set.add(lastLastMove.x);
        set.add(lastLastMove.y);


        if(set.size() == 1 || moveHistroy.size() >= 64){ // no more moves possible
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

    @Override
    public List<Move> getPossibleMoves(boolean playerOne){
        if (playerOne && currPlayer != 1){
            return null;
        }

        if (!playerOne && currPlayer != 2){
            return null;
        }

        ArrayList<Move> possibleMoves = new ArrayList<>();
        HashSet<String> seenCoordinates = new HashSet<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(this.board[j][i] == 0) {
                    for (int[] dir: directions){
                        String coordinate = j + "," + i;
                        if(checkForPieces(j, i, dir) && !seenCoordinates.contains(coordinate)){
                            possibleMoves.add(new Move(j, i));
                            seenCoordinates.add(coordinate);
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }


    // Get all empty fields next to opponent
    public ArrayList<Integer[]> getAnchorNodes(){
        ArrayList<Integer[]> anchorNodes = new ArrayList<>();
        HashSet<String> seenCoordinates = new HashSet<>();
        for (PlayerMove madeMove : moveHistroy) {

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
            int fieldVal = getBoard()[newX][newY];
            if (fieldVal == 0){
                break;
            }
            else // we only hit this block if we found curr player again
                if (fieldVal == currOpponent)
                {
                newX+=direction[0];
                newY+=direction[1];
                hitOpponentSwitch = true;
                }
                else
                {
                    return fieldVal == currPlayer && hitOpponentSwitch; // return true if we hit player again after hitting opponents
                }
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

    public List<Move> getPossibleMoves2(boolean playerOne){
        if (playerOne && currPlayer != 1){
            return null;
        }

        if (!playerOne && currPlayer != 2){
            return null;
        }

        ArrayList<Move> possibleMoves = new ArrayList<>();
        ArrayList<Integer[]> moveAnchors = getAnchorNodes();
        HashSet<String> seenCoordinates = new HashSet<>(); // TODO: refactor duplicate code

        for (Integer[] cord : moveAnchors) {
            for (int[] direction : getDirections()) {
                String coordinate = cord[0] + "," + cord[1];
                if (checkForPieces(cord[0], cord[1], direction) && !seenCoordinates.contains(coordinate)) {
                    seenCoordinates.add(coordinate);
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
