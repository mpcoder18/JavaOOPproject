package nl.rug.oop.rts;

import lombok.NoArgsConstructor;
import nl.rug.oop.rts.graph.model.GraphModel;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class to manage saving and loading the game.
 */
@NoArgsConstructor
public class SaveManager {

    public void saveGame(GraphModel model, String filePath) {
        JsonObject json = model.toJson();
        String jsonString = json.toJsonString(1);

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
            System.out.println(jsonString);
            System.out.println(json.toJsonString(1));
            return new GraphModel(json.get("nodes"), json.get("edges"), json.get("simulationStep"), json.get("simulationSpeed"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
