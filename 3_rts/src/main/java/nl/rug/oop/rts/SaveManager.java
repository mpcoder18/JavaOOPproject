package nl.rug.oop.rts;

import lombok.NoArgsConstructor;
import nl.rug.oop.rts.graph.model.GraphModel;

import javax.swing.*;
import java.io.File;
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

    /**
     * Open a file chooser to save a game.
     *
     * @param model the state of the game
     * @param parentFrame the parent frame
     */
    public void saveGameChooser(GraphModel model, JFrame parentFrame) {
        boolean validName = false;
        while (!validName) {
            JFileChooser fileChooser = getjFileChooser();
            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.endsWith(".json")) {
                    filePath += ".json";
                }
                if (new File(filePath).exists()) {
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to replace the existing file?",
                            "Confirm Overwrite", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        validName = true;
                        System.out.println("Saving to " + filePath);
                        saveGame(model, filePath);
                    }
                } else {
                    validName = true;
                    System.out.println("Saving to " + filePath);
                    saveGame(model, filePath);
                }
            } else {
                validName = true; // User cancelled the file chooser, exit the loop
            }
        }
    }

    /**
     * Open a file chooser to load a game.
     *
     * @param parentFrame the parent frame
     * @return the loaded game
     */
    public GraphModel loadGameChooser(JFrame parentFrame) {
        boolean validName = false;
        while (!validName) {
            JFileChooser fileChooser = getjFileChooser();
            int userSelection = fileChooser.showOpenDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                String filePath = fileToLoad.getAbsolutePath();
                if (filePath.endsWith(".json")) {
                    validName = true;
                    System.out.println("Loading from " + filePath);
                    return loadGame(filePath);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a JSON file", "Invalid file",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                validName = true; // User cancelled the file chooser, exit the loop
            }
        }
        return null;
    }

    private JFileChooser getjFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save graph");
        fileChooser.setSelectedFile(new File("graph.json"));

        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".json") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "JSON files";
            }
        });
        return fileChooser;
    }
}