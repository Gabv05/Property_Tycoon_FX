package org.main.property_tycoon_fx.GameManager;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import java.util.LinkedList;


public class Player {
    private int playerID;
    private String playerName;
    private int Money;
    private String token;

    // to access dice
    private GameBoard gameBoard;    // let player reference gameboard
    private Dice dice;
    private Button rollButton;

      private int tilePosition = 0;
      private int minimumPosition = 0;
      private int maxPosition = 39;


     // Tile CurrentTile;

    public String getPlayerName() {
        return playerName;
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
    }

    public void setTilePosition(int tilePosition) {this.tilePosition = tilePosition;}

    public int getTilePosition() {
        return tilePosition;
    }


    public Player(int playerID, String playerName, int Money, GameBoard gameBoard) {
        this.Money = Money;
        this.playerID = playerID;
        this.playerName = playerName;
        this.gameBoard = gameBoard;
        dice = gameBoard.getDice();    // get dice object created in gameboard
        token = gameBoard.giveToken();
    }


    public ImageView playerImageView() {
        System.out.println(playerName + " is: " + token);
        // create image and imageview
        Image tokenImage = new Image("/images/" + token + "_token.png", 60, 60, true, true);
        ImageView imageView = new ImageView(tokenImage);
        return imageView;
    }

    public ImageView playerTabImageView() {
        System.out.println(playerName + " is tab: " + token);
        // create image and IV
        Image tabImage = new Image("/images/" + token + "_tab.png", 328, 0, true, true);
        ImageView tabImageView = new ImageView(tabImage);
        return tabImageView;
    }

     public int move()
     {
         if (dice.canRollAgain()) {
             int moveValue = dice.rollDice();
             tilePosition = tilePosition + moveValue;
             System.out.println(playerName + " position: " + tilePosition);
         } else{
             System.out.println(playerName + " can't roll again");
         }

        if (tilePosition > maxPosition) {
            int difference = tilePosition - maxPosition;
            tilePosition = minimumPosition + difference;
        }

        System.out.println(tilePosition);
        return tilePosition;
     }


    public void buyTileProperty(int tilePosition)
    {
        // Get List of tiles from Excel
        TileReader TReader = new TileReader();
        TReader.getTileDetails(); // Load tile details from Excel
        LinkedList<Tile> tileList = TReader.returnTileList(); // Get tiles from TileReader
        Tile currentTile = tileList.get(tilePosition);
        if(currentTile.isCanBeBought())
        {
            if (this.Money >= currentTile.getCost())
            {
              this.Money -= (int) currentTile.getCost();
              // The thing to make it like update on excel
                System.out.println("Cha Ching, Bought "+ currentTile.getSpace());
                currentTile.setCanBeBought(false);
                currentTile.setIsOwnedBy(this.playerID);
                System.out.println(currentTile.getSpace() + " Is owned by " + currentTile.getIsOwnedBy());
            }
            else
            {
                System.out.println("You Broke or tile no can be bought lol");
            }
        }
    }
}
