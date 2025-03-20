package org.main.property_tycoon_fx.GameManager;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Random;

public class Player {

    private int playerID;
    private String playerName;
    private int Money;

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

    public int getTilePosition() {
        return tilePosition;
    }

    public Player(int playerID, String playerName, int Money) {
        this.Money = Money;
        this.playerID = playerID;
        this.playerName = playerName;
    }

    private int rollDice() {
          Random DiceRollRandom = new Random();
          int isDouble= 0;
          int noMoves = 0;

          // allow player to roll a max of two times if roll is a double
          for (int rollNum = 0; rollNum < 2; rollNum++) {
              int diceroll1 = DiceRollRandom.nextInt(1,3);
              int diceroll2 = DiceRollRandom.nextInt(1,3);
              int diceRollValue = diceroll1 + diceroll2;
              noMoves = diceRollValue;

            //  System.out.println("Roll number: " + rollNum + " Dice roll value: " + diceRollValue + " first: " + diceroll1 + " second: " + diceroll2);

              // if player rolls a double
              if (diceroll1 == diceroll2) {
                  noMoves = 0;      // do not let player move if they roll a double
                  isDouble ++;
                  // if player rolls a double once
                  if (isDouble == 1) {
              //        System.out.println("You rolled a double! Roll again!");
                  } else if (isDouble == 2) { //TODO need to make player go to the jail position if 2 doubles are rolled
              //        System.out.println("You rolled another double! GO TO JAIL!");
                      break;
                  }
              } else {
                  break;    // if roll is not a double, break out and return noMoves
              }
          }

          return noMoves;
     }

     public int move()
     {
        tilePosition = tilePosition + rollDice();

        if (tilePosition > maxPosition) {
            int difference = tilePosition - maxPosition;
            tilePosition = minimumPosition + difference;
        }

      //  System.out.println("Player position: " + tilePosition + "\n");

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
