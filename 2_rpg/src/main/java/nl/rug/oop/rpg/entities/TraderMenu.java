package nl.rug.oop.rpg.entities;

import nl.rug.oop.rpg.ChoiceMenu;
import nl.rug.oop.rpg.inventory.Item;
import nl.rug.oop.rpg.inventory.items.Sword;
import nl.rug.oop.rpg.inventory.items.armor.Boots;
import nl.rug.oop.rpg.inventory.items.armor.Chestplate;
import nl.rug.oop.rpg.inventory.items.armor.Helmet;
import nl.rug.oop.rpg.inventory.items.armor.Leggings;
import nl.rug.oop.rpg.player.Player;

import java.util.List;

/**
 * Class that represents the trader menu.
 */
public class TraderMenu {
    private final List<Item> inventory;
    private final Trader trader;

    /**
     * Constructor to create a new trader menu.
     *
     * @param inventory Inventory of the trader
     * @param trader    Trader that the player interacts with
     */
    public TraderMenu(Trader trader, List<Item> inventory) {
        this.trader = trader;
        this.inventory = inventory;
    }

    /**
     * Method to buy an item from the player.
     *
     * @param player Player that sells the item
     * @param item   Item that the player sells
     */
    public void buyItem(Player player, Item item) {
        player.getInventory().remove(item);
        player.setMoney(player.getMoney() + item.getValue());
        System.out.println("You sold the " + item.getName() + " for " + item.getValue() + " gold.");
    }

    /**
     * Method to sell an item to the player.
     *
     * @param player Player that buys the item
     * @param item   Item that the player buys
     */
    public void sellItem(Player player, Item item) {
        if (player.getMoney() < item.getValue()) {
            System.out.println("You don't have enough money to buy the " + item.getName() + ".");
            return;
        }
        inventory.remove(item);
        player.getInventory().add(item);
        player.setMoney(player.getMoney() - item.getValue());
        player.autoEquipBest();
        System.out.println("You bought the " + item.getName() + " for " + item.getValue() + " gold.");
    }

    /**
     * Method to display the buy menu.
     *
     * @param player Player that interacts with the trader
     */
    void buyMenu(Player player) {
        ChoiceMenu buyMenu = new ChoiceMenu("What do you want to buy?");
        for (int i = 0; i < inventory.size(); i++) {
            int finalI = i;
            String description = getDescription(trader, i);
            buyMenu.addChoice(i, () -> sellItem(player, inventory.get(finalI)),
                    description + " - " + inventory.get(i).getValue() + " gold");
        }
        buyMenu.addChoice(inventory.size(), () -> {}, "Exit");
        buyMenu.run(player.getGame().getScanner());
    }

    /**
     * Method to display the sell menu.
     *
     * @param player Player that interacts with the trader
     */
    public void sellMenu(Player player) {
        ChoiceMenu sellMenu = new ChoiceMenu("What do you want to sell?");
        for (int i = 0; i < player.getInventory().size(); i++) {
            int finalI = i;
            String description = getDescription(player, i);
            sellMenu.addChoice(i, () -> buyItem(player, player.getInventory().get(finalI)),
                    description + " - " + player.getInventory().get(i).getValue() + " gold");
        }
        sellMenu.addChoice(player.getInventory().size(), () -> {}, "Exit");
        sellMenu.run(player.getGame().getScanner());
    }

    private String getDescription(Player player, int i) {
        return getString(i, player.getInventory(), this.trader);
    }

    private String getDescription(Trader trader, int i) {
        return getString(i, trader.getInventory(), trader);
    }

    private String getString(int i, List<Item> inventory, Trader trader) {
        String description = inventory.get(i).getName();
        return switch (description) {
            case "Sword" ->  description + " (" + ((Sword) inventory.get(i)).getDamage() + " damage)";
            case "Helmet" -> description + " (" + ((Helmet) inventory.get(i)).getDefense() + " defense)";
            case "Chestplate" -> description + " (" + ((Chestplate) inventory.get(i)).getDefense() + " defense)";
            case "Leggings" -> description + " (" + ((Leggings) inventory.get(i)).getDefense() + " defense)";
            case "Boots" -> description + " (" + ((Boots) inventory.get(i)).getDefense() + " defense)";
            default -> "";
        };
    }
}
