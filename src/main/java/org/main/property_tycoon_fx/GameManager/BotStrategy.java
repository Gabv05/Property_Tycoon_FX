package org.main.property_tycoon_fx.GameManager;

public class BotStrategy {

    // Buy property if player has at least 25% more than the property cost
    public boolean shouldBuyProperty(Player player, Tile tile) {
        int playerMoney = player.getMoney();
        double cost = tile.getCost();
        return playerMoney >= cost * 1.25;
    }

    // Only upgrade if player has at least 25% more than the upgrade cost
    public boolean shouldUpgrade(Player player, Tile tile) {
        if (!tile.canUpgrade()) return false;

        // Placeholder: You can define upgrade cost differently (e.g., 50, 100, etc.)
        double upgradeCost = tile.getCost() * 0.5;  // Let's say upgrades are 50% of tile cost
        int playerMoney = player.getMoney();

        return playerMoney >= upgradeCost * 1.25;
    }
}
