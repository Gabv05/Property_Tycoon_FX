package org.main.property_tycoon_fx.GameManager;

import javafx.scene.image.Image;

import java.util.Random;

public class Player {
      private int tilePosition = 0;
      private int minimumPosition = 0;
      private int maxPosition = 39;

      Tile CurrentTile;

    public Player(int playerID, String playerName) {
    }

    private int rollDice() {
          Random DiceRollRandom = new Random();
          int diceroll1 = DiceRollRandom.nextInt(1,6);
          int diceroll2 = DiceRollRandom.nextInt(1,6);
          int diceRollValue = diceroll1 + diceroll2;
          System.out.println("Dice roll value: " + diceRollValue);
          return(diceRollValue);
     }

     public int move()
     {
        tilePosition = tilePosition + rollDice();

        if (tilePosition > maxPosition) {
            int difference = tilePosition - maxPosition;
            tilePosition = minimumPosition + difference;
        }

        System.out.println("Player position: " + tilePosition);

        return tilePosition;
     }

    public int getTilePosition() {
        return tilePosition;
    }
}
