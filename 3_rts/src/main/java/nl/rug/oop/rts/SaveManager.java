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

    /**
     * Save a game to a file.
     *
     * @param model    the game to save
     * @param filePath the file path
     */
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

    /**
     * Load a game from a file.
     *
     * @param filePath the file path
     * @return the loaded game
     */
    public GraphModel loadGame(String filePath) {
        try {
            JsonParser parser = new JsonParser();
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            JsonObject json = (JsonObject) parser.parse(jsonString);
            return new GraphModel(json.get("NodeSize"), json.get("SimulationStep"),
                    (JsonList) json.get("Edges"), (JsonList) json.get("Nodes"), (JsonList) json.get("EventRecords"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public void saveGameChooser()
}
