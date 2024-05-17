package nl.rug.oop.rpg.game;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rpg.menus.ChoiceMenu;
import nl.rug.oop.rpg.menus.SaveManager;
import nl.rug.oop.rpg.entities.NPC;
import nl.rug.oop.rpg.environment.Door;
import nl.rug.oop.rpg.environment.Room;
import nl.rug.oop.rpg.player.Player;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

/**
 * Class that contains the main game loop.
 */
public class Game implements Serializable {
    @Serial
    private static final long serialVersionUID = 1324293489028L;
    private final Player player;
    @Getter
    private transient Scanner scanner;
    @Getter
    @Setter
    private List<Room> rooms;
    private transient SaveManager saveManager;
    private boolean isRunning = true;

    /**
     * Constructor for the Game class.
     *
     * @param player  Player of the game
     * @param scanner Scanner to get input from the terminal
     * @param rooms   List of rooms in the game
     */
    public Game(Player player, Scanner scanner, List<Room> rooms) {
        this.player = player;
        this.scanner = scanner;
        this.rooms = rooms;
        saveManager = new SaveManager();
    }

    /**
     * Method to run the main game loop.
     */
    public void run() {
        System.out.println("Welcome to the game!");
        ChoiceMenu choiceMenu = new ChoiceMenu("What do you want to do? (-1 to do nothing)");
        choiceMenu.addChoice(0, () -> player.getCurrentRoom().inspect(), "Look around");
        choiceMenu.addChoice(1, player::checkInventory, "Check inventory");
        choiceMenu.addChoice(2, this::chooseDoor, "Look for a way out");
        choiceMenu.addChoice(3, this::interactWithNPC, "Look for company");
        choiceMenu.addChoice(4, this::quickSave, "Quick save");
        choiceMenu.addChoice(5, this::quickLoad, "Quick load");
        choiceMenu.addChoice(6, this::save, "Save");
        choiceMenu.addChoice(7, this::load, "Load");
        choiceMenu.addChoice(8, this::quitGame, "Quit game");
        while (isRunning) {
            choiceMenu.run(scanner);
        }
    }

    private void quickLoad() {
        Game loadedGame = saveManager.quickLoad();
        if (loadedGame == null) {
            System.out.println("No save file found.");
            return;
        }
        loadedGame.scanner = scanner;
        loadedGame.saveManager = saveManager;
        this.isRunning = false;
        loadedGame.run();
    }

    private void quickSave() {
        saveManager.quickSave(this);
    }

    private void save() {
        File saveFolder = new File("savedgames");
        if (!saveFolder.exists()) {
            boolean created = saveFolder.mkdir();
            if (!created) {
                System.out.println("Could not create save folder.");
                return;
            }
        }

        System.out.println("what will be the name of the saved game?");
        scanner.nextLine();
        String inputString = scanner.nextLine();
        // Check with regex if the input is a valid file name
        if (!inputString.matches("^[a-zA-Z0-9_]*$")) {
            System.out.println("Invalid file name. Only letters, numbers and underscores are allowed.");
            return;
        }
        // Check if the save already exists
        File[] savedgames = saveFolder.listFiles();
        assert savedgames != null;
        for (File file : savedgames) {
            if (file.getName().equals(inputString + ".save")) {
                System.out.println("Save already exists. Overwrite? (y/n)");
                String answer = scanner.next();
                if (!answer.matches("[yY]")) {
                    return;
                }
            }
        }

        File saveFile = new File(saveFolder, inputString + ".save");
        System.out.println("Saving game to " + saveFile.getAbsolutePath());
        saveManager.saveTo(saveFile, this);
    }

    private void load() {
        File saveFolder = new File("savedgames");
        if (!saveFolder.exists()) {
            System.out.println("No saves currently exists.");
            return;
        }
        File[] savedgames = saveFolder.listFiles();

        int counter = 1;
        assert savedgames != null;
        for (File file : savedgames) {
            System.out.println(counter + ". " + file.getName());
            counter++;
        }

        System.out.print("Save: ");
        int save = scanner.nextInt();

        File file = savedgames[save - 1];
        Game loadedGame = saveManager.loadFrom(file);
        if (loadedGame == null) {
            System.out.println("No save file found.");
            return;
        }
        loadedGame.scanner = scanner;
        loadedGame.saveManager = saveManager;
        this.isRunning = false;
        loadedGame.run();
    }

    private void chooseDoor() {
        ChoiceMenu choiceMenu = new ChoiceMenu("Which door do you want to take? (-1 to stay here)");
        List<Door> doors = player.getCurrentRoom().getDoors();
        int counter = 0;
        for (Door door : doors) {
            choiceMenu.addChoice(counter++, () -> door.interact(player), door.getDescription());
        }
        choiceMenu.addChoice(counter, () -> {
        }, "Stay here");
        choiceMenu.run(scanner);
    }

    private void interactWithNPC() {
        if (player.getCurrentRoom().getNpcs().isEmpty()) {
            System.out.println("There is no one to interact with.");
            return;
        }
        int counter = 0;
        ChoiceMenu choiceMenu = new ChoiceMenu("Which creature do you want to interact with? (-1 to go back)");
        for (NPC npc : player.getCurrentRoom().getNpcs()) {
            choiceMenu.addChoice(counter++, () -> npc.interact(player), npc.getDescription());
        }
        choiceMenu.run(scanner);
    }

    public void gameOver() {
        System.out.println("Game over");
        isRunning = false;
    }

    public void quitGame() {
        System.out.println("Goodbye!");
        isRunning = false;
    }

    public void removeNPC(NPC npc) {
        player.getCurrentRoom().getNpcs().remove(npc);
    }
}
