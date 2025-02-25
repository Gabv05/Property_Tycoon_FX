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
        GameBoard Gameboard = new GameBoard();
        ArrayList<Tile> GameTiles = new ArrayList<>();

        Gameboard.start(primaryStage);


        Player player1= new Player(1, "Marcel");
        for (int i = 0; i < 20; i++) {
            player1.move();
        }
    }
}
