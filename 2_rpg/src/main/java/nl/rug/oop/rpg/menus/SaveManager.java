package nl.rug.oop.rpg.menus;

import nl.rug.oop.rpg.game.Game;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Class that manages saving and loading the game.
 */
public class SaveManager {
    public SaveManager() {
    }

    /**
     * Saves the game to a file called "quicksave.ser".
     *
     * @param game the game to save
     */
    public void quickSave(Game game) {
        File saveDir = new File("savedgames");
        if (!saveDir.exists()) {
            boolean created = saveDir.mkdir();
            if (!created) {
                System.out.println("Could not create save folder.");
                return;
            }
        }
        File saveFile = new File("savedgames/quicksave.ser");
        saveTo(saveFile, game);

    }

    /**
     * Loads the game from a file called "quicksave.ser".
     *
     * @return the loaded game
     */
    public Game quickLoad() {
        File saveFile = new File("savedgames/quicksave.ser");
        return loadFrom(saveFile);
    }

    /**
     * Saves the game to a file.
     *
     * @param file the file to save to
     * @param game the game to save
     */
    public void saveTo(File file, Game game) {
        try (FileOutputStream fos = new FileOutputStream(file);
             GZIPOutputStream zipStream = new GZIPOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(zipStream)) {
            oos.writeObject(game);

            System.out.println("Game saved to " + file.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the game from a file.
     *
     * @param file the file to load from
     * @return the loaded game
     */
    public Game loadFrom(File file) {
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream fis = new FileInputStream(file);
             GZIPInputStream zipStream = new GZIPInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(zipStream)) {
            Game game = (Game) ois.readObject();
            System.out.println("Game loaded from " + file.getName());
            return game;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
