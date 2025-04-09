//WIP
package org.main.property_tycoon_fx.GameManager;

public class PlayerAI extends Player {

    private final BotStrategy strategy;
    private final Bank bank;

    public PlayerAI(int playerID, String playerName, int money, GameBoard gameBoard, Bank bank) {
        super(playerID, playerName, money, gameBoard);
        this.strategy = new BotStrategy(); // probably only going to write one - decisions made off of % of funds 
        this.bank = bank;
    }

    @Override
    public void takeTurn() {
        // jail
        if (handleJailTurn()) {
            return; // Skips turn if still in jail
        }

        // move 
        int newPosition = move();
        Tile tile = getGameBoard().getTile(newPosition);

        if (tile == null) {
            System.out.println(getPlayerName() + " landed on an invalid tile.");
            return;
        }
  System.out.println(getPlayerName() + " landed on " + tile.getSpace());

        // Handle Tile Action (GoToJail, potluck etc)
        if (tile.getAction() != null && !tile.getAction().isEmpty()) {
            handleTileAction(tile);
            return;
        }

        // tile type - get from excel
        if (tile.isCanBeBought()) {
            tryToBuyProperty(tile);
        } else if (tile.getIsOwnedBy() == getPlayerID()) {
            tryToUpgrade(tile);
        } else if (tile.getIsOwnedBy() != -1) {
            payRent(tile);
        } else {
            System.out.println("No action needed on this tile.");
        }
    }

    // action tiles 
    private void handleTileAction(Tile tile) {
        switch (tile.getAction()) {
            case "GoToJail":
                moveToJail();
                break;

            case "PayTax":
                payTax(tile);
                break;

            case "DrawCard":
                drawCard(tile); // Placeholder
                break;

            default:
                System.out.println("Unknown tile action: " + tile.getAction());
                break;
        }
    }

    // Buy Gaff
    private void tryToBuyProperty(Tile tile) {
        if (strategy.shouldBuyProperty(this, tile)) {
            buyTileProperty((int) tile.getPosition());
        } else {
            System.out.println(getPlayerName() + " chose not to buy " + tile.getSpace());
        }
    }

// pay rent
    private void payRent(Tile tile) {
        Player owner = getGameBoard().getPlayerByID(tile.getIsOwnedBy());
//in case there’s a problem with our ownership system, this prevents the program crashing 
        if (owner == null) {
            System.out.println("No owner.");
            return;
        }

        double[] rentArray = tile.getRent();
        int rent = (int) (rentArray.length > 0 ? rentArray[0] : 0); // todo house logic 

        if (getMoney() >= rent) {
            setMoney(getMoney() - rent);
  owner.setMoney(owner.getMoney() + rent);
            System.out.println(getPlayerName() + " paid £" + rent + " rent to " + owner.getPlayerName());
        } else {
            System.out.println(getPlayerName() + " can't afford rent!");
            //do bankruptcy and asset selling 
        }
    }

    // Negative potluck - add inheriting values or work on later - probably easier to write these per card
    private void payTax(Tile tile) {
        int taxAmount = (int) tile.getCost(); 
// Will depend on card, gives general framework 
        if (getMoney() >= taxAmount) {
            bank.chargePlayer(this, taxAmount, "Tax");
        } else {
            System.out.println(getPlayerName() + " can't afford to pay!");
            // TODO: Add forced payment / liquidation
        }
    }

    // upgrade gaff
    private void tryToUpgrade(Tile tile) {
        if (strategy.shouldUpgrade(this, tile)) {
            System.out.println(getPlayerName() + " upgrades " + tile.getSpace());
            //todo: Deduct upgrade cost, increment house rank
        } else {
            System.out.println(getPlayerName() + " decided not to upgrade " + tile.getSpace());
        }
    }

    // draw card placeholder, can use for both potluck and other
    private void drawCard(Tile tile) {
        System.out.println(getPlayerName() + " drew a card at " + tile.getSpace());
        //todo: Connect to deck
    }
}
