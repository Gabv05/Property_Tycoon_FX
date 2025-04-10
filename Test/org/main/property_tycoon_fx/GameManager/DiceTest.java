package org.main.property_tycoon_fx.GameManager;

import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {
    Dice dice = new Dice();

    @Test
    void canRollAgain() {

    }

    @Test
    void rollDiceNormal() {
        for (int i = 0; i < 10; i++) {
            int roll = dice.rollDice();
            assertTrue(roll >= 1 && roll <= 12, "Roll should be between 1 and 12");
        }
    }

    @Test
    void rollDiceFalse() {
        int roll = dice.rollDice();
    }

    @Test
    void rollDiceExponential() {
        int roll = dice.rollDice();
    }
}