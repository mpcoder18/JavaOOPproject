package nl.rug.oop.rpg;

import java.util.*;

/**
 * Class to represent a choice menu.
 */
public class ChoiceMenu {
    private final String prompt;
    private final Map<Integer, Runnable> choices;
    private final List<String> descriptions;

    /**
     * Create a new choice menu.
     *
     * @param prompt Prompt to display to the user
     */
    public ChoiceMenu(String prompt) {
        this.prompt = prompt;
        choices = new HashMap<>();
        descriptions = new ArrayList<>();
    }

    /**
     * Run the choice menu.
     *
     * @param scanner Scanner to get user input
     */
    public void run(Scanner scanner) {
        System.out.println(prompt);
        int counter = 0;
        for (String description : descriptions) {
            System.out.println("  (" + counter++ + ") " + description);
        }
        int choice = scanner.nextInt();
        if (choices.containsKey(choice)) {
            choices.get(choice).run();
        } else if (choice == -1) {
            System.out.println("You do nothing.");
        } else {
            System.out.println("Invalid choice");
        }
    }

    public void addChoice(int index, Runnable choice, String description) {
        choices.put(index, choice);
        descriptions.add(description);
    }
}
