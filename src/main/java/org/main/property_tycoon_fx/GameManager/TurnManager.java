package org.main.property_tycoon_fx.GameManager;

public class TurnManager {
    private boolean endTurn;
    private GameBoard gameBoard;
    private int turnIndex = 0;
    private Player[] players;


    public TurnManager(Player[] players) {
        this.endTurn = false;
        this.players = players;
    }

    public void setPlayerArray(Player[] players){
        this.players = players;
    }

    public Player[] getPlayersArray(){
        return players;
    }

    public Player getCurrentPlayer(){
        return getPlayersArray()[turnIndex];
    }

    public void currentPlayerRoll(Player player) {
        if (player == getCurrentPlayer()) {
            player.move();
        }
    }

    public void nextplayer() {
        // increment turnIndex so it can get next player and wraps back to the start of array if increments larger than array
        turnIndex = (turnIndex + 1) % getPlayersArray().length;
    }
}
