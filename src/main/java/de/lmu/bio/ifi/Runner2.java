package de.lmu.bio.ifi;

import szte.mi.KI_1;
import szte.mi.KI_2;
import szte.mi.KI_Random;
import szte.mi.Move;

import java.util.ArrayList;
import java.util.Random;

public class Runner2 {
    public static void main(String[] args){
        KI_2 p1 = new KI_2();
        KI_Random p2 = new KI_Random();

        Move last = null;
        int win = 0;
        int draw = 0;

        long startTime, endTime;
        for (int j = 1; j <= 100; j++) {
            startTime = System.currentTimeMillis();
            OthelloGame o = new OthelloGame();
            p1.init(0, 8000, new Random());
            p2.init(1, 8000, new Random());

            //ArrayList<PlayerMove> moves = new ArrayList<>();
            //moves.add(new PlayerMove(0,0, true));
            //moves.add(new PlayerMove(0,1, false));
            //moves.add(new PlayerMove(1,0, false));
            //moves.add(new PlayerMove(2,2, true));
            //moves.add(new PlayerMove(3,3, true));
            //moves.add(new PlayerMove(4,4, true));
            //moves.add(new PlayerMove(5,5, true));
            //moves.add(new PlayerMove(6,6, true));
            //moves.add(new PlayerMove(7,7, false));
            //o.setBoard(
            //        new int[][]{
            //                {1, 2, 0, 0, 0, 0, 0, 0},
            //                {2, 0, 0, 0, 0, 0, 0, 0},
            //                {0, 0, 1, 0, 0, 0, 0, 0},
            //                {0, 0, 0, 1, 0, 0, 0, 0},
            //                {0, 0, 0, 0, 1, 0, 0, 0},
            //                {0, 0, 0, 0, 0, 1, 0, 0},
            //                {0, 0, 0, 0, 0, 0, 1, 0},
            //                {0, 0, 0, 0, 0, 0, 0, 2}
            //        }
            //);
            //o.setMoveHistroy(moves);
            //o.setStatus(GameStatus.RUNNING);
            //o.setCurrPlayer(1);
            //o.setCurrOpponent(2);
            //o.setPlayerOne(true);

            //p1.appendGameState(o);
            //p1.getOthelloGame().setCurrPlayer(1);
            //p1.getOthelloGame().setCurrOpponent(2);
            //p1.getOthelloGame().setPlayerOne(true);

            //p2.appendGameState(o);
            //p2.getOthelloGame().setCurrPlayer(1);
            //p2.getOthelloGame().setCurrOpponent(2);
            //p2.getOthelloGame().setPlayerOne(true);
            //System.out.println(o);
            //last = new Move(2,2);
            while(o.gameStatus() == GameStatus.RUNNING){
                int isNull = 0;
                if(p1.getOthelloGame().gameStatus() != GameStatus.RUNNING || p2.getOthelloGame().gameStatus() != GameStatus.RUNNING ){
                    break;
                }

                last = p1.nextMove(last, 1, 1);
                if (last != null){
                    o.makeMove(o.isPlayerOne(), last.x, last.y);
                } else {
                    isNull++;
                }
                if (o.gameStatus() != GameStatus.RUNNING){
                    break;
                }

                last = p2.nextMove(last, 1, 1);
                if (last != null){
                    o.makeMove(o.isPlayerOne(), last.x, last.y);
                }else {
                    isNull++;
                }
                if (o.gameStatus() != GameStatus.RUNNING){
                    break;
                }


                if(isNull == 2){
                    break;
                }
            }
            System.out.println("ROUND " + j);
            System.out.println(o.gameStatus().toString());
            System.out.println(o.countPiecesOnBoard()[0]);
            System.out.println(o.countPiecesOnBoard()[1]);

            if (o.gameStatus() == GameStatus.PLAYER_1_WON){
                win++;
                System.out.println("1\n");
            } else if (o.gameStatus() == GameStatus.DRAW){
                draw++;
                System.out.println("0\n");
            } else {
                System.out.println("-1\n");
            }
            System.out.println(win + " wins");
            System.out.println(draw + " draws");
            System.out.println(j - win - draw + " losses");
            //System.out.println(o);
            //System.out.println(p1.getOthelloGame());
            //System.out.println(p2.getOthelloGame());

            //if (o.gameStatus() == GameStatus.RUNNING){
            //    for (int i = 0; i < p1.getOthelloGame().getMoveHistroy().size() -2 ; i++) {
            //        System.out.println(i);
            //        System.out.println("p1: " + p1.getOthelloGame().getMoveHistroy().get(i).x +" " + p1.getOthelloGame().getMoveHistroy().get(i).y);
            //        System.out.println("p2: " + p2.getOthelloGame().getMoveHistroy().get(i).x +" " + p2.getOthelloGame().getMoveHistroy().get(i).y);
            //    }

            //    break;
            //}

            endTime = System.currentTimeMillis();
            long roundTime = endTime - startTime;
            System.out.println("Round " + j + " took " + roundTime + " milliseconds");
        }
    }
}
