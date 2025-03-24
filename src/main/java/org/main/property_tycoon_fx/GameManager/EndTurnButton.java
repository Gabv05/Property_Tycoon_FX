package org.main.property_tycoon_fx.GameManager;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EndTurnButton {
    private Button endTurnBtn;


    public EndTurnButton(Pane dicePane, double sceneWidth, double sceneHeight) {
        // endTurn button
        //
        this.endTurnBtn = new Button();
        endTurnBtn.setPrefWidth(sceneWidth * 0.208);
        endTurnBtn.setPrefHeight(sceneHeight * 0.03);
        // graphics of button
        Image buttonImage = (new Image("/images/endTurnBtn.png", 0, 0, true, true));
        ImageView buttonImageView = new ImageView(buttonImage);
        // apply css to get rid of padding on button to only show image
        endTurnBtn.setStyle("-fx-padding: 0;");
        endTurnBtn.setGraphic(buttonImageView);
        // position button in pane
        endTurnBtn.setTranslateX(sceneWidth * 0.01);
        endTurnBtn.setTranslateY(sceneHeight * 0.115);
        // add button to dicePane
        dicePane.getChildren().add(endTurnBtn);
    }

    public Button getEndTurnBtn() {
        return endTurnBtn;
    }
}
