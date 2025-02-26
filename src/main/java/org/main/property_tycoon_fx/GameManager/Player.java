package org.main.property_tycoon_fx.GameManager;

import javafx.scene.image.Image;

import java.util.Random;

public class Player {
      private int tilePosition = 0;
      private int minimumPosition = 0;
      private int maxPosition = 39;

     // Tile CurrentTile;

    public Player(int playerID, String playerName) {
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
              noMoves += diceRollValue;

              System.out.println("Roll number: " + rollNum + " Dice roll value: " + diceRollValue + " first: " + diceroll1 + " second: " + diceroll2);

              // if player rolls a double
              if (diceroll1 == diceroll2) {
                  noMoves = 0;      // do not let player move if they roll a double
                  isDouble ++;
                  // if player rolls a double once
                  if (isDouble == 1) {
                      System.out.println("You rolled a double! Roll again!");
                  } else if (isDouble == 2) {
                      System.out.println("You rolled another double! GO TO JAIL!");
                      noMoves = 0;      // do not let player move if they roll a double
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

        System.out.println("Player position: " + tilePosition + "\n");

        return tilePosition;


     }




    public int getTilePosition() {
        return tilePosition;
    }



}
