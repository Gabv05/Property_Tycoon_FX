package org.main.property_tycoon_fx.GameManager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;

public class GameBoard extends Application {
    public GameManager Gamemanager;
    private LinkedList<Tile> tileList;
    private Dice dice;
    private Pane[] playerTabs;
    private EndTurnButton endTurnButton;
    private BorderPane boardPane = new BorderPane();

    GridPane BottomPane = new GridPane();
    GridPane TopPane = new GridPane();
    GridPane LeftPane = new GridPane();
    GridPane RightPane = new GridPane();

    // We will add all our game elements to this group.
    Group mainGroup = new Group();

    public static List<String> availableTokens = new ArrayList<>(Arrays.asList(
            "boot",
            "ship",
            "smartphone",
            "hatstand",
            "cat",
            "iron"
    ));

    public String giveToken() {
        int randomIndex = new Random().nextInt(availableTokens.size());
        String token = availableTokens.get(randomIndex);
        availableTokens.remove(randomIndex);
        return token;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        SetUpBoard(primaryStage);
        StartBoard(primaryStage);
    }

    // In our design we assume a 1920x1080 layout. All coordinates and sizes are relative to these numbers as this is my monitor size at home
    private void SetUpBoard(Stage primaryStage) {
        final double designWidth = 1920;
        final double designHeight = 1080;

        boardPane.setStyle("-fx-background-color: beige;");
        boardPane.setLayoutX(designWidth * 0.025);
        boardPane.setLayoutY(designHeight * 0.05);
        boardPane.setPrefWidth(designWidth * 0.5);
        boardPane.setPrefHeight(designHeight * 0.7);

        double paneWidth = boardPane.getPrefWidth() * 0.190;
        double paneHeight = boardPane.getPrefHeight() * 0.190;

        double sidePaneWidth = boardPane.getPrefWidth() * 0.160;
        double sidePaneHeight = boardPane.getPrefHeight() * (1 - 2 * 0.175);

        BottomPane.setStyle("-fx-background-color: beige;");
        BottomPane.setPrefWidth(paneWidth);
        BottomPane.setPrefHeight(paneHeight);

        TopPane.setStyle("-fx-background-color: beige;");
        TopPane.setPrefWidth(paneWidth);
        TopPane.setPrefHeight(paneHeight);

        LeftPane.setStyle("-fx-background-color: beige;");
        LeftPane.setPrefWidth(sidePaneWidth);
        LeftPane.setPrefHeight(sidePaneHeight);

        RightPane.setStyle("-fx-background-color: beige;");
        RightPane.setPrefWidth(sidePaneWidth);
        RightPane.setPrefHeight(sidePaneHeight);

        // Position the side panes in the boardPane.
        boardPane.setBottom(BottomPane);
        boardPane.setTop(TopPane);
        boardPane.setLeft(LeftPane);
        boardPane.setRight(RightPane);


        // Create pane for Bank:
        Pane bankPane = new Pane();
        bankPane.setStyle("-fx-background-color: beige;");
        bankPane.setLayoutX(designWidth * 0.75);
        bankPane.setLayoutY(designHeight * 0.05);
        bankPane.setPrefWidth(designWidth * 0.225);
        bankPane.setPrefHeight(designHeight * 0.45);

        // Create pane for Dice:
        Pane DicePane = new Pane();
        DicePane.setStyle("-fx-background-color: beige;");
        DicePane.setLayoutX(designWidth * 0.75);
        DicePane.setLayoutY(designHeight * 0.5);
        DicePane.setPrefWidth(designWidth * 0.225);
        DicePane.setPrefHeight(designHeight * 0.45);

        // Create button objects and show image
        dice = new Dice(DicePane, designWidth, designHeight);
        endTurnButton = new EndTurnButton(DicePane, designWidth, designHeight);

        // Create five player tab panes:
        playerTabs = new Pane[5];
        double playerTabW = designWidth * 0.17;
        double playerTabH = designHeight * 0.14;
        for (int tabIndex = 0; tabIndex < 5; tabIndex++) {
            playerTabs[tabIndex] = new Pane();
            playerTabs[tabIndex].setPrefWidth(playerTabW);
            playerTabs[tabIndex].setPrefHeight(playerTabH);
        }

        // Add the player tabs to a vertical layout container:
        VBox tabLayout = new VBox(15);
        tabLayout.getChildren().addAll(playerTabs);

        // Position the player tabs:
        Pane positionPlayerTabs = new Pane();
        positionPlayerTabs.setLayoutX(designWidth * 0.56);
        positionPlayerTabs.setLayoutY(designHeight * 0.1);
        positionPlayerTabs.getChildren().addAll(tabLayout);

        // Add all primary game elements to our mainGroup:
        mainGroup.getChildren().addAll(boardPane, bankPane, DicePane, positionPlayerTabs);

        // Populate the board’s tiles.
        FillTiles(TopPane, BottomPane, LeftPane, RightPane);

        // Wrap mainGroup in a StackPane for auto-centering
        StackPane root = new StackPane();
        root.getChildren().add(mainGroup);

        // Calculate the scale factor based on the actual screen size

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Compute the scale factor to fit the design inside the screen.
        double scaleX = screenWidth / designWidth;
        double scaleY = screenHeight / designHeight;
        double scale = Math.min(scaleX, scaleY);

        // Apply the scale transformation.
        mainGroup.setScaleX(scale);
        mainGroup.setScaleY(scale);



        Scene gameScene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(gameScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public void addPlayerImage(Player player, double x, double y) {
        ImageView imageView = player.playerImageView();
        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        mainGroup.getChildren().add(imageView);
    }

    public void addPlayerTab(Player player) {
        ImageView imageView = player.playerTabImageView();
        playerTabs[player.getPlayerID() - 1].getChildren().add(imageView);

        Text name = new Text(player.getPlayerName());
        name.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
        name.setFill(Color.DARKSLATEGRAY);
        name.setLayoutX(130);
        name.setLayoutY(50);

        Text money = new Text("£" + player.getMoney());
        money.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD, 16));
        money.setFill(Color.LIGHTGREY);
        money.setLayoutX(130);
        money.setLayoutY(80);

        Text position = new Text("Position: " + player.getTilePosition());
        position.setFont(Font.font("Monospaced", FontWeight.BOLD, 16));
        position.setFill(Color.LIGHTGREY);
        position.setLayoutX(130);
        position.setLayoutY(100);

        playerTabs[player.getPlayerID() - 1].getChildren().addAll(name, money, position);
    }

