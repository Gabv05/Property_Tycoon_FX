package org.main.property_tycoon_fx.GameManager;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        boardPane.setPrefWidth(sceneWidth * 0.5);
        boardPane.setPrefHeight(sceneHeight * 0.7);
        boardPane.setBorder(Border);

        double paneWidth = boardPane.getPrefWidth() * 0.190;  // Same width for Top and Bottom panes
        double paneHeight = boardPane.getPrefHeight() * 0.190; // Same height for all panes

        double sidePaneWidth = boardPane.getPrefWidth() * 0.14; // Keep the side panes narrower
        double sidePaneHeight = boardPane.getPrefHeight() * (1 - 2 * 0.175); // Adjust height so they don't overlap

        GridPane BottomPane = new GridPane();
        BottomPane.setStyle("-fx-background-color: beige;");
        BottomPane.setPrefWidth(paneWidth);
        BottomPane.setPrefHeight(paneHeight);
        BottomPane.setBorder(Border);
        BottomPane.setRotate(180);
        addGridBorders(BottomPane,1,1);

        GridPane TopPane = new GridPane();
        TopPane.setStyle("-fx-background-color: beige;");
        TopPane.setPrefWidth(paneWidth);
        TopPane.setPrefHeight(paneHeight);
        TopPane.setBorder(Border);
        TopPane.setRotate(180);
        addGridBorders(TopPane,1,1);

        GridPane LeftPane = new GridPane();
        LeftPane.setStyle("-fx-background-color: beige;");
        LeftPane.setPrefWidth(sidePaneWidth);
        LeftPane.setPrefHeight(sidePaneHeight); // Shorter height so it fits
        LeftPane.setBorder(Border);
        addGridBorders(LeftPane,1,1);

        GridPane RightPane = new GridPane();
        RightPane.setStyle("-fx-background-color: beige;");
        RightPane.setPrefWidth(sidePaneWidth);
        RightPane.setPrefHeight(sidePaneHeight); // Shorter height so it fits
        RightPane.setBorder(Border);
        addGridBorders(RightPane,1,1);


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


    public void FillTiles(Pane BoardPane) {

        TileReader TReader = new TileReader();
        TReader.getTileDetails(); // Load the tile details from the Excel sheet


        int maxPerRow = TReader.getNoOfTiles() / 4;

        // get available pane types
        List<Pane> paneTypes = GetPaneTypes();
        Pane CornerPane = paneTypes.get(0);
        Pane RectPane = paneTypes.get(1);

        //get the list of tiles
        LinkedList<Tile> tileList = TReader.returnTileList(); // Get the list of tiles from TileReader

        // Loop through each tile and assign it a position based on its characteristics
        for (Tile tile : tileList) {
            String PositionStr = Double.toString(tile.getPosition());

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


    private void addGridBorders(GridPane pane, int rows, int cols) {
        double paneWidth = pane.getPrefWidth();
        double paneHeight = pane.getPrefHeight();

        // Calculate the cell size as a percentage of the pane's size
        double cellWidth = paneWidth / cols;
        double cellHeight = paneHeight / rows;

        // Ensure the size remains close to 185 pixels when possible
        double scaleFactor = 185 / Math.min(paneWidth / cols, paneHeight / rows);
        cellWidth = Math.min(cellWidth, 185 * scaleFactor);
        cellHeight = Math.min(cellHeight, 185 * scaleFactor);

        // Set fixed size for columns
        for (int col = 0; col < cols; col++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.NEVER);
            colConstraints.setMinWidth(cellWidth);
            pane.getColumnConstraints().add(colConstraints);
        }

        // Set fixed size for rows
        for (int row = 0; row < rows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.NEVER);
            rowConstraints.setMinHeight(cellHeight);
            pane.getRowConstraints().add(rowConstraints);
        }

        // Add the cells
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Label cell = new Label(row + "," + col);
                cell.setMinSize(cellWidth, cellHeight);
                cell.setAlignment(Pos.CENTER);
                cell.setFont(new Font(14));
                cell.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
                cell.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, null)));

                pane.add(cell, col, row);
            }
        }
    }



}
