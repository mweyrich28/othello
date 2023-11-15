package de.lmu.bio.ifi;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import szte.mi.KI_Random;
import szte.mi.Move;
import szte.mi.Player;

import java.util.ArrayList;
import java.util.Random;

public class OthelloGui extends Application {
    Player p1;
    Player p2;
    Move lastMove;
    // Global score for both players
    private int scoreP1 = 0;
    private int scoreP2 = 0;

    String winnerOfLastRoundLableText = "";
    Button[][] gameBoardArray = new Button[8][8];
    Stage primaryStage; // needed for restart button
    OthelloGame localGame; // a local game that runs othello
    VBox infoLog; // Box for printing game state

    public class PlayButtonHandler implements EventHandler<ActionEvent> {
        Button button;
        int x;
        int y;

        // button handler
        public PlayButtonHandler(Button button, int x, int y) {
            this.button = button;
            this.x = x;
            this.y = y;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            // get correct piece
            Circle circle = new Circle(30); // Create a circle with radius 30
            circle.setFill(localGame.getCurrPlayer() == 1 ? Color.BLACK : Color.WHITE); // Set color based on player

            if (p1 == null && p2 == null) { // Handle if player vs player
                // if the move is valid, update button and local game
                if (localGame.makeMove(localGame.getCurrPlayer() % 2 != 0, this.x, this.y)) {
                    // make piece appear on button
                    this.button.setGraphic(circle);
                    // update gameBoardArray
                    updateGUI(localGame);
                    // show game state
                    updateInfoLog();
                    System.out.println(localGame.gameStatus().toString());
                }
            } else if (p1 == null && p2 != null) { // player vs KI
                Move last;
                if (localGame.makeMove(localGame.getCurrPlayer() % 2 != 0, this.x, this.y)) {
                    // move is valid, so we save it as last move for the opponent to process
                    last = new Move(this.x, this.y);
                    // make piece appear on button
                    this.button.setGraphic(circle);
                    // update gameBoardArray
                    updateGUI(localGame);
                    updateInfoLog();


                    // here player class two makes a move
                    Move randomMove = p2.nextMove(last, 8000, 8);
                    if (randomMove != null) {
                        last = new Move(randomMove.x, randomMove.y);
                        localGame.makeMove(false, last.x, last.y);
                        updateGUI(localGame);
                        updateInfoLog();
                    }
                }
            }
            /* this was for the optional task to watch two ki palyers
            else if (p1 != null && p2 != null){
                System.out.println("Move");
                makeKimoves();
            }
             */
        }
    }

    public void incrementScoreP1() {
        this.scoreP1++;
    }

    public void incrementScoreP2() {
        this.scoreP2++;
    }

    public void checkCurrGameForWinner() {
        GameStatus status = localGame.gameStatus();
        if (status == GameStatus.DRAW || status == GameStatus.PLAYER_1_WON || status == GameStatus.PLAYER_2_WON) {
            int currOrder = localGame.getOrder();
            int newOrder = currOrder == 1 ? 0 : 1; // flip order
            localGame = new OthelloGame(newOrder);
            purgeBoard();
            updateGUI(localGame);
            updateInfoLog();

        }
    }

    public int getScoreP1() {
        return scoreP1;
    }

    public int getScoreP2() {
        return scoreP2;
    }

    public void purgeBoard() {
        primaryStage.setScene(new Scene(initGame()));
        primaryStage.show();
        // for (int i = 0; i < gameBoardArray.length; i++) {
        //     for (int j = 0; j < gameBoardArray.length; j++) {
        //         Button button = new Button();
        //         button.setPrefSize(100, 100);
        //         gameBoardArray[j][i] = button;
        //         button.addEventHandler(ActionEvent.ACTION, new PlayButtonHandler(button, j, i));
        //     }
        // }
    }

    // method for printing the game state if won / draw
    public void updateInfoLog() {
        String header = "Othello ProPra";
        if (localGame.gameStatus() == GameStatus.PLAYER_1_WON) {
            incrementScoreP1();
            incrementScoreP1();
            winnerOfLastRoundLableText = "\nLAST ROUND\n\tBLACK WON\n→ PIECES BLACK: " + localGame.countPiecesOnBoard()[0] + "\n→ PIECES WHITE: " + localGame.countPiecesOnBoard()[1];
        } else if (localGame.gameStatus() == GameStatus.PLAYER_2_WON) {
            incrementScoreP2();
            incrementScoreP2();
            winnerOfLastRoundLableText = "\nLAST ROUND\n\tWHITE WON\n→ PIECES BLACK: " + localGame.countPiecesOnBoard()[0] + "\n→ PIECES WHITE: " + localGame.countPiecesOnBoard()[1];
        } else if (localGame.gameStatus() == GameStatus.DRAW) {
            winnerOfLastRoundLableText = "GAME DRAW";
            incrementScoreP1();
            incrementScoreP2();
        }

        String playerInfoText = "Next Piece to place:";
        String playerScoreText = "\nScore:\nBLACK: [" + getScoreP1() + "]" + "\nWHITE: [" + getScoreP2() + "]";

        Label headerLable = new Label(header);
        headerLable.setStyle("-fx-underline: true; -fx-font-weight: bold; -fx-text-fill: white");
        headerLable.setAlignment(Pos.CENTER);
        Label playerInfo = new Label(playerInfoText);
        playerInfo.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        Circle playerPiece = new Circle(30);
        playerPiece.setFill((localGame.getCurrPlayer() == 1) ? Color.BLACK : Color.WHITE);
        Label scoreLable = new Label(playerScoreText);
        scoreLable.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        Label winnerLable = new Label(winnerOfLastRoundLableText);
        winnerLable.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        infoLog.getChildren().clear();
        infoLog.getChildren().addAll(headerLable, playerInfo, playerPiece, scoreLable, winnerLable);
        checkCurrGameForWinner();
    }

