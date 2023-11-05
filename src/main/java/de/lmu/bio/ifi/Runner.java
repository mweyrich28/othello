package de.lmu.bio.ifi;

import szte.mi.Move;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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

    public static void main(String args[]) throws FileNotFoundException {

        FileReader reader = new FileReader(new File("/home/malte/01_Documents/projects/othello/src/main/java/de/lmu/bio/ifi/moves.tsv"));
        ArrayList<Move> moves = readMoves(reader);

        OthelloGame o = new OthelloGame();
        System.out.println(o.toString());
        ArrayList<Integer[]> moveAnchors = o.getAnchorNodes(true);

        int c = 0;
        for (Move cord : moves) {
                o.makeMove(c%2 == 0, cord.x, cord.y);
                c++;
        }
    }
}
