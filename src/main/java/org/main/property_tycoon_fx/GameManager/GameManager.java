package org.main.property_tycoon_fx.GameManager;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

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
        Player player1= new Player(1, "Marcel", 0);
        for (int i = 0; i < 10; i++) {
            player1.move();

        }

        // test
        player1.buyTileProperty(player1.getTilePosition());


// lauren was here

    }
}
