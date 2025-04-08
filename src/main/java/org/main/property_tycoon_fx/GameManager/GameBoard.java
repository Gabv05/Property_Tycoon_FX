package org.main.property_tycoon_fx.GameManager;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.T;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.*;

public class GameBoard extends Application {
    private Dice dice;
    private Pane[] playerTabs;
    private EndTurnButton endTurnButton;
    private BorderPane boardPane = new BorderPane();

    GridPane BottomPane = new GridPane();
    GridPane TopPane = new GridPane();
    GridPane LeftPane = new GridPane();
    GridPane RightPane = new GridPane();

    Group mainGroup = new Group();

    public static List<String> availableTokens = new ArrayList<>(Arrays.asList(
            "boot",
            "ship",
            "smartphone",
            "hatstand",
            "cat",
            "iron"
    ));     // will add the other two tokens once finished images

    public String giveToken(){
        // get random token name
        int randomIndex = new Random().nextInt(availableTokens.size());
        System.out.println("index: " + randomIndex + " taken token = " + availableTokens.get(randomIndex));
        String token = availableTokens.get(randomIndex);
        availableTokens.remove(randomIndex);
        System.out.println("arraylist = " + availableTokens);

        return token;
    }

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


        BottomPane.setStyle("-fx-background-color: beige;");
        BottomPane.setPrefWidth(paneWidth);
        BottomPane.setPrefHeight(paneHeight);
        BottomPane.setBorder(Border);


        TopPane.setStyle("-fx-background-color: beige;");
        TopPane.setPrefWidth(paneWidth);
        TopPane.setPrefHeight(paneHeight);
        TopPane.setBorder(Border);


        LeftPane.setStyle("-fx-background-color: beige;");
        LeftPane.setPrefWidth(sidePaneWidth);
        LeftPane.setPrefHeight(sidePaneHeight); // Shorter height so it fits
        LeftPane.setBorder(Border);


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

        // create button objects and show image
        //
        dice = new Dice(DicePane, sceneWidth, sceneHeight);
        //setDice(dice);
        endTurnButton = new EndTurnButton(DicePane, sceneWidth, sceneHeight);

        // player tab panes
        playerTabs = new Pane[5];    // create total of 5 panes as that is max amount of players
        double playerTabW = sceneWidth * 0.17;
        double playerTabH = sceneHeight * 0.14;
        // create each pane and assign to position in array
        for (int tabIndex = 0; tabIndex < 5; tabIndex++) {
            playerTabs[tabIndex] = new Pane();
            playerTabs[tabIndex].setPrefWidth(playerTabW);
            playerTabs[tabIndex].setPrefHeight(playerTabH);
        }

        // group all tabs into layout YBox to hold all player tabs vertically
        VBox tabLayout = new VBox(15);
        tabLayout.getChildren().addAll(playerTabs);   // add all player tab panes to layout

        // create pane to specifically position tabLayout
        Pane positionPlayerTabs = new Pane();
        positionPlayerTabs.setLayoutX(sceneWidth * 0.56);
        positionPlayerTabs.setLayoutY(sceneHeight * 0.1);
        positionPlayerTabs.getChildren().addAll(tabLayout);


        // Create the main group to hold everything

        mainGroup.getChildren().addAll(boardPane, bankPane, DicePane, positionPlayerTabs);

        // Call FillTiles method to populate the board
        FillTiles(TopPane, BottomPane, LeftPane, RightPane);

        // Create the scene and set it to the primary stage
        Scene gameScene = new Scene(mainGroup, sceneWidth, sceneHeight);
        primaryStage.setScene(gameScene);
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    // add player ImageView to boardPane
    public void addPlayerImage(Player player, double x, double y){
        ImageView imageView = player.playerImageView();
        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
       mainGroup.getChildren().add(imageView);
    }

    // add players tab image to board
    public void addPlayerTab(Player player){
        // add image
        ImageView imageView = player.playerTabImageView();
        playerTabs[player.getPlayerID() - 1].getChildren().add(imageView);
        // add text
        // name
        Text name = new Text(player.getPlayerName());
        name.setFont(Font.font("Monospaced", FontWeight.BOLD,18));
        name.setFill(Color.DARKSLATEGRAY);
        name.setLayoutX(130);
        name.setLayoutY(50);
        // money
        Text money = new Text("£" + player.getMoney());
        money.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD,16));
        money.setFill(Color.LIGHTGREY);
        money.setLayoutX(130);
        money.setLayoutY(80);
        // player position
        Text position = new Text("Position: " + player.getTilePosition());
        position.setFont(Font.font("Monospaced", FontWeight.BOLD,16));
        position.setFill(Color.LIGHTGREY);
        position.setLayoutX(130);
        position.setLayoutY(100);
        // add text to player tab
        playerTabs[player.getPlayerID() - 1].getChildren().addAll(name, money, position);
    }

    public Dice getDice(){
        return dice;
    }

    public EndTurnButton getEndTurnButton(){return endTurnButton;}


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

    public void passPlayerPosTile(Player player, int pPos) {
        TileReader TReader = new TileReader();
        TReader.getTileDetails(); // Load tile details from Excel
        LinkedList<Tile> tileList = TReader.returnTileList(); // Get tiles from TileReader

        Label MoveLabel = null;
        Tile Requested = tileList.get(pPos);
        String RequestedName = Requested.getSpace();
        double RequestedPos = Requested.getPosition();


        // Search in all panes
        MoveLabel = findLabelInPane(LeftPane, RequestedName,pPos);
        if (MoveLabel == null) MoveLabel = findLabelInPane(TopPane, RequestedName,pPos);
        if (MoveLabel == null) MoveLabel = findLabelInPane(RightPane, RequestedName,pPos);
        if (MoveLabel == null) MoveLabel = findLabelInPane(BottomPane, RequestedName,pPos);

        if (MoveLabel != null) {
            double offsetX = MoveLabel.getLayoutX() + MoveLabel.getParent().getLayoutX() + boardPane.getLayoutX();
            double offsetY = MoveLabel.getLayoutY() + MoveLabel.getParent().getLayoutY() + boardPane.getLayoutY();
            MoveToken(player, offsetX, offsetY);
        }
    }


    private Label findLabelInPane(GridPane pane, String requestedName,int PlayerPos) {
        for (Node node : pane.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                if (label.getText().equalsIgnoreCase(requestedName)) {
                    return label;
                }
            }
        }
        return null;
    }

    public void MoveToken(Player player, Double posX, Double posY) {
        ImageView playerToken = player.getPlayerTokenImage();
        playerToken.setTranslateX(posX);
        playerToken.setTranslateY(posY);
        playerToken.toFront();

        System.out.println("Moving to: " + playerToken.getTranslateX() + ", " + playerToken.getTranslateY());
    }



    public void updateBoard(Stage PrimaryStage, Player player)
    {

    }
}

