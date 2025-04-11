package org.main.property_tycoon_fx.GameManager;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.LinkedList;

public class GameManager extends Application {
    GameBoard Gameboard = new GameBoard();
    Dice dice;
    EndTurnButton endTurnButton;
    private Player[] players;
    TurnManager turnManager = new TurnManager(players);
    Button rollButton;
    Button endTurnBtn;

    private final Bank bank = new Bank();
    private int StartposX;
    private int StartposY;

    public static void main(String[] args) {
        launch(args);
    }

    public int getStartposX() { return StartposX; }
    public void setStartposX(int startposX) { StartposX = startposX; }

    public int getStartposY() { return StartposY; }
    public void setStartposY(int startposY) { StartposY = startposY; }

    public Player[] getAllPlayers() {
        return players;
    }

    public Player[] createPlayers(int count, int goPosX, int goPosY) {
        players = new Player[count];

        // Player 1 is human, rest are AI
        players[0] = new Player(1, "Player1", 1500, Gameboard);
        players[1] = new PlayerAI(2, "AI Player2", 1500, Gameboard, bank);
        players[2] = new PlayerAI(3, "AI Player3", 1500, Gameboard, bank);
        players[3] = new PlayerAI(4, "AI Player4", 1500, Gameboard, bank);
        players[4] = new PlayerAI(5, "AI Player5", 1500, Gameboard, bank);

        for (Player player : players) {
            Gameboard.addPlayerImage(player, goPosX, goPosY);
            Gameboard.addPlayerTab(player);
        }

        turnManager.setPlayerArray(players);
        return players;
    }

    @Override
    public void start(Stage primaryStage) {
        // Load board and tiles
        TileReader tileReader = new TileReader();
        tileReader.getTileDetails();
        LinkedList<Tile> tileList = tileReader.returnTileList();

        Gameboard.Gamemanager = this;
        Gameboard.start(primaryStage);

        // Create 5 players
        createPlayers(5, getStartposX(), getStartposY());

        // Get UI elements
        dice = Gameboard.getDice();
        endTurnButton = Gameboard.getEndTurnButton();
        rollButton = dice.getRollButton();
        endTurnBtn = endTurnButton.getEndTurnBtn();

        CardReader cardReader = new CardReader();
        cardReader.getCardDetails();

        // Roll Button (Only works for human players)
        rollButton.setOnAction(event -> {
            Player currentPlayer = turnManager.getCurrentPlayer();
            if (currentPlayer instanceof PlayerAI) {
                System.out.println("AI can't roll manually.");
                return;
            }

            System.out.println("Roll Button Clicked by " + currentPlayer.getPlayerName());
            turnManager.currentPlayerRoll(currentPlayer);
            Gameboard.passPlayerPosTile(currentPlayer, currentPlayer.getTilePosition());
        });

        // End Turn Button
        endTurnBtn.setOnAction(event -> {
            turnManager.nextplayer();
            Player nextPlayer = turnManager.getCurrentPlayer();
            System.out.println("It's now " + nextPlayer.getPlayerName() + "'s turn.");

            // Disable roll button for AI
            rollButton.setDisable(nextPlayer instanceof PlayerAI);

            if (nextPlayer instanceof PlayerAI) {
                nextPlayer.takeTurn();
                Gameboard.passPlayerPosTile(nextPlayer, nextPlayer.getTilePosition());

                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> endTurnBtn.fire());
                pause.play();
            } else {
                dice.resetRolls();
            }
        });

        System.out.println("Game started at " + getStartposX() + ", " + getStartposY());
    }
}
