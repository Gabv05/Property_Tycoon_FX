package org.main.property_tycoon_fx.GameManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Bank {
    Scanner scan;

    public Bank() {
    }

    //method for bank to take the player's money
    private void takePlayerMoney(Player player, int takeSum) {
       player.setMoney(player.getMoney() - takeSum);
    }

    //method for bank to give the player money
    private void givePlayerMoney(Player player, int giveSum) {
       player.setMoney(player.getMoney() + giveSum);
    }

    public void holdAuction(Tile tileProperty, ArrayList<Player> playersList) {
        int price = 0; //the price to be paid for the property
        int participantMoney = 0; //variable for the money of the player actively bidding
        int playerID = -1; //variable that holds the player ID
        Player currentPlayer = null; //player actively bidding
        Player currentWinner = null; //player winning the auction
        boolean auctionEnd = false; //tracks if the auction needs to end
        int playerBid = 0; //holds the current bid the player wants to add onto the price
        String answer = null; //holds string (y or n)

        scan = new Scanner(System.in);

        //text simulation of an auction
        System.out.println("-----------------------------------------------");
        System.out.println("Auction");
        System.out.println("-----------------------------------------------");
        //while action has not ended
        while (!auctionEnd) {
            //input whether 2 or more players are still willing and able to participate
            System.out.print("Are 2 or more players still willing to bid? (Y/N): ");
            answer = scan.nextLine();
            //if players answer yes
            if (answer.equalsIgnoreCase("Y")) {
                //input player ID, and search for them in the player array
                System.out.print("Please enter your player ID: ");
                while (currentPlayer == null) {
                    playerID = scan.nextInt();
                    scan.nextLine();
                    for (int i = 0; i < playersList.size(); i++) {
                        if (playersList.get(i).getPlayerID() == playerID) {
                            currentPlayer = playersList.get(i);
                        }
                    }
                }
                participantMoney = currentPlayer.getMoney();
                //inputting the player's bid
                System.out.print("Please enter your amount to bid for: ");
                playerBid = scan.nextInt();
                scan.nextLine();

                //if player hasn't got enough money compared to what they bid, their bid is invalid
                if (playerBid > participantMoney) {
                    System.out.println("Not enough money to bid");
                    //if bid is valid, add it onto the price and make the current player the provisional winner of the auction
                } else if (price + playerBid <= participantMoney) {
                    price += playerBid;
                    currentWinner = currentPlayer;
                }

                System.out.println("The current bid is: " + price);
                currentPlayer = null;
            } else if (answer.equalsIgnoreCase("N")) {
                auctionEnd = true;
            }
        }

        tileProperty.setIsOwnedBy(currentWinner.getPlayerID());
        currentWinner.setMoney(currentWinner.getMoney() - price);
        System.out.println("Auction winner is: " + currentWinner.getPlayerID());
    }

    private void takeProperty(LinkedList<Tile> tileList, Player player) {
        int playerId = player.getPlayerID(); //getting the player id

        if (player.getMoney() <= 0) { //if player has ran out of money
            //searches through the tile list for any properties owned by the player
           for (Tile tile : tileList) {
               //if a property is found, it is sold at half price to the bank and the player receives the money
               if (tile.getIsOwnedBy() == playerId && player.getMoney() <= 0) { //repeated until player has more than 0 cash
                   tile.setIsOwnedBy(0);
                   player.setMoney((int)(tile.getCost()/2));
               } else {
                   break;
               }
           }
        }
    }

    public void chargePlayer(Player player, int amount, String reason) {
        if (player.getMoney() >= amount) {
            takePlayerMoney(player, amount);
            System.out.println(player.getPlayerName() + " paid £" + amount + " for " + reason);
        } else {
            System.out.println(player.getPlayerName() + " cannot afford £" + amount + " for " + reason);
            // You could call takeProperty here or handle bankruptcy logic
        }
    }

}

