package nl.rug.oop.rpg;

/**
 * Class to represent NPCs.
 */
public class NPC implements Inspectable, Interactable {
    /**
     * Description of the NPC.
     */
    protected String description;
    /**
     * Damage the NPC can deal.
     */
    protected int damage;
    /**
     * Health of the NPC.
     */
    protected int health;

    /**
     * Constructor to create a new NPC.
     * @param description Description of the NPC
     * @param damage Damage the NPC can deal
     * @param health Health of the NPC
     */
    public NPC(String description, int damage, int health) {
        this.description = description;
        this.damage = damage;
        this.health = health;
    }

    /**
     * Method to inspect a NPC.
     */
    public void inspect() {
        System.out.println(description);
    }

    /**
     * Method to interact with an NPC.
     * @param player Player that interacts with the NPC
     */
    public void interact(Player player) {
        System.out.println("The creature is asleep so you can’t interact with it.");
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }
}
