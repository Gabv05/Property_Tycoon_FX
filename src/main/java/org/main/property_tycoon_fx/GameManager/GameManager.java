package org.main.property_tycoon_fx.GameManager;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.util.ArrayList;
import java.util.LinkedList;

public class GameManager extends Application {
    GameBoard Gameboard = new GameBoard();
    Dice dice;
    EndTurnButton endTurnButton;
    private Player[] players;
    TurnManager turnManager = new TurnManager(players);
    Button rollButton;
    Button endTurnBtn;

    public int StartposX;
    public int StartposY;

    public int getStartposX() {
        return StartposX;
    }

    public void setStartposX(int startposX) {
        StartposX = startposX;
    }

    public int getStartposY() {
        return StartposY;
    }

    public void setStartposY(int startposY) {
        StartposY = startposY;
    }

    public static void main(String[] args) {
        launch(args);
    }

    // create array of 5 players and return array for turn management
    public Player[] createPlayers(int count, int goPosX, int goPosY) {
        // create array which can hold 5 players
        players = new Player[count];

        // create players
        players[0] = new Player(1, "Player1", 1500, Gameboard);
        players[1] = new Player(2, "Player2", 1500, Gameboard);
        players[2] = new Player(3, "Player3", 1500, Gameboard);
        players[3] = new Player(4, "Player4", 1500, Gameboard);
        players[4] = new Player(5, "Player5", 1500, Gameboard);

        // add players to gameboard
        for (int index = 0; index < players.length; index++) {
            Gameboard.addPlayerImage(players[index], goPosX, goPosY);
            Gameboard.addPlayerTab(players[index]);
        }

        // pass array of players to turnManager to be set
        turnManager.setPlayerArray(players);

        return players;
    }

    @Override
    public void start(Stage primaryStage) {
        TileReader TReader = new TileReader();
        TReader.getTileDetails();
        LinkedList<Tile> tileList = TReader.returnTileList();

        Gameboard.Gamemanager = this;

        Gameboard.start(primaryStage);

        // get objects from gameboard to be used for buttons
        dice = Gameboard.getDice();
        endTurnButton = Gameboard.getEndTurnButton();

        CardReader CardReader = new CardReader();
        CardReader.getCardDetails();

        // buttons //
        //
        // roll button:
        rollButton = dice.getRollButton();
        rollButton.setOnAction(actionEvent -> {
            System.out.println("Roll Button Clicked by " + turnManager.getCurrentPlayer().getPlayerName());
            turnManager.currentPlayerRoll(turnManager.getCurrentPlayer());
            turnManager.getCurrentPlayer().getTilePosition();
            Gameboard.passPlayerPosTile(turnManager.getCurrentPlayer(), turnManager.getCurrentPlayer().getTilePosition());

        });
        // end turn button:
        endTurnBtn = endTurnButton.getEndTurnBtn();
        endTurnBtn.setOnAction(actionEvent -> {
            System.out.println("End Button Clicked by " + turnManager.getCurrentPlayer().getPlayerName());
            turnManager.nextplayer();
            System.out.println("Current turn is for:  " + turnManager.getCurrentPlayer().getPlayerName());
            dice.resetRolls(); // reset rolls for next player
            //rollButton.setDisable(false);
        });

        // create 5 players
        createPlayers(5, getStartposX(), getStartposY());
        System.out.println("X is: " + getStartposX() + "Y is: " + getStartposY());

        // test
        //player1.buyTileProperty(player1.getTilePosition());


// lauren was here

    }
}
