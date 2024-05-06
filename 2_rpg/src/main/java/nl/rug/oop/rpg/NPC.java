package nl.rug.oop.rpg;

/**
 * Class to represent NPCs.
 */
public class NPC implements Inspectable, Interactable {
    private String description;

    /**
     * Constructor to create a new NPC.
     * @param description Description of the NPC
     */
    public NPC(String description) {
        this.description = description;
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
}
