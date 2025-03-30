package org.main.property_tycoon_fx.GameManager;

import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    Scanner scan;

    public Bank() {
    }

    private void takePlayerMoney(Player player, int takeSum) {
       player.setMoney(player.getMoney() - takeSum);
    }

    private void givePlayerMoney(Player player, int giveSum) {
       player.setMoney(player.getMoney() + giveSum);
    }

    public void holdAuction(Tile tileProperty, ArrayList<Player> playersList) {
        int startingPrice = 0;
        int participantMoney = 0;
        int playerID = -1;
        Player currentPlayer = null;
        Player currentWinner = null;
        boolean auctionEnd = false;

        scan = new Scanner(System.in);

        System.out.println("-----------------------------------------------");
        System.out.println("Auction");
        System.out.println("-----------------------------------------------");
        while (!auctionEnd) {
            int playerBid = 0;
            String answer = null;
            System.out.print("Are 2 or more players still willing to bid? (Y/N): ");
            answer = scan.nextLine();
            if (answer.equalsIgnoreCase("Y")) {
                System.out.print("Please enter your player ID: ");
                while (currentPlayer == null) {
                    playerID = scan.nextInt();
                    for (int i = 0; i < playersList.size(); i++) {
                        if (playersList.get(i).getPlayerID() == playerID) {
                            currentPlayer = playersList.get(i);
                        }
                    }
                }
                participantMoney = currentPlayer.getMoney();
                System.out.print("Please enter your amount to bid for: ");
                playerBid = scan.nextInt();

                if (playerBid > participantMoney) {
                    System.out.println("Not enough money to bid");
                } else if (startingPrice + playerBid <= participantMoney) {
                    startingPrice += playerBid;
                    currentWinner = currentPlayer;
                } else {
                    System.out.println("Your bid is too high for your bank balance");
                }

                System.out.println("The current bid is: " + startingPrice);

            }
        }

        tileProperty.setIsOwnedBy(currentWinner.getPlayerID());
        System.out.println("Auction winner is: " + currentWinner.getPlayerID());
    }

    private void takeProperty() {
        //TODO if player is out of money, their lowest valued property is taken and they are paid half the money for it, then its auctioned, repeat until player has more than 0 money
    }

}
