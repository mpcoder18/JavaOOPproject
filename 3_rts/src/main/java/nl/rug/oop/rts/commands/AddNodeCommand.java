package nl.rug.oop.rts.commands;

import nl.rug.oop.rts.util.SoundPlayer;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.model.GraphModel;

/**
 * Command to add a node to the graph.
 */
public class AddNodeCommand implements Command {
    private final GraphModel model;
    private final Node node;

    public AddNodeCommand(GraphModel model, Node node) {
        this.model = model;
        this.node = node;
    }

    @Override
    public void execute() {
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.playSound("addNoise.wav");
        model.getNodes().add(node);
    }

    @Override
    public void undo() {
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.playSound("removeNoise.wav");
        model.getNodes().remove(node);
    }
}