    private List<Player> players = new ArrayList<>();

    public List<Player> getAllPlayers() {
        return players;
    }

    public Player getPlayerByID(int id) {
        for (Player player : Gamemanager.getAllPlayers()) {
            if (player.getPlayerID() == id) {
                return player;
            }
        }
        return null;
    }


    public Dice getDice() {
        return dice;
    }

    public EndTurnButton getEndTurnButton() {
        return endTurnButton;
    }

    public void FillTiles(GridPane Top, GridPane Bottom, GridPane Left, GridPane Right) {
        TileReader tileReader = new TileReader();
        tileReader.getTileDetails();

        int topSize = 11;
        int sideSize = 9;

        tileList = tileReader.returnTileList();

        int bottomCol = 0;
        int rightRow = sideSize - 1;
        int topCol = topSize - 1;
        int leftRow = 0;

        for (Tile tile : tileList) {
            String positionStr = Double.toString(tile.getPosition());

            boolean isCorner = positionStr.endsWith("1.0") || tile.getPosition() == 1;
            tile.setType(isCorner ? "Corner" : "Rectangle");

            Label rectLabel = new Label(tile.getSpace());
            rectLabel.setMinSize(150, 75);
            rectLabel.setMaxSize(150, 75);
            rectLabel.setPrefSize(150, 75);
            rectLabel.setAlignment(Pos.CENTER);
            rectLabel.setFont(new Font(14));
            rectLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
            rectLabel.setStyle("-fx-text-fill: transparent;");

            Label rotatedRectLabel = new Label(tile.getSpace());
            rotatedRectLabel.setMinSize(75, 150);
            rotatedRectLabel.setMaxSize(75, 150);
            rotatedRectLabel.setPrefSize(75, 150);
            rotatedRectLabel.setAlignment(Pos.CENTER);
            rotatedRectLabel.setFont(new Font(14));
            rotatedRectLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
            rotatedRectLabel.setStyle("-fx-text-fill: transparent;");

            Label cornerLabel = new Label(tile.getSpace());
            cornerLabel.setMinSize(150, 150);
            cornerLabel.setMaxSize(150, 150);
            cornerLabel.setPrefSize(150, 150);
            cornerLabel.setAlignment(Pos.CENTER);
            cornerLabel.setFont(new Font(14));
            cornerLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
            cornerLabel.setStyle("-fx-text-fill: transparent;");

            Image rectangleImage = new Image(tile.getTileImage().getUrl(), 75, 150, false, false);
            Image cornerImage = new Image(tile.getTileImage().getUrl(), 150, 150, false, false);

            BackgroundImage rectangleBackground = new BackgroundImage(
                    rectangleImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            );

            ImageView rotated90View = new ImageView(rectangleImage);
            rotated90View.setRotate(90);
            WritableImage rotatedImage90 = rotated90View.snapshot(null, null);

            ImageView rotated270View = new ImageView(rectangleImage);
            rotated270View.setRotate(270);
            WritableImage rotatedImage270 = rotated270View.snapshot(null, null);

            ImageView rotated180View = new ImageView(rectangleImage);
            rotated180View.setRotate(180);
            WritableImage rotatedImage180 = rotated180View.snapshot(null, null);

            BackgroundImage cornerBackground = new BackgroundImage(
                    cornerImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            );

            BackgroundImage rotatedBackground90 = new BackgroundImage(
                    rotatedImage90,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            );

            BackgroundImage rotatedBackground270 = new BackgroundImage(
                    rotatedImage270,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            );

            BackgroundImage rotatedBackground180 = new BackgroundImage(
                    rotatedImage180,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            );

            // Fill grids based on positions
            if (bottomCol < topSize) {
                if (tile.getType().equals("Corner")) {
                    cornerLabel.setBackground(new Background(cornerBackground));
                    Bottom.add(cornerLabel, bottomCol, 0);
                } else {
                    rotatedRectLabel.setBackground(new Background(rectangleBackground));
                    Bottom.add(rotatedRectLabel, bottomCol, 0);
                }
                bottomCol++;
            } else if (rightRow >= 0) {
                rectLabel.setBackground(new Background(rotatedBackground270));
                Right.add(rectLabel, 0, rightRow);
                rightRow--;
            } else if (topCol >= 0) {
                if (tile.getType().equals("Corner")) {
                    cornerLabel.setBackground(new Background(cornerBackground));
                    Top.add(cornerLabel, topCol, 0);
                } else {
                    rotatedRectLabel.setBackground(new Background(rotatedBackground180));
                    Top.add(rotatedRectLabel, topCol, 0);
                }
                topCol--;
            } else if (leftRow < sideSize) {
                rectLabel.setBackground(new Background(rotatedBackground90));
                Left.add(rectLabel, 0, leftRow);
                leftRow++;
            }
        }
    }

