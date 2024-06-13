package nl.rug.oop.rts.graph;

import lombok.AllArgsConstructor;
import nl.rug.oop.rts.objects.Army;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a simulation of the game.
 */
@AllArgsConstructor
public class Simulation {
    private GraphManager graphManager;

    /**
     * Run a single step of the simulation.
     */
    public void step() {
        resolveBattles();
        moveArmiesNode();
        resetMoved(); // Remove this line to divide the move simulation into two steps
//        resolveBattles();
//        encounterRandomEvents();
        moveArmiesEdge();
        resetMoved();
//        resolveBattles();
//        encounterRandomEvents();
    }

    private void resolveBattles() {
        for (Node node : graphManager.getNodes()) {
            if (shouldBattle(node)) {
                System.out.println("Battle on node " + node);
//                node.battle();
            }
        }
    }

    private boolean shouldBattle(Node node) {
        // A battle occurs if there are more than one army and they are from different teams
        return node.getArmies().size() > 1 && node.getArmies().stream().map(Army::getTeam).distinct().count() > 1;
    }

    private void moveArmiesNode() {
        // Every army moves to a random edge connected to the node
        for (Node node : graphManager.getNodes()) {
            List<Army> armiesToMove = new ArrayList<>();
            for (Army army : node.getArmies()) {
                if (army.isMoved()) {
                    continue;
                }

                Edge randomEdge = node.getEdgeList().get((int) (Math.random() * node.getEdgeList().size()));
                Node randomNode = randomEdge.getOtherNode(node);
                army.setMovingToNextStep(randomNode);
                army.setMoved(true);
                randomEdge.getArmies().add(army);
                armiesToMove.add(army);
            }
            node.getArmies().removeAll(armiesToMove);
        }
        graphManager.modified();
    }

    private void moveArmiesEdge() {
        // Every army moves to their destination node
        for (Edge edge : graphManager.getEdges()) {
            List<Army> armiesToMove = new ArrayList<>();
            for (Army army : edge.getArmies()) {
                if (army.isMoved()) {
                    continue;
                }

                Node destination = army.getMovingToNextStep();
                destination.getArmies().add(army);
                armiesToMove.add(army);
            }
            edge.getArmies().removeAll(armiesToMove);
        }
        graphManager.modified();
    }

    private void resetMoved() {
        for (Node node : graphManager.getNodes()) {
            for (Army army : node.getArmies()) {
                army.setMoved(false);
            }
        }
        for (Edge edge : graphManager.getEdges()) {
            for (Army army : edge.getArmies()) {
                army.setMoved(false);
            }
        }
    }
}
