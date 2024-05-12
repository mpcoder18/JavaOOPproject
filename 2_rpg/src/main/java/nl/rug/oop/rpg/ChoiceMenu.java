package nl.rug.oop.rpg;

import java.util.*;

public class ChoiceMenu {
    private final String prompt;
    private Map<Integer, Runnable> choices;
    private List<String> descriptions;

    public ChoiceMenu(String prompt) {
        this.prompt = prompt;
        choices = new HashMap<>();
        descriptions = new ArrayList<>();
    }

    public void run(Scanner scanner) {
        System.out.println(prompt);
        for (int i = 0; i < descriptions.size(); i++) {
            System.out.println("  (" + i + ") " + descriptions.get(i));
        }
        int choice = scanner.nextInt();
        if (choices.containsKey(choice)) {
            choices.get(choice).run();
        } else if (choice == -1) {
            return;
        } else {
            System.out.println("Invalid choice");
        }
    }

    public void addChoice(int index, Runnable choice, String description) {
        choices.put(index, choice);
        descriptions.add(description);
    }
}
