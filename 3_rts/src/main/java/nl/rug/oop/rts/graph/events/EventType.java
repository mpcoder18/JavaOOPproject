package nl.rug.oop.rts.graph.events;

import lombok.AllArgsConstructor;

/**
 * An event type that can be executed on an army.
 */
@AllArgsConstructor
public enum EventType {
    REINFORCEMENTS("Additional troops have arrived! (+15 units)"),
    NATURAL_DISASTER("Natural Disaster! (-35% units)"),
    HIDDEN_WEAPONRY("Hidden Weaponry! (+1 damage)"),
    BLACK_PLAGUE("The Black Plague! (-50% units)"),
    BLESSING("The Gods have blessed a unit! (+1 giga unit)"),
    SABOTAGE("The enemy stole some of our weapons! (-2 damage)"),
    TRAINING("A unit has been trained! (+2 damage)"),
    SOUP("A good soup has been made! (+1 health)");

    private final String name;
}
