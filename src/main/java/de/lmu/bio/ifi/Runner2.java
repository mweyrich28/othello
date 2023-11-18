package de.lmu.bio.ifi;

import szte.mi.KI_1;
import szte.mi.KI_Random;
import szte.mi.Move;

import java.util.Random;

public class Runner2 {
    public static void main(String[] args){
        KI_1 p2 = new KI_1();
        KI_Random p1 = new KI_Random();

        Move last = null;
        int win = 0;
        int draw = 0;

        long startTime, endTime;
        for (int j = 1; j <= 100; j++) {
            startTime = System.currentTimeMillis();
            OthelloGame o = new OthelloGame();
            p1.init(0, (long) 8.000, new Random());
            p2.init(1, (long) 8.000, new Random());

            while(o.gameStatus() == GameStatus.RUNNING){
                if(p1.getOthelloGame().gameStatus() != GameStatus.RUNNING || p2.getOthelloGame().gameStatus() != GameStatus.RUNNING ){
                    System.out.println("P1!!!!!!!!!!!!");
                    break;
                }

                if(o.getCurrPlayer() == 1){
                    last = p1.nextMove(last, 1, 1);
                    if(last != null){
                        o.makeMove(true, last.x, last.y);
                    }
                } else {
                    last = p2.nextMove(last, 1, 1);
                    if(last != null){
                        o.makeMove(false, last.x, last.y);
                    }
                }

                if (o.gameStatus() != GameStatus.RUNNING){
                    break;
                }


            }
            System.out.println("ROUND " + j);
            System.out.println(o.gameStatus().toString());
            System.out.println(o.countPiecesOnBoard()[0]);
            System.out.println(o.countPiecesOnBoard()[1]);
            if (o.countPiecesOnBoard()[1] == 0){
                OthelloGame gameCopy = new OthelloGame();
                for (Move m : o.getMoveHistroy()) {
                    gameCopy.makeMove(gameCopy.getCurrPlayer() % 2 != 0, m.x, m.y);
                    System.out.println(gameCopy);
                }
            }
            if (o.gameStatus() == GameStatus.PLAYER_1_WON){
                win++;
            } else if (o.gameStatus() == GameStatus.DRAW){
                draw++;
            }
            System.out.println(win + " wins");
            System.out.println(draw + " draws");
            System.out.println(j - win + " losses");
            System.out.println(o);
            System.out.println(p1.getOthelloGame());
            System.out.println(p2.getOthelloGame());

            if (o.gameStatus() == GameStatus.RUNNING){
                // OthelloGame copy = new OthelloGame();
                // for (Move m: o.getMoveHistroy()) {
                //     copy.makeMove(copy.getCurrPlayer() % 2 != 0, m.x, m.y);
                //     System.out.println(copy);
                // }
                for (int i = 0; i < p1.getOthelloGame().getMoveHistroy().size() -2 ; i++) {
                    System.out.println(i);
                    System.out.println("p1: " + p1.getOthelloGame().getMoveHistroy().get(i).x +" " + p1.getOthelloGame().getMoveHistroy().get(i).y);
                    System.out.println("p2: " + p2.getOthelloGame().getMoveHistroy().get(i).x +" " + p2.getOthelloGame().getMoveHistroy().get(i).y);
                }

                break;
            }

            endTime = System.currentTimeMillis();
            long roundTime = endTime - startTime;
            System.out.println("Round " + j + " took " + roundTime + " milliseconds");
        }
    }
}
