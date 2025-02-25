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

        GridPane BottomPane = new GridPane();
        BottomPane.setStyle("-fx-background-color: beige;");
        BottomPane.setPrefWidth(paneWidth);
        BottomPane.setPrefHeight(paneHeight);
        BottomPane.setBorder(Border);

        GridPane TopPane = new GridPane();
        TopPane.setStyle("-fx-background-color: beige;");
        TopPane.setPrefWidth(paneWidth);
        TopPane.setPrefHeight(paneHeight);
        TopPane.setBorder(Border);

        GridPane LeftPane = new GridPane();
        LeftPane.setStyle("-fx-background-color: beige;");
        LeftPane.setPrefWidth(sidePaneWidth);
        LeftPane.setPrefHeight(sidePaneHeight); // Shorter height so it fits
        LeftPane.setBorder(Border);

        GridPane RightPane = new GridPane();
        RightPane.setStyle("-fx-background-color: beige;");
        RightPane.setPrefWidth(sidePaneWidth);
        RightPane.setPrefHeight(sidePaneHeight); // Shorter height so it fits
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

           // System.out.println("Placed " + tile.getType() + " " + tile.getSpace() + " at position " + tile.getPosition() + "is Group: " + tile.getGroup());

            Label cellRect = new Label(tile.getSpace()); // Create a label for the tile normal rotation
            cellRect.setMinSize(150, 75);
            cellRect.setMaxSize(150, 75);
            cellRect.setPrefSize(150, 75);
            cellRect.setAlignment(Pos.CENTER);
            cellRect.setFont(new Font(14));
            cellRect.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));


            Label cellRect1 = new Label(tile.getSpace());
            cellRect1.setMinSize(75, 150);
            cellRect1.setMaxSize(75, 150);
            cellRect1.setPrefSize(75, 150);
            cellRect1.setAlignment(Pos.CENTER);
            cellRect1.setFont(new Font(14));
            cellRect1.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

            Label cellCorner = new Label(tile.getSpace()); // Create a label for the tile
            cellCorner.setMinSize(150, 150);
            cellCorner.setMaxSize(150, 150);
            cellCorner.setPrefSize(150, 150);
            cellCorner.setAlignment(Pos.CENTER);
            cellCorner.setFont(new Font(14));
            cellCorner.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

            Image imageTileRect = new Image(tile.getTileImage().getUrl(), 75, 150, false, false);
            Image imageTileCorner = new Image(tile.getTileImage().getUrl(), 150, 150, false, false);

            BackgroundImage WeSoStupidlol = new BackgroundImage(
                    imageTileRect,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            );

            BackgroundImage WeSoStupidlol1 = new BackgroundImage(
                    imageTileCorner,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            );

            if (bottomCol < topSize) {
                if (tile.getType().equals("Corner")) {
                    cellCorner.setBackground(new Background(WeSoStupidlol1));
                    Bottom.add(cellCorner, bottomCol, 0); // Fill BottomPane left to right
                } else {
                    cellRect1.setBackground(new Background(WeSoStupidlol));
                    Bottom.add(cellRect1, bottomCol, 0); // Fill BottomPane left to right
                }
                bottomCol++;
            } else if (rightRow >= 0) {
                cellRect.setBackground(new Background(WeSoStupidlol));
                Right.add(cellRect, 0, rightRow); // Fill RightPane bottom to top
                rightRow--;
            } else if (topCol >= 0) {
                if (tile.getType().equals("Corner")) {
                    cellCorner.setBackground(new Background(WeSoStupidlol1));
                    Top.add(cellCorner, topCol, 0); // Fill TopPane right to left
                } else {
                    cellRect1.setBackground(new Background(WeSoStupidlol));
                    Top.add(cellRect1, topCol, 0); // Fill TopPane right to left
                }
                topCol--;
            } else if (leftRow < sideSize) {
                cellRect.setBackground(new Background(WeSoStupidlol));
                Left.add(cellRect, 0, leftRow); // Fill LeftPane top to bottom
                leftRow++;
            }
        }
    }

    public void updateBoard(Stage PrimaryStage)
    {

    }
}