    public void passPlayerPosTile(Player player, int pPos) {
        TileReader TReader = new TileReader();
        TReader.getTileDetails();
        LinkedList<Tile> tileList = TReader.returnTileList();

        Label MoveLabel = null;
        Tile Requested = tileList.get(pPos);
        String RequestedName = Requested.getSpace();

        MoveLabel = findLabelInPane(LeftPane, RequestedName, pPos);
        if (MoveLabel == null) MoveLabel = findLabelInPane(TopPane, RequestedName, pPos);
        if (MoveLabel == null) MoveLabel = findLabelInPane(RightPane, RequestedName, pPos);
        if (MoveLabel == null) MoveLabel = findLabelInPane(BottomPane, RequestedName, pPos);

        if (MoveLabel != null) {
            // Calculate the center of the label
            double labelCenterX = MoveLabel.getLayoutX() + MoveLabel.getWidth()/2;
            double labelCenterY = MoveLabel.getLayoutY() + MoveLabel.getHeight()/2;

            double offsetX = labelCenterX + MoveLabel.getParent().getLayoutX() + boardPane.getLayoutX();
            double offsetY = labelCenterY + MoveLabel.getParent().getLayoutY() + boardPane.getLayoutY();
            MoveToken(player, offsetX, offsetY);
        }
    }

    private Label findLabelInPane(GridPane pane, String requestedName, int PlayerPos) {
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
        // Get the token's dimensions
        double tokenWidth = playerToken.getImage().getWidth();
        double tokenHeight = playerToken.getImage().getHeight();

        // Calculate center position by offsetting by half the token size
        playerToken.setTranslateX(posX - tokenWidth/2);
        playerToken.setTranslateY(posY - tokenHeight/2);
        playerToken.toFront();
    }

    public void StartBoard(Stage PrimaryStage) {
        Label MoveLabel = null;
        MoveLabel = findLabelInPane(BottomPane,"Go", 1);
        if (MoveLabel != null) {

            double labelCenterX = MoveLabel.getLayoutX() + MoveLabel.getWidth()/2;
            double labelCenterY = MoveLabel.getLayoutY() + MoveLabel.getHeight()/2;

            Gamemanager.setStartposX((int) (labelCenterX + MoveLabel.getParent().getLayoutX()  + (boardPane.getLayoutX()/2.65)));
            Gamemanager.setStartposY((int) (labelCenterY + MoveLabel.getParent().getLayoutY() + (boardPane.getLayoutY()/2.25)));
        }
    }

    public Tile getTile(int position) {
        if (tileList == null || position < 0 || position >= tileList.size()) return null;
        return tileList.get(position);
    }
}
    // popups to be expanded on, if we had more time, template made for a hypothetical team to acquire this project from us.

//    public void addPopup(VBox Popup)
//    {
//        Popup.toFront();
//        root.getChildren().add(Popup);
//    }

