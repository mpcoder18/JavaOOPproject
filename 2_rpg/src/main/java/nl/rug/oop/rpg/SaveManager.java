package nl.rug.oop.rpg;

import nl.rug.oop.rpg.entities.NPC;
import nl.rug.oop.rpg.environment.Room;
import nl.rug.oop.rpg.game.Game;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;



public class SaveManager {
    public SaveManager() {
    }

    public void quickSave(Game game) {
        File saveDir = new File("savedgames");
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File saveFile = new File("savedgames/quicksave.ser");
        saveTo(saveFile, game);

    }

    public Game quickLoad() {
        File saveFile = new File("savedgames/quicksave.ser");
        return loadFrom(saveFile);
    }

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
