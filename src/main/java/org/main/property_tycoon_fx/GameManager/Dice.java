package org.main.property_tycoon_fx.GameManager;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.Random;

// ignore animation related methods for now - will work on that later on in project

public class Dice {
    private final Random random = new Random();
    private Image dice1;
    private Image dice2;
    private ImageView imageView1;
    private ImageView imageView2;
    private Pane dicePane;
    private int dice1Val;
    private int dice2Val;
    private Button rollButton;

    // get button and dice images and put in the dicePane in gameboard
    public Dice(Pane dicePane, double sceneWidth, double sceneHeight) {
        this.dicePane = dicePane;

        // roll button
        //
        this.rollButton = new Button();
        rollButton.setPrefWidth(100);
        rollButton.setPrefHeight(30);
        // graphics of button
        Image buttonImage = (new Image("/images/rollButton.png", 100, 0, true, true));
        ImageView buttonImageView = new ImageView(buttonImage);
        // apply css to get rid of padding on button to only show image
        rollButton.setStyle("-fx-padding: 0;");
        rollButton.setGraphic(buttonImageView);
        // position button in pane
        rollButton.setTranslateX(sceneWidth * 0.03);
        rollButton.setTranslateY(sceneHeight * 0.045);
        // add button to dicePane
        dicePane.getChildren().add(rollButton);

        // show dice1
        //
        dice1 = (new Image("/images/dice_1.png", 100, 0, true,true));   // set initial image
        imageView1 = new ImageView(dice1);
        // set position in pane
        imageView1.setTranslateX(sceneWidth * 0.1);
        imageView1.setTranslateY(sceneHeight * 0.01);
        dicePane.getChildren().add(imageView1);

        // show dice2
        //
        dice2 = (new Image("/images/dice_1.png", 100, 0, true,true));   // set initial image
        imageView2 = new ImageView(dice2);
        // set position in pane
        imageView2.setTranslateX(sceneWidth * 0.165);
        imageView2.setTranslateY(sceneHeight * 0.01);
        dicePane.getChildren().add(imageView2);
    }

    // to set a specific dice image
    public Image setDiceImage(int index) {
        return new Image("/images/dice_" + index + ".png", 100, 0, true, true);
    }


    // used as random image for animation
    public Image randomDiceImage() {
        int randomInt = random.nextInt(1, 7);
        return new Image("/images/dice_" + randomInt + ".png", 100, 0, true, true);
    }

    private void getDice1Value(int dice1Value) {
        this.dice1Val = dice1Value;
    }

    private int setDice1Value(){
        return dice1Val;
    }

    private void getDice2Value(int dice2Value) {
        this.dice2Val = dice2Value;
    }

    private int setDice2Value(){
        return dice2Val;
    }

    // used in player class to access button
    public Button getRollButton(){
        return rollButton;
    }


    public int rollDice() {
        int isDouble = 0;
        int noMoves = 0;
        int dice1result;
        int dice2result;

        for (int rollNum = 0; rollNum < 2; rollNum++) {
            // dice 1:
            dice1result = random.nextInt(1, 7);
            getDice1Value(dice1result);
            dice1 = setDiceImage(setDice1Value());
            imageView1.setImage(dice1);

            // dice 2:
            dice2result = random.nextInt(1, 7);
            getDice2Value(dice2result);
            dice2 = setDiceImage(setDice2Value());
            imageView2.setImage(dice2);


            int diceRollValue = dice1result + dice2result;
            noMoves = diceRollValue;

            System.out.println("Roll number: " + rollNum + " Dice roll value: " + diceRollValue + " first: " + dice1result + " second: " + dice2result);

            if (dice1result == dice2result) {
                noMoves = 0;
                isDouble++;
                if (isDouble == 1) {
                    System.out.println("You rolled a double! Roll again!");

                } else if (isDouble == 2) {
                    System.out.println("You rolled another double! GO TO JAIL!");
                    break;
                }
            } else {
                break;
            }
        }
        return noMoves;
    }
}
