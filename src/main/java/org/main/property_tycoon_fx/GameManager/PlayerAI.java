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
        // Allow AI to roll dice
        getGameBoard().getDice().resetRolls();

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

    private void tryToBuyProperty(Tile tile) {
        if (strategy.shouldBuyProperty(this, tile)) {
            buyTileProperty((int) tile.getPosition(), bank);
        } else {
            System.out.println(getPlayerName() + " chose not to buy " + tile.getSpace());
        }
    }

    // pay rent
    private void payRent(Tile tile) {
        Player owner = getGameBoard().getPlayerByID(tile.getIsOwnedBy());
        if (owner == null) {
            System.out.println("No owner.");
            return;
        }

        double[] rentArray = tile.getRent();
        int rent = (int) (rentArray.length > 0 ? rentArray[0] : 0); // Replace with tile.getCurrentRent()

        if (getMoney() >= rent) {
            setMoney(getMoney() - rent);  // <-- THIS
            owner.setMoney(owner.getMoney() + rent);  // <-- AND THIS
            System.out.println(getPlayerName() + " paid £" + rent + " rent to " + owner.getPlayerName());
        } else {
            System.out.println(getPlayerName() + " can't afford rent!");
            // TODO: Add bankruptcy or selling assets
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
        //todo: Connect to deck - nick the code from the uhhh tile reader for the cards
    }

    private boolean isInJail = false;
    private int jailTurns = 0;
    private int getOutOfJailCards = 0;

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        this.isInJail = inJail;
        if (!inJail) {
            jailTurns = 0;
        }
    }

    public int getJailTurns() {
        return jailTurns;
    }

    public void incrementJailTurns() {
        jailTurns++;
    }

    public void resetJailTurns() {
        jailTurns = 0;
    }

    public boolean hasGetOutOfJailCard() {
        return getOutOfJailCards > 0;
    }

    public void addGetOutOfJailCard() {
        getOutOfJailCards++;
    }

    public void useGetOutOfJailCard() {
        if (getOutOfJailCards > 0) {
            getOutOfJailCards--;
        }
    }

    public void moveToJail() {
        System.out.println(getPlayerName() + " is sent to Jail.");
        setTilePosition(11); // Use correct jail tile index
        setInJail(true);
        resetJailTurns();
        getGameBoard().passPlayerPosTile(this, 11); // Move token on board too
    }

    public boolean handleJailTurn() {
        if (!isInJail()) return false;

        System.out.println(getPlayerName() + " is in jail (Turn " + getJailTurns() + "/3)");

        if (hasGetOutOfJailCard()) {
            System.out.println(getPlayerName() + " used a Get Out of Jail Free card!");
            useGetOutOfJailCard();
            setInJail(false);
            resetJailTurns();
            return false;
        }

        incrementJailTurns();

        if (jailTurns >= 3) {
            System.out.println(getPlayerName() + " has served 3 turns and is released from jail.");
            setInJail(false);
            resetJailTurns();
            return false;
        }

        System.out.println(getPlayerName() + " skips this turn in jail.");
        return true;
    }
}
