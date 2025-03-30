package org.main.property_tycoon_fx.GameManager;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameManager extends Application {
    private ArrayList<Player> players;
    private int playerID;
    private int noOfPlayers;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //creating the player-related variables
        players = new ArrayList<>();
        playerID = 1;

        //launching the card reader
        CardReader CardReader = new CardReader();
        CardReader.getCardDetails();

        GameBoard Gameboard = new GameBoard();
      // ArrayList<Tile> GameTiles = new ArrayList<>();

        Gameboard.start(primaryStage);

    // test roll
        Player player1= new Player(playerID, "Marcel", 1000, Gameboard);
        players.add(player1);
        playerID++; //increment the playerID every time a new player is created and add it to the array
        Player player2= new Player(playerID, "Chloe", 600, Gameboard);
        players.add(player2);
        playerID++;

        Gameboard.addPlayerImage(player1, 200);  // add player to boardPane
        Gameboard.addPlayerImage(player2, 300);

        Bank bank = new Bank();
        bank.holdAuction(new Tile(45, "Test", "TestG", null, true, 30, null, 0), players);

        // test
        player1.buyTileProperty(player1.getTilePosition());
    }
}
