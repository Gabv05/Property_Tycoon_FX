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
        BorderPane boardPane = new BorderPane();
        boardPane.setStyle("-fx-background-color: beige;");
        boardPane.setLayoutX(sceneWidth * 0.025); // Start 5% from the left
        boardPane.setLayoutY(sceneHeight * 0.05); // Slight top margin
        boardPane.setPrefWidth(sceneWidth * 0.7);
        boardPane.setPrefHeight(sceneHeight * 0.9);
        boardPane.setBorder(Border);

        GridPane BottomPane = new GridPane();
        BottomPane.setStyle("-fx-background-color: beige;");
        BottomPane.setPrefWidth(boardPane.getPrefWidth()); // Full width for BottomPane
        BottomPane.setPrefHeight(boardPane.getPrefHeight() * 0.175); // Height for BottomPane
        BottomPane.setBorder(Border);

        GridPane TopPane = new GridPane();
        TopPane.setStyle("-fx-background-color: beige;");
        TopPane.setPrefWidth(boardPane.getPrefWidth()); // Full width for TopPane
        TopPane.setPrefHeight(boardPane.getPrefHeight() * 0.175); // Height for TopPane
        TopPane.setBorder(Border);


        GridPane LeftPane = new GridPane();
        LeftPane.setStyle("-fx-background-color: beige;");
        LeftPane.setPrefWidth(boardPane.getPrefWidth() * 0.15); // Width for LeftPane
        LeftPane.setPrefHeight(boardPane.getPrefHeight()); // Full height for LeftPane
        LeftPane.setBorder(Border);


        GridPane RightPane = new GridPane();
        RightPane.setStyle("-fx-background-color: beige;");
        RightPane.setPrefWidth(boardPane.getPrefWidth() * 0.15); // Width for RightPane
        RightPane.setPrefHeight(boardPane.getPrefHeight()); // Full height for RightPane
        RightPane.setBorder(Border);


        // Set side panes to be inside the boardPane using BorderPane
        boardPane.setBottom(BottomPane);
        boardPane.setTop(TopPane);
        boardPane.setLeft(LeftPane);
        boardPane.setRight(RightPane);

        // Create pane for Bank (inside the boardPane):
        Pane bankPane = new Pane();
        bankPane.setStyle("-fx-background-color: beige;");
        bankPane.setLayoutX(sceneWidth * 0.75);
        bankPane.setLayoutY(sceneHeight * 0.05);
        bankPane.setPrefWidth(sceneWidth * 0.225);
        bankPane.setPrefHeight(sceneHeight * 0.45);
        bankPane.setBorder(Border);

        // Create pane for Dice (inside the boardPane):
        Pane DicePane = new Pane();
        DicePane.setStyle("-fx-background-color: beige;");
        DicePane.setLayoutX(sceneWidth * 0.75);
        DicePane.setLayoutY(sceneHeight * 0.5);
        DicePane.setPrefWidth(sceneWidth * 0.225);
        DicePane.setPrefHeight(sceneHeight * 0.45);
        DicePane.setBorder(Border);

        // Create the main group to hold everything
        Group mainGroup = new Group();
        mainGroup.getChildren().addAll(boardPane, bankPane, DicePane);

        // Call FillTiles method to populate the board
        FillTiles(boardPane);

        // Create the scene and set it to the primary stage
        Scene gameScene = new Scene(mainGroup, sceneWidth, sceneHeight);
        primaryStage.setScene(gameScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public void FillTiles(Pane BoardPane) {
        // Initialize the TileReader and load tile details
        TileReader TReader = new TileReader();
        TReader.getTileDetails(); // Load the tile details from the Excel sheet

        // Set max number of tiles per row
        int maxPerRow = TReader.getNoOfTiles() / 4;

        // get available pane types
        List<Pane> paneTypes = GetPaneTypes();
        Pane CornerPane = paneTypes.get(0);
        Pane RectPane = paneTypes.get(1);

        //get the list of tiles
        LinkedList<Tile> tileList = TReader.returnTileList(); // Get the list of tiles from TileReader

        // Loop through each tile and assign it a position based on its characteristics
        for (int i = 0; i < tileList.size(); i++) {
            Tile tile = tileList.get(i);
            String PositionStr  = Double.toString(tile.getPosition());

            //check if the tile ends in 1 or is just 1, and assign to CornerPane; else assign to RectPane
            if (PositionStr.endsWith("1.0") || tile.getPosition() == 1) {
                // Place tile in CornerPane

                System.out.println("Placed corner tile: " + tile.getSpace() + " at position " + tile.getPosition());
            } else {
                // Place tile in RectPane

                System.out.println("Placed rectangle tile: " + tile.getSpace() + " at position " + tile.getPosition());
            }
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
            BorderStrokeStyle.DASHED, // Solid line style
            CornerRadii.EMPTY, // No rounded corners
            new BorderWidths(2) // 2-pixel border width
    ));

}
