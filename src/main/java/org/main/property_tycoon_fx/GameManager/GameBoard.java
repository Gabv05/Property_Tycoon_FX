package org.main.property_tycoon_fx.GameManager;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        double sidePaneWidth = boardPane.getPrefWidth() * 0.160; // Keep the side panes narrower
        double sidePaneHeight = boardPane.getPrefHeight() * (1 - 2 * 0.175);

        // Create the Bottom pane with fixed size
        GridPane BottomPane = new GridPane();
        BottomPane.setStyle("-fx-background-color: beige;");
        BottomPane.setPrefWidth(paneWidth);
        BottomPane.setPrefHeight(paneHeight);
        BottomPane.setMaxWidth(paneWidth); // Ensuring the width doesn't exceed
        BottomPane.setMaxHeight(paneHeight); // Ensuring the height doesn't exceed
        BottomPane.setBorder(Border);
        BottomPane.setHgap(0);
        BottomPane.setVgap(0);

        GridPane TopPane = new GridPane();
        TopPane.setStyle("-fx-background-color: beige;");
        TopPane.setPrefWidth(paneWidth);
        TopPane.setPrefHeight(paneHeight);
        TopPane.setMaxWidth(paneWidth);
        TopPane.setMaxHeight(paneHeight);
        TopPane.setBorder(Border);
        TopPane.setHgap(0);
        TopPane.setVgap(0);

        GridPane LeftPane = new GridPane();
        LeftPane.setStyle("-fx-background-color: beige;");
        LeftPane.setPrefWidth(sidePaneWidth);
        LeftPane.setPrefHeight(sidePaneHeight);
        LeftPane.setMaxWidth(sidePaneWidth); // Fixed width for Left Pane
        LeftPane.setMaxHeight(sidePaneHeight); // Fixed height for Left Pane
        LeftPane.setBorder(Border);
        LeftPane.setHgap(0);
        LeftPane.setVgap(0);

        GridPane RightPane = new GridPane();
        RightPane.setStyle("-fx-background-color: beige;");
        RightPane.setPrefWidth(sidePaneWidth);
        RightPane.setPrefHeight(sidePaneHeight);
        RightPane.setMaxWidth(sidePaneWidth); // Fixed width for Right Pane
        RightPane.setMaxHeight(sidePaneHeight); // Fixed height for Right Pane
        RightPane.setBorder(Border);
        RightPane.setHgap(0);
        RightPane.setVgap(0);

        boardPane.setBottom(BottomPane);
        boardPane.setTop(TopPane);
        boardPane.setLeft(LeftPane);
        boardPane.setRight(RightPane);

        Pane bankPane = new Pane();
        bankPane.setStyle("-fx-background-color: beige;");
        bankPane.setLayoutX(sceneWidth * 0.75);
        bankPane.setLayoutY(sceneHeight * 0.05);
        bankPane.setPrefWidth(sceneWidth * 0.225);
        bankPane.setPrefHeight(sceneHeight * 0.45);
        bankPane.setBorder(Border);

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

        FillTiles(TopPane, BottomPane, LeftPane, RightPane);

        // Create the scene and set it to the primary stage
        Scene gameScene = new Scene(mainGroup, sceneWidth, sceneHeight);
        primaryStage.setScene(gameScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    // Create Universal Border:
    public Border Border = new Border(new BorderStroke(Color.BLACK, // Border color
            BorderStrokeStyle.DASHED, // Solid line style
            CornerRadii.EMPTY, // No rounded corners
            new BorderWidths(2) // 2-pixel border width
    ));


    public void FillTiles(GridPane Top, GridPane Bottom, GridPane Left, GridPane Right) {
        TileReader TReader = new TileReader();
        TReader.getTileDetails(); // Load tile details from Excel

        int topSize = 11;
        int sideSize = 9;

        LinkedList<Tile> tileList = TReader.returnTileList(); // Get tiles from TileReader

        // Track positions for each pane
        int bottomCol = 0;
        int rightRow = sideSize - 1;
        int topCol = topSize - 1;
        int leftRow = 0;

        for (Tile tile : tileList) {
            String PositionStr = Double.toString(tile.getPosition());

            boolean isCorner = PositionStr.endsWith("1.0") || tile.getPosition() == 1;
            tile.setType(isCorner ? "Corner" : "Rectangle");

            System.out.println("Placed " + tile.getType() + " " + tile.getSpace() + " at position " + tile.getPosition() + "is Group: " + tile.getGroup());

            ImageView cellRectTop = new ImageView(tile.getTileImage()); // Create a label for the tile flipped 180 degrees
            cellRectTop.setFitWidth(75);
            cellRectTop.setFitHeight(150);
            cellRectTop.setRotate(180);

            ImageView cellRectBottom = new ImageView(tile.getTileImage()); // Create a label for the tile flipped 180 degrees
            cellRectBottom.setFitWidth(75);
            cellRectBottom.setFitHeight(150);

            ImageView cellRectLeft = new ImageView(tile.getTileImage()); // Create a label for the tile flipped 180 degrees
            cellRectLeft.setFitWidth(75);
            cellRectLeft.setFitHeight(150);
            cellRectLeft.setRotate(90);

            ImageView cellRectRight = new ImageView(tile.getTileImage()); // Create a label for the tile flipped 180 degrees
            cellRectRight.setFitWidth(75);
            cellRectRight.setFitHeight(150);
            cellRectRight.setRotate(270);


            Label cellCorner = new Label(tile.getSpace()); // Create a label for the tile
            cellCorner.setMinSize(150, 150);
            cellCorner.setMaxSize(150, 150);
            cellCorner.setPrefSize(150, 150);
            cellCorner.setAlignment(Pos.CENTER);
            cellCorner.setFont(new Font(14));
            cellCorner.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
            cellCorner.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, null)));



            if (bottomCol < topSize) {
                if (tile.getType().equals("Corner")) {
                    Bottom.add(cellCorner, bottomCol, 0); // Fill BottomPane left to right
                } else {
                    Bottom.add(cellRectBottom, bottomCol, 0); // Fill BottomPane left to right
                }
                bottomCol++;
            } else if (rightRow >= 0) {
                Right.add(cellRectRight, 0, rightRow); // Fill RightPane bottom to top
                rightRow--;
            } else if (topCol >= 0) {
                if (tile.getType().equals("Corner")) {
                    Top.add(cellCorner, topCol, 0); // Fill TopPane right to left
                } else {
                    Top.add(cellRectTop, topCol, 0); // Fill TopPane right to left
                }
                topCol--;
            } else if (leftRow < sideSize) {
                Left.add(cellRectLeft, 0, leftRow); // Fill LeftPane top to bottom
                leftRow++;
            }
        }
    }


}
