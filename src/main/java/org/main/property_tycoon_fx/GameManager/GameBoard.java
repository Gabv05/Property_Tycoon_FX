package org.main.property_tycoon_fx.GameManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameBoard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SetUpBoard(primaryStage);
    }

    //  private void rollDiceAndMove() {
    //    int[] roll = player.rollDice(); // Get dice values
    //   dice1View.setImage(new Image(getClass().getResourceAsStream("/dice" + roll[0] + ".png")));
    //  dice2View.setImage(new Image(getClass().getResourceAsStream("/dice" + roll[1] + ".png")));

    // Move the player and check the tile -You can expand upon this to trigger events such as purchase, pay rent, etc. It may be better to do this outside the dice function in its own place though
    // player.move(roll[0] + roll[1]);
    // tileLabel.setText("Current Tile: " + player.getCurrentTile());
    // }


    private void SetUpBoard(Stage primaryStage) {
        double sceneWidth = 1920;
        double sceneHeight = 1080;



        // Create pane for board:
        StackPane boardPane = new StackPane();
        boardPane.setStyle("-fx-background-color: beige;");
        boardPane.setLayoutX(sceneWidth * 0.025); // Start 5% from the left
        boardPane.setLayoutY(sceneHeight * 0.05); // Slight top margin
        boardPane.setPrefWidth(sceneWidth * 0.7);
        boardPane.setPrefHeight(sceneHeight * 0.9);
        boardPane.setBorder(Border);


        // Create pane for Bank:
        Pane bankPane = new Pane();
        bankPane.setStyle("-fx-background-color: beige;");
        bankPane.setLayoutX(sceneWidth * 0.75);
        bankPane.setLayoutY(sceneHeight * 0.05);
        bankPane.setPrefWidth(sceneWidth * 0.225);
        bankPane.setPrefHeight(sceneHeight * 0.45);
        bankPane.setBorder(Border);

        // Create pane for Bank:
        Pane DicePane = new Pane();
        DicePane.setStyle("-fx-background-color: beige;");
        DicePane.setLayoutX(sceneWidth * 0.75);
        DicePane.setLayoutY(sceneHeight * 0.5);
        DicePane.setPrefWidth(sceneWidth * 0.225);
        DicePane.setPrefHeight(sceneHeight * 0.45);
        DicePane.setBorder(Border);

        // Image DiceImage = new Image("");

        Group mainGroup = new Group();
        mainGroup.getChildren().addAll(boardPane, bankPane, DicePane);

        FillTiles(boardPane);

        Scene gameScene = new Scene(mainGroup, sceneWidth, sceneHeight);
        primaryStage.setScene(gameScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void FillTiles(Pane BoardPane) {
        // Get Pane Types
        List<Pane> paneTypes = GetPaneTypes();
        Pane CornerPane = paneTypes.get(0);
        Pane RectPane = paneTypes.get(1);

        TileReader TReader = new TileReader();
        TReader.getTileDetails(); // Load the tile details from the Excel sheet

        LinkedList<Tile> tileList = TReader.returnTileList(); // Get the list of tiles from TileReader

        for (Tile tile : tileList) {
            System.out.println("Placed tile: " + tile.getSpace() + " at position " + tile.getPosition());
        }
    }


    public List<Pane> GetPaneTypes() {
        Pane CornerPane = new Pane();
        CornerPane.setScaleX(.25);
        CornerPane.setScaleY(.25);
        CornerPane.setBorder(Border);

        Pane RectPane = new Pane();
        RectPane.setScaleX(.175);
        RectPane.setScaleY(.25);
        RectPane.setBorder(Border);

        List<Pane> panes = new ArrayList<>();
        panes.add(CornerPane);
        panes.add(RectPane);

        return panes;
    }


    // Create Universal Border:
   public Border Border = new Border(new BorderStroke(Color.BLACK, // Border color
            BorderStrokeStyle.SOLID, // Solid line style
            CornerRadii.EMPTY, // No rounded corners
            new BorderWidths(2) // 2-pixel border width
    ));

}
