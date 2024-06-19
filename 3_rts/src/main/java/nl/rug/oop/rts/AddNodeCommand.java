package nl.rug.oop.rts;

import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.model.GraphModel;

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

