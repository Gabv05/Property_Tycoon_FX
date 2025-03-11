package org.main.property_tycoon_fx.GameManager;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Player {
    private int playerID;
    private String playerName;
    private int Money;

    // to access dice
    private GameBoard gameBoard;    // let player reference gameboard
    private Dice dice;
    private Button rollButton;

      private int tilePosition = 0;
      private int minimumPosition = 0;
      private int maxPosition = 39;

      // get array of token types and convert to array list
      String[] tokenTypesArr = {"boot", "smartphone", "ship", "hatstand"};
      List<String> tokenTypesList = Arrays.asList(tokenTypesArr);
      ArrayList<String> tokenTypesAL = new ArrayList<>(tokenTypesList);



     // Tile CurrentTile;

    private ArrayList<String> updateArrayList(ArrayList<String> tokensAL) {
        System.out.println(tokensAL);
        return tokensAL;
    }

    private int randomIndex(ArrayList<String> tokensAL) {
        int randomIndex = new Random().nextInt(tokensAL.size());
        System.out.println(randomIndex);
        return randomIndex;
    }

    private String tokenString(int index, ArrayList<String> tokensAL) {
        String tokenName = tokensAL.get(index);
        System.out.println(tokenName);
        // update array list:
        tokensAL.remove(index);
        updateArrayList(tokensAL);

        return tokenName;
    }

    private ImageView playerImageView(){
        // get random string for image path
        String tokenName = tokenString(randomIndex(tokenTypesAL), tokenTypesAL);
        Image tokenImage = new Image("/images/" + tokenName + "_token.png", 100, 100, true, true);
        ImageView tokenIV = new ImageView(tokenImage);

        return tokenIV;
    }



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

    public int getTilePosition() {
        return tilePosition;
    }


    public Player(int playerID, String playerName, int Money, GameBoard gameBoard) {
        this.Money = Money;
        this.playerID = playerID;
        this.playerName = playerName;
        this.gameBoard = gameBoard;
        this.dice = gameBoard.getDice();    // get dice object created in gameboard

        // when button is clicked move
        this.rollButton = dice.getRollButton();
        rollButton.setOnAction(actionEvent -> {
            System.out.println("Button Clicked");
            move();
        });

        // test AL
        System.out.println(tokenTypesAL + " size = " + tokenTypesAL.size());
        playerImageView();


        Image playerToken = new Image("/images/ship_token.png", 100, 0, true, true);
        ImageView playerTokenImageView = new ImageView(playerToken);
        // set position
        playerTokenImageView.setX(500);
        playerTokenImageView.setY(500);
        // create group and scene object

    }

    // roll the dice
    public int getMoveValue(){
        int totalMovement = dice.rollDice();    // get total move value from dice object
        //System.out.println(playerName + " total movement: " + totalMovement);

        return totalMovement;
    }



     public int move()
     {
        tilePosition = tilePosition + getMoveValue();

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
            }
            else
            {
                System.out.println("You Broke or tile no can be bought lol");
            }
        }
    }

}
