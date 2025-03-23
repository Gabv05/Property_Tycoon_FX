package org.main.property_tycoon_fx.GameManager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.util.Duration;
import java.util.Random;

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

    private int isDouble = 0;
    private int rollNum = 0;
    private int noMoves = 0;
    private boolean canRoll = true; // Control rolling

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


    // only let another roll if animation has completed
    private void diceAnimation(Runnable animationComplete) {
        // show six random images of the dice in 120ms
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(20), event -> {
                    int randomIndex1 = random.nextInt(1, 6);
                    dice1 = setDiceImage(randomIndex1);
                    imageView1.setImage(dice1);

                    int randomIndex2 = random.nextInt(1, 6);
                    dice2 = setDiceImage(randomIndex2);
                    imageView2.setImage(dice2);
                }),
                new KeyFrame(Duration.millis(120))
        );
        timeline.setCycleCount(6);
        // when timeline (random images) complete set the last image shown to be the final dice roll values
        timeline.setOnFinished(event -> {
            int finalDice1Value = setDice1Value();
            int finalDice2Value = setDice2Value();

            dice1 = setDiceImage(finalDice1Value);
            imageView1.setImage(dice1);
            dice2 = setDiceImage(finalDice2Value);
            imageView2.setImage(dice2);

            if (finalDice1Value == finalDice2Value) {
                Timeline finalDisplayTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(3))
                );
                finalDisplayTimeline.setOnFinished(e -> animationComplete.run());
                finalDisplayTimeline.play();
            } else {
                animationComplete.run();
            }
        });
        timeline.play();
    }

    public int rollDice() {
        if (!canRoll) {
            return noMoves; // Prevent rolling if not allowed.
        }

        int dice1result = random.nextInt(1, 6);
        getDice1Value(dice1result);
        int dice2result = random.nextInt(1, 6);
        getDice2Value(dice2result);

        diceAnimation(() -> { // Callback for animation completion
            noMoves = setDice1Value() + setDice2Value();

            if (setDice1Value() == setDice2Value()) {
                noMoves = 0;
                isDouble++;
                if (isDouble == 1) {
                    System.out.println("You rolled a double! Roll again!");
                    canRoll = true; // Allow another roll
                } else if (isDouble == 2) {
                    System.out.println("You rolled another double! GO TO JAIL!");
                    noMoves = 0;
                    canRoll = false; // Prevent further rolls
                }
            } else {
                canRoll = false; // Prevent further rolls
            }
        });
        rollNum++;
        return noMoves;
    }

    public void resetRolls(){
        isDouble = 0;
        rollNum = 0;
        noMoves = 0;
        canRoll = true;
    }


}
