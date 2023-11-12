package de.lmu.bio.ifi;

import javafx.application.Application;
import szte.mi.Move;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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

        FileReader reader = new FileReader(new File("/home/malte/01_Documents/projects/othello/src/main/java/de/lmu/bio/ifi/moves.tsv"));
        ArrayList<Move> moves = readMoves(reader);
        OthelloGame o = new OthelloGame(0);
        int c = 0;
        // for (Move m : moves) {
        //     o.makeMove(c%2==0, m.x, m.y);
        //     System.out.println(o.gameStatus().toString());
        //     System.out.println(o.toString());
        //     System.out.println("Next move: " + o.getCurrPlayer());
        //     if (o.getPossibleMoves(false) == null && o.getPossibleMoves(true) == null) {
        //         System.out.println("Moves for player 1: null");
        //         System.out.println("Moves for player 2: null");
        //     }
        //     else if (o.getPossibleMoves(true) == null){
        //         System.out.println("Moves for player 1: null");
        //         System.out.println("Moves for player 2: " +o.getPossibleMoves(false).size());
        //     }
        //     else if (o.getPossibleMoves(false) == null){
        //         System.out.println("Moves for player 1: " +o.getPossibleMoves(true).size());
        //         System.out.println("Moves for player 2: null");
        //     }
        //     else {
        //         System.out.println("Moves for player 1: " +o.getPossibleMoves(true).size());
        //         System.out.println("Moves for player 2: " +o.getPossibleMoves(false).size());
        //     }
        //     c++;
        //     System.out.println(o.gameStatus().toString());
        // }
        OthelloGui.main(args);
    }
}
