package de.lmu.bio.ifi;

import szte.mi.KI_1;
import szte.mi.KI_Random;
import szte.mi.Move;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;

public class Runner {

    // stole method from tutorium
    public static ArrayList<Move> readMoves(Reader reader) throws FileNotFoundException {
        ArrayList<Move> list = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split("/"); // ["(x", "y)"]
                int x = Integer.parseInt(Character.toString(parts[0].charAt(1))); // "x"
                int y = Integer.parseInt(Character.toString(parts[1].charAt(0))); // "y"
                list.add(new Move(x,y));
            }
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }



    public static void main(String args[]) throws Exception {

        //FileReader reader = new FileReader(new File("/home/malte/01_Documents/projects/othello/src/main/java/de/lmu/bio/ifi/moves.tsv"));
        //ArrayList<Move> moves = readMoves(reader);
        //OthelloGame o = new OthelloGame();
        //int c = 0;
    //    KI_1 ki = new KI_1();
    //    KI_1 ki1 = new KI_1();
    //    ki.init(0, 1, new Random());
    //    ki1.init(1,2, new Random());
    //    for (Move m : moves) {

    //        System.out.println(o.makeMove(o.isPlayerOne(), m.x, m.y));

    //        System.out.println("Last Move " + m.x + " " + m.y + " of " + o.getCurrOpponent());
    //        System.out.println("Next Player: " + o.getCurrPlayer());

    //        if (o.getPossibleMoves(false) == null && o.getPossibleMoves(true) == null) {
    //            System.out.println("Moves for player 1: null");
    //            System.out.println("Moves for player 2: null");
    //        }
    //        else if (o.getPossibleMoves(true) == null){
    //            System.out.println("Moves for player 1: null");
    //            System.out.println("Moves for player 2: " +o.getPossibleMoves(false).size());
    //        }
    //        else if (o.getPossibleMoves(false) == null){
    //            System.out.println("Moves for player 1: " +o.getPossibleMoves(true).size());
    //            System.out.println("Moves for player 2: null");
    //        }
    //        else {
    //            System.out.println("Moves for player 1: " +o.getPossibleMoves(true).size());
    //            System.out.println("Moves for player 2: " +o.getPossibleMoves(false).size());
    //        }
    //        System.out.println(o.gameStatus().toString());
    //        System.out.println(o);
    //        c++;
    //    }
    //    System.out.println("----------------------------------------------------------");
    //    System.out.println(o.isPlayerOne());


    //    ki1.appendGameState(o);
    //    System.out.println(ki1.getOthelloGame());

    //    Move last = new Move(0,3);



    //    Move lastMove = ki1.nextMove(last, 1,1);
    //    System.out.println("Next Player: " + o.getCurrPlayer());
    //    System.out.println(lastMove.x + " " + lastMove.y);
    //    System.out.println(o.makeMove(o.isPlayerOne(), lastMove.x, lastMove.y));
    //    System.out.println(o);

    //    if (o.getPossibleMoves(false) == null && o.getPossibleMoves(true) == null) {
    //        System.out.println("Moves for player 1: null");
    //        System.out.println("Moves for player 2: null");
    //    }
    //    else if (o.getPossibleMoves(true) == null){
    //        System.out.println("Moves for player 1: null");
    //        System.out.println("Moves for player 2: " +o.getPossibleMoves(false).size());
    //    }
    //    else if (o.getPossibleMoves(false) == null){
    //        System.out.println("Moves for player 1: " +o.getPossibleMoves(true).size());
    //        System.out.println("Moves for player 2: null");
    //    }
    //    else {
    //        System.out.println("Moves for player 1: " +o.getPossibleMoves(true).size());
    //        System.out.println("Moves for player 2: " +o.getPossibleMoves(false).size());
    //    }

    //    lastMove = new Move(0,4);
    //    System.out.println(o.makeMove(o.isPlayerOne(), lastMove.x, lastMove.y));
    //    System.out.println(o);
    //    System.out.println(o.isPlayerOne());


    //    lastMove = ki1.nextMove(lastMove, 1,1);
    //    System.out.println("Next Player: " + o.getCurrPlayer());
    //    System.out.println(lastMove.x + " " + lastMove.y);
    //    System.out.println(o.makeMove(o.isPlayerOne(), lastMove.x, lastMove.y));
    //    System.out.println(o);

    //    if (o.getPossibleMoves(false) == null && o.getPossibleMoves(true) == null) {
    //        System.out.println("Moves for player 1: null");
    //        System.out.println("Moves for player 2: null");
    //    }
    //    else if (o.getPossibleMoves(true) == null){
    //        System.out.println("Moves for player 1: null");
    //        System.out.println("Moves for player 2: " +o.getPossibleMoves(false).size());
    //    }
    //    else if (o.getPossibleMoves(false) == null){
    //        System.out.println("Moves for player 1: " +o.getPossibleMoves(true).size());
    //        System.out.println("Moves for player 2: null");
    //    }
    //    else {
    //        System.out.println("Moves for player 1: " +o.getPossibleMoves(true).size());
    //        System.out.println("Moves for player 2: " +o.getPossibleMoves(false).size());
    //    }

    //    lastMove = new Move(6,6);
    //    System.out.println(o.makeMove(o.isPlayerOne(), lastMove.x, lastMove.y));
    //    System.out.println(o);
    //    System.out.println(o.isPlayerOne());


    //    lastMove = ki1.nextMove(lastMove, 1,1);
    //    System.out.println("Next Player: " + o.getCurrPlayer());
    //    System.out.println(lastMove.x + " " + lastMove.y);
    //    System.out.println(o.makeMove(o.isPlayerOne(), lastMove.x, lastMove.y));
    //    System.out.println(o);

    //    if (o.getPossibleMoves(false) == null && o.getPossibleMoves(true) == null) {
    //        System.out.println("Moves for player 1: null");
    //        System.out.println("Moves for player 2: null");
    //    }
    //    else if (o.getPossibleMoves(true) == null){
    //        System.out.println("Moves for player 1: null");
    //        System.out.println("Moves for player 2: " +o.getPossibleMoves(false).size());
    //    }
    //    else if (o.getPossibleMoves(false) == null){
    //        System.out.println("Moves for player 1: " +o.getPossibleMoves(true).size());
    //        System.out.println("Moves for player 2: null");
    //    }
    //    else {
    //        System.out.println("Moves for player 1: " +o.getPossibleMoves(true).size());
    //        System.out.println("Moves for player 2: " +o.getPossibleMoves(false).size());
    //    }
        OthelloGui.main(args);
    }
}
