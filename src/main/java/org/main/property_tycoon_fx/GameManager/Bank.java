package org.main.property_tycoon_fx.GameManager;

public class Bank {

    public Bank() {
    }

    private void takePlayerMoney(Player player, int takeSum) {
       player.setMoney(player.getMoney() - takeSum);
    }

    private void givePlayerMoney(Player player, int giveSum) {
       player.setMoney(player.getMoney() + giveSum);
    }

    private void holdAuction(Player participant, Tile tileProperty) {
        //TODO players will need to type in their bid and then enter their player id, after they will keep bidding until one is left
        int startingPrice = 0;
        int playerBid = 0;
        int participantMoney = participant.getMoney();


        System.out.println("-----------------------------------------------");
        System.out.println("Auction");
        System.out.println("-----------------------------------------------");
        System.out.println("Please enter your bid or drop out of auction:");
        System.out.println("Please enter your name or token ID:");

        if (playerBid > participantMoney) {
            System.out.println("Not enough money to bid");
            //TODO [kick player out the auction]
        } else {
            startingPrice += playerBid;
        }

        //TODO if one player is left, they get the property

    }

    private void takeProperty() {
        //TODO if player is out of money, their lowest valued property is taken and they are paid half the money for it, then its auctioned, repeat until player has more than 0 money
    }

}
