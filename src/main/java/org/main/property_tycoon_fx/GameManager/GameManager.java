package org.main.property_tycoon_fx.GameManager;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameManager extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CardReader CardReader = new CardReader();
        CardReader.getCardDetails();

        GameBoard Gameboard = new GameBoard();
      // ArrayList<Tile> GameTiles = new ArrayList<>();

        Gameboard.start(primaryStage);

    // test roll
        Player player1= new Player(1, "Marcel", 0, Gameboard);
        Player player2= new Player(2, "Chloe", 1, Gameboard);
        Gameboard.addPlayerImage(player1, 200);  // add player to boardPane
        Gameboard.addPlayerImage(player2, 300);
        //for (int i = 0; i < 10; i++) {
            //player1.move();
        //}
        //player1.move();

        // test
        player1.buyTileProperty(player1.getTilePosition());


// lauren was here

    }
}
