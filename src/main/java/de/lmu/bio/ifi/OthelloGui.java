package de.lmu.bio.ifi;
import szte.mi.Move;
import szte.mi.Player;
import szte.mi.KI_Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import szte.mi.Move;
import szte.mi.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class OthelloGui extends Application{
    Player p1;
    Move lastMove;
    Player p2;

    Button[][] gameBoardArray = new Button[8][8];
    Stage primaryStage;
    OthelloGame localGame;
    VBox infoLog;
    String[] args;

    public class PlayButtonHandler implements EventHandler<ActionEvent>{
        Button button;
        int x;
        int y;
        public PlayButtonHandler(Button button, int x, int y){
            this.button = button;
            this.x = x;
            this.y = y;
        }
        @Override
        public void handle(ActionEvent actionEvent) {
            String piece;
            if(localGame.getCurrPlayer() == 1){
                piece = "X";
            }
            else {
                piece = "O";
            }

            if(p1 == null && p2 == null){ // Handle if player vs player
                if(localGame.makeMove(localGame.getCurrPlayer() % 2 != 0, this.x, this.y)){
                    this.button.setText(piece);
                    updateGUI(localGame.getBoard());
                    showPossibleMoves((ArrayList<Move>) localGame.getPossibleMoves(localGame.getCurrPlayer() % 2 != 0));
                    updateInfoLog();
                }
            }
            else if (p1 == null && p2 != null) { // player vs KI
                Move last;
                if(localGame.makeMove(localGame.getCurrPlayer() % 2 != 0, this.x, this.y)){
                    this.button.setText(piece);
                    last = new Move(this.x, this.y);
                    updateGUI(localGame.getBoard());
                    showPossibleMoves((ArrayList<Move>) localGame.getPossibleMoves(localGame.getCurrPlayer() % 2 != 0));
                    updateInfoLog();
                    last = p2.nextMove(last, 8000, 8); // KI makes move
                    localGame.makeMove(false, last.x, last.y);
                    updateGUI(localGame.getBoard());
                    showPossibleMoves((ArrayList<Move>) localGame.getPossibleMoves(localGame.getCurrPlayer() % 2 != 0));
                }
            } else { // KI vs KI
                localGame.makeMove(localGame.getCurrPlayer() % 2 == 0, this.x, this.y);
                this.button.setText(piece);
                updateGUI(localGame.getBoard());
                showPossibleMoves((ArrayList<Move>) localGame.getPossibleMoves(localGame.getCurrPlayer() % 2 != 0));
                updateInfoLog();
            }
        }
    }
    public void updateInfoLog(){
        Label infoLabel = new Label(localGame.gameStatus().toString());
        infoLog.getChildren().add(infoLabel);

        if (infoLog.getChildren().size() > 10) {
            infoLog.getChildren().remove(0);
        }
    }
    public BorderPane initGame(){
        BorderPane gameScene = new BorderPane();
        GridPane gameGrid = new GridPane();
        Button restartGame = new Button("Restart");
        HBox buttonMenu = new HBox(10);
        VBox infoLog = new VBox();
        this.infoLog = infoLog;
        infoLog.setPrefWidth(200);
        infoLog.setPrefHeight(200);
        infoLog.setLayoutX(500);
        infoLog.setLayoutY(100);
        infoLog.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px;");
        infoLog.setSpacing(5);
        Label infoLabel = new Label(localGame.gameStatus().toString());
        infoLog.getChildren().add(infoLabel);

        if (infoLog.getChildren().size() > 10) {
            infoLog.getChildren().remove(0);
        }

        for (int i = 0; i < gameBoardArray.length; i++) {
            for (int j = 0; j < gameBoardArray.length; j++) {
                Button button = new Button();
                button.setPrefSize(100, 100);
                gameGrid.add(button, j, i);
                gameBoardArray[j][i] = button;
                button.addEventHandler(ActionEvent.ACTION, new PlayButtonHandler(button, j, i));
            }
        }
        gameScene.setCenter(gameGrid);
        gameGrid.setAlignment(Pos.CENTER);
        buttonMenu.getChildren().add(restartGame);
        gameScene.setBottom(buttonMenu);
        gameScene.setRight(infoLog);

        restartGame.setOnAction((ActionEvent r) -> {
            Stage stage = (Stage) restartGame.getScene().getWindow();
            stage.close();
            start(new Stage());
        });

        return gameScene;
    }
    
    public void showPossibleMoves(ArrayList<Move> moves){
        for (Move m : moves) {
            gameBoardArray[m.x][m.y].setStyle("-fx-background-color: #90ee90;-fx-border-color: black; -fx-border-width: 2;");
        }
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        this.localGame = new OthelloGame(0);
        primaryStage.setScene(new Scene(initStartMenu()));
        primaryStage.show();
        //updateGUI(localGame.getBoard());


        //showPossibleMoves((ArrayList<Move>) localGame.getPossibleMoves(true));

    }

    private BorderPane initStartMenu(){

        // Start Menu
        BorderPane startMenu = new BorderPane();
        VBox menuStack = new VBox(10);

        // header label
        Label headerLabel = new Label("Othello ProPra");
        headerLabel.setStyle("-fx-background-color: white; -fx-font-size: 40; -fx-padding: 40; -fx-border-color: #000000");

        // center Game Title
        BorderPane.setAlignment(headerLabel, Pos.TOP_CENTER);
        startMenu.setTop(headerLabel);
        headerLabel.setAlignment(Pos.TOP_CENTER);

        RadioButton rb1 = new RadioButton("Player vs. Player");
        RadioButton rb2 = new RadioButton("Player vs. KI");
        RadioButton rb3 = new RadioButton("KI vs. KI");
        ToggleGroup tg = new ToggleGroup();
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        rb3.setToggleGroup(tg);
        rb1.setSelected(true);
        rb1.setLayoutX(500);
        rb1.setLayoutY(10);
        rb2.setLayoutX(500);
        rb2.setLayoutY(30);
        rb3.setLayoutX(500);
        rb3.setLayoutY(50);
        menuStack.getChildren().addAll(rb1, rb2, rb3);


        Button startGame = new Button("Start Game");
        startGame.setLayoutX(500);
        startGame.setLayoutY(80);
        startGame.setOnAction((ActionEvent e) -> {
            if (rb1.isSelected()) {
                playerVSplayer();
            } else if (rb2.isSelected()) {
                playerVSKI();
            } else if (rb3.isSelected()) {
                kiVSki();
            }
        });
        menuStack.getChildren().add(startGame);
        menuStack.setAlignment(Pos.CENTER);
        startMenu.setCenter(menuStack);

        return startMenu;
    }

    public void playerVSplayer(){
        this.p1 = null;
        this.p2 = null;
        primaryStage.setScene(new Scene(initGame()));
        updateGUI(this.localGame.getBoard());
        showPossibleMoves( (ArrayList<Move>) this.localGame.getPossibleMoves(true));
        primaryStage.show();
    }
    public void playerVSKI(){
        KI_Random p2 = new KI_Random();
        this.p2 = p2;

        p2.init(1, 8000, new Random());

        OthelloGame o = new OthelloGame(0);
        System.out.println(o.toString());
        primaryStage.setScene(new Scene(initGame()));
        updateGUI(this.localGame.getBoard());
        showPossibleMoves( (ArrayList<Move>) this.localGame.getPossibleMoves(true));
        primaryStage.show();
    }

    public void kiVSki(){
        KI_Random p1 = new KI_Random();
        KI_Random p2 = new KI_Random();

        p1.init(1, 8000, new Random());
        p2.init(1, 8000, new Random());

        OthelloGame o = new OthelloGame(0);
        System.out.println(o.toString());
        Move last = null;
        primaryStage.setScene(new Scene(initGame()));
        updateGUI(this.localGame.getBoard());
        showPossibleMoves( (ArrayList<Move>) this.localGame.getPossibleMoves(true));

        primaryStage.show();

        // TODO: Finish this here
        lastMove = p1.nextMove(lastMove, 8000, 8);
        Button button = gameBoardArray[lastMove.x][lastMove.y];
        button.fire();
        updateGUI(localGame.getBoard());
        showPossibleMoves( (ArrayList<Move>) localGame.getPossibleMoves(true));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        lastMove = p2.nextMove(lastMove, 8000, 8);
        button = gameBoardArray[lastMove.x][lastMove.y];
        button.fire();
        updateGUI(localGame.getBoard());
        showPossibleMoves( (ArrayList<Move>) localGame.getPossibleMoves(true));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }


    public void updateGUI(int[][] gameBoard){
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                this.gameBoardArray[j][i].setStyle("-fx-background-color: #FFFFFF;-fx-font-size: 20px;-fx-border-color: black; -fx-border-width: 2;");
                switch (gameBoard[j][i]){
                    case (0):
                        break;
                    case(1):
                        this.gameBoardArray[j][i].setText("X");
                        break;
                    case(2):
                        this.gameBoardArray[j][i].setText("O");
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
