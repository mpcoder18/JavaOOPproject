package nl.rug.oop.rts;

import nl.rug.oop.rts.graph.model.GraphModel;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class to manage saving and loading the game.
 */
// TODO: Implement save manager by putting all relevant methods here
public class SaveManager {
    public SaveManager() {

    }

    public void saveGame(GraphModel model, String filePath) {
        JsonObject json = model.toJson();
        String jsonString = json.toJsonString();

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public GraphModel loadGame(String filePath) {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            JsonObject json = new JsonObject(jsonString);
//            return new GraphModel(json); // TODO: Implement GraphModel constructor
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