    private void showScore(Label headerLable, Label playerInfo, Circle playerPiece, Label scoreLable, Label winnerLable) {
        Platform.runLater(() -> {
            infoLog.getChildren().clear();
            infoLog.getChildren().addAll(headerLable, playerInfo, playerPiece, scoreLable, winnerLable);
            checkCurrGameForWinner();
        });
    }

    // Initializes game scene
    public BorderPane initGame(){
        BorderPane gameScene = new BorderPane();
        GridPane gameGrid = new GridPane();
        Button restartGame = new Button("Restart");
        HBox buttonMenu = new HBox(10);
        VBox infoLog = new VBox();
        this.infoLog = infoLog;
        infoLog.setPrefWidth(300);
        infoLog.setPrefHeight(30);
        infoLog.setStyle("-fx-background-color: #00791b; -fx-border-color: #000000; -fx-border-width: 4px; -fx-border-radius: 1px;");
        infoLog.setSpacing(5);
        // init Buttons in gameBoardArray
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
        updateInfoLog();

        restartGame.setOnAction((ActionEvent r) -> {
            Stage stage = (Stage) restartGame.getScene().getWindow();
            stage.close();
            start(new Stage());
        });

        // return finished scene
        return gameScene;
    }

    // highlight possible moves in green
    public void showPossibleMoves(ArrayList<Move> moves){
        for (Move m : moves) {
            gameBoardArray[m.x][m.y].setStyle("-fx-background-color: #90ee90;-fx-border-color: black; -fx-border-width: 2;");
        }
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.localGame = new OthelloGame(0);
        // init primaryStage with start menu
        primaryStage.setScene(new Scene(initStartMenu()));
        primaryStage.show();
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

        // RadioButtons for choosing game scenario
        RadioButton rb1 = new RadioButton("Player vs. Player");
        RadioButton rb2 = new RadioButton("Player vs. Random");
        // RadioButton rb3 = new RadioButton("Random vs. Random");
        ToggleGroup tg = new ToggleGroup();
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        // rb3.setToggleGroup(tg);
        rb1.setSelected(true);
        rb1.setLayoutX(500);
        rb1.setLayoutY(10);
        rb2.setLayoutX(500);
        rb2.setLayoutY(30);
        // rb3.setLayoutX(500);
        // rb3.setLayoutY(30);
        menuStack.getChildren().addAll(rb1, rb2);


        // Start Button
        Button startGame = new Button("Start Game");
        startGame.setLayoutX(500);
        startGame.setLayoutY(80);
        startGame.setOnAction((ActionEvent e) -> {
            if (rb1.isSelected()) {
                playerVSplayer();
            }
            else if (rb2.isSelected()) {
                playerVSKI();
            }
        });
        menuStack.getChildren().add(startGame);
        menuStack.setAlignment(Pos.CENTER);
        startMenu.setCenter(menuStack);

        return startMenu;
    }

    // scenario if two human players want to play
    public void playerVSplayer(){
        primaryStage.setScene(new Scene(initGame()));
        updateGUI(this.localGame);
        showPossibleMoves( (ArrayList<Move>) this.localGame.getPossibleMoves(true));
        primaryStage.show();
    }
    public void playerVSKI(){
        KI_Random p2 = new KI_Random();
        this.p2 = p2;

        p2.init(1, 8000, new Random());

        OthelloGame o = new OthelloGame(0);
        primaryStage.setScene(new Scene(initGame()));
        updateGUI(this.localGame);
        showPossibleMoves( (ArrayList<Move>) this.localGame.getPossibleMoves(true));
        primaryStage.show();
    }

    public void kiVSki(){
        KI_Random p1 = new KI_Random();
        KI_Random p2 = new KI_Random();

        p1.init(1, 8000, new Random());
        p2.init(1, 8000, new Random());

        OthelloGame o = new OthelloGame(1);
        primaryStage.setScene(new Scene(initGame()));
        updateGUI(this.localGame);
        showPossibleMoves( (ArrayList<Move>) this.localGame.getPossibleMoves(true));

        primaryStage.show();

    }


    // Method for updating the gameGUI after each move
    public void updateGUI(OthelloGame game){
        int[][] gameBoard = game.getBoard();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                this.gameBoardArray[j][i].setStyle("-fx-background-color: #00791b;-fx-font-size: 20px;-fx-border-color: black; -fx-border-width: 2;");
                Circle circle = new Circle(30);
                switch (gameBoard[j][i]){
                    case (0):
                        break;
                    case(1):
                        circle.setFill(Color.BLACK);
                        this.gameBoardArray[j][i].setGraphic(circle);
                        break;
                    case(2):
                        circle.setFill(Color.WHITE);
                        this.gameBoardArray[j][i].setGraphic(circle);
                        break;
                }
            }
        }
        showPossibleMoves((ArrayList<Move>) localGame.getPossibleMoves(localGame.getCurrPlayer() % 2 != 0));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
