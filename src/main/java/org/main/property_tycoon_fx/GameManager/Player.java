package org.main.property_tycoon_fx.GameManager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import java.util.LinkedList;

public class Player {
    private int playerID;
    private String playerName;
    private int Money;
    private String token;

    private GameBoard gameBoard;
    private Dice dice;
    private Button rollButton;

    private int tilePosition = 0;
    private int minimumPosition = 0;
    private int maxPosition = 39;

    private ImageView playerTokenImage;

    //Jail + GOOJ system
    private boolean isInJail = false;
    private int jailTurns = 0;
    private int getOutOfJailCards = 0;

    private StringProperty positionText;
    private StringProperty moneyText;

    public Player(int playerID, String playerName, int Money, GameBoard gameBoard) {
        this.Money = Money;
        this.playerID = playerID;
        this.playerName = playerName;
        this.gameBoard = gameBoard;
        dice = gameBoard.getDice();
        token = gameBoard.giveToken();
        this.tilePosition = 0;

        // for player tab text
        this.positionText = new SimpleStringProperty("Position: " + getTileName(this.tilePosition));  // initially have GO for when player first joins game
        this.moneyText = new SimpleStringProperty("£ " + this.Money);
    }

    public String getPlayerName() {
        return playerName;
    }


    public String getTileName(int index) {
        TileReader TReader = new TileReader();
        TReader.getTileDetails(); // Load tile details from Excel

        LinkedList<Tile> tileList = TReader.returnTileList(); // Get tiles from TileReader
        for (Tile tile : tileList) {
            if (tile.getPosition() -1 == index) {
                System.out.println(tile.getPosition() -1 + "TILE : " + tile.getSpace());
                return tile.getSpace();
            }
        }
        return " ";
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
        moneyText.set("£" + Money);    // update money value in player tab
    }

    public int getTilePosition() {
        return tilePosition;
    }

    public StringProperty getPositionText() {return positionText;}
    public StringProperty geMoneyText() {return moneyText;}

    public void setTilePosition(int tilePosition) {
        this.tilePosition = tilePosition;
        positionText.set("Position: " + getTileName(tilePosition));
    }

    public ImageView getPlayerTokenImage() {
        return playerTokenImage;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public ImageView playerImageView() {
        System.out.println(playerName + " is: " + token);
        Image tokenImage = new Image("/images/" + token + "_token.png", 60, 60, true, true);
        ImageView imageView = new ImageView(tokenImage);
        playerTokenImage = imageView;
        return imageView;
    }

    public ImageView playerTabImageView() {
        System.out.println(playerName + " is tab: " + token);
        Image tabImage = new Image("/images/" + token + "_tab.png", 328, 0, true, true);
        ImageView tabImageView = new ImageView(tabImage);
        return tabImageView;
    }

    public int move() {
        if (dice.canRollAgain()) {
            int moveValue = dice.rollDice();
            tilePosition = tilePosition + moveValue;
            System.out.println(playerName + " position: " + tilePosition);
        } else {
            System.out.println(playerName + " can't roll again");
        }

        if (tilePosition > maxPosition) {
            int difference = tilePosition - maxPosition;
            tilePosition = minimumPosition + difference;
        }

        System.out.println(tilePosition);
        setTilePosition(tilePosition);
        return tilePosition;
    }

    public void buyTileProperty(int tilePosition, Bank bank) {
        TileReader TReader = new TileReader();
        TReader.getTileDetails();
        LinkedList<Tile> tileList = TReader.returnTileList();
        Tile currentTile = tileList.get(tilePosition);

        if (currentTile.isCanBeBought()) {
            int cost = (int) currentTile.getCost();
            if (getMoney() >= cost) {
                bank.chargePlayer(this, cost, "Property purchase: " + currentTile.getSpace());
                currentTile.setCanBeBought(false);
                currentTile.setIsOwnedBy(this.getPlayerID());
                System.out.println("Cha Ching, Bought " + currentTile.getSpace());
            } else {
                System.out.println("You're broke, can't buy " + currentTile.getSpace());
            }
        }
    }

    public void takeTurn() {
        // Default or placeholder behavior for human players
        System.out.println(getPlayerName() + "'s turn.");
    }

    // Jail!!!
    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
        if (!inJail) jailTurns = 0;
    }

    public int getJailTurns() {
        return jailTurns;
    }

    public void incrementJailTurns() {
        jailTurns++;
    }

    public void resetJailTurns() {
        jailTurns = 0;
    }

    public void moveToJail() {
        System.out.println(getPlayerName() + " is sent to Jail.");
        setTilePosition(10);
        setInJail(true);
        resetJailTurns();
        getGameBoard().passPlayerPosTile(this, 10);
    }

    public boolean handleJailTurn() {
        if (!isInJail()) return false;

        System.out.println(getPlayerName() + " is in jail (Turn " + getJailTurns() + "/3)");

        if (hasGetOutOfJailCard()) {
            System.out.println(getPlayerName() + " used a Get Out of Jail Free card.");
            useGetOutOfJailCard();
            setInJail(false);
            resetJailTurns();
            return false;
        }

        incrementJailTurns();

        if (getJailTurns() >= 3) {
            System.out.println(getPlayerName() + " has served their sentence and is released.");
            setInJail(false);
            resetJailTurns();
            return false;
        }

        System.out.println(getPlayerName() + " skips this turn.");
        return true;
    }

    // GOOJ Cards
    public int getGetOutOfJailCards() {
        return getOutOfJailCards;
    }

    public void addGetOutOfJailCard() {
        this.getOutOfJailCards++;
    }

    public void useGetOutOfJailCard() {
        if (getOutOfJailCards > 0) {
            this.getOutOfJailCards--;
        }
    }

    public boolean hasGetOutOfJailCard() {
        return getOutOfJailCards > 0;
    }


    public void CheckCondition() {
        if (tilePosition == 3 || tilePosition == 18 || tilePosition == 34) {

            // reference to popup class
            //     Popup popup = new Popup("PotLuck", 0);
            //   gameBoard.addPopup(popup.getPopupBox());
        }
        if (tilePosition == 8 || tilePosition == 23 || tilePosition == 37) {
            // reference to popup class
            //   Popup popup = new Popup("OppKnocks", 0);
            // gameBoard.addPopup(popup.getPopupBox());
        }
    }

}


