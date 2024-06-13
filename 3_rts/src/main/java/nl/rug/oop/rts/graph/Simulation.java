package nl.rug.oop.rts.graph;

import lombok.AllArgsConstructor;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Team;
import nl.rug.oop.rts.objects.Unit;

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
        resolveBattles();
//        encounterRandomEvents();
        moveArmiesEdge();
        resetMoved();
        resolveBattles();
//        encounterRandomEvents();
    }

    private void resolveBattles() {
        for (Node node : graphManager.getNodes()) {
            if (shouldBattle(node)) {
                resolveBattle(node.getArmies());
            }
        }
        for (Edge edge : graphManager.getEdges()) {
            if (shouldBattle(edge)) {
                resolveBattle(edge.getArmies());
            }
        }
    }

    private void resolveBattle(List<Army> armies) {
        List<Army> armiesTeamA = new ArrayList<>();
        List<Army> armiesTeamB = new ArrayList<>();
        for (Army army : armies) {
            if (army.getTeam() == Team.TEAM_A) {
                armiesTeamA.add(army);
            } else {
                armiesTeamB.add(army);
            }
        }
        // While each army has at least one unit
        while (!armiesTeamA.isEmpty() && !armiesTeamB.isEmpty()) {
            // Randomly select an army from each team
            Army armyA = armiesTeamA.get((int) (Math.random() * armiesTeamA.size()));
            Army armyB = armiesTeamB.get((int) (Math.random() * armiesTeamB.size()));

            resolveArmyBattle(armyA, armyB);

            // Remove armies with no units
            if (armyA.getUnits().isEmpty()) {
                armiesTeamA.remove(armyA);
                armies.remove(armyA);
            } else {
                armiesTeamB.remove(armyB);
                armies.remove(armyB);
            }
        }
        graphManager.modified();
    }

    private static void resolveArmyBattle(Army armyA, Army armyB) {
        while (!armyA.getUnits().isEmpty() && !armyB.getUnits().isEmpty()) {
            // Select the first unit from each army
            Unit unitA = armyA.getUnits().get(0);
            Unit unitB = armyB.getUnits().get(0);

            if (Math.random() < 0.5) {
                unitA.attack(unitB);
            } else {
                unitB.attack(unitA);
            }

            // Remove units with no health
            if (unitA.getHealth() <= 0) {
                armyA.getUnits().remove(unitA);
            } else if (unitB.getHealth() <= 0) {
                armyB.getUnits().remove(unitB);
            }
        }
    }

    private boolean shouldBattle(Selectable selectable) {
        // A battle occurs if there are more than one army and they are from different teams
        if (selectable instanceof Node node) {
            return node.getArmies().size() > 1 && node.getArmies().stream().map(Army::getTeam).distinct().count() > 1;
        } else {
            Edge edge = (Edge) selectable;
            return edge.getArmies().size() > 1 && edge.getArmies().stream().map(Army::getTeam).distinct().count() > 1;
        }
    }

    private void moveArmiesNode() {
        // Every army moves to a random edge connected to the node
        for (Node node : graphManager.getNodes()) {
            if (node.getEdgeList().isEmpty()) {
                continue;
            }
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
