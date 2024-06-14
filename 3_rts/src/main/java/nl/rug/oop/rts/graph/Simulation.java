package nl.rug.oop.rts.graph;

import lombok.AllArgsConstructor;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventRecord;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Team;
import nl.rug.oop.rts.objects.Unit;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        resetState();
        resolveBattles();
        encounterRandomEvents();
        moveArmiesEdge();
        resetState();
        resolveBattles();
        encounterRandomEvents();
        resetState();
        graphManager.setSimulationStep(graphManager.getSimulationStep() + 1);
        graphManager.modified();
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

    private void resetState() {
        for (Node node : graphManager.getNodes()) {
            List<Army> toRemove = new ArrayList<>();
            for (Army army : node.getArmies()) {
                army.setMoved(false);
                if (army.getUnits().isEmpty()) {
                    toRemove.add(army);
                }
            }
            node.getArmies().removeAll(toRemove);
        }
        for (Edge edge : graphManager.getEdges()) {
            List<Army> toRemove = new ArrayList<>();
            for (Army army : edge.getArmies()) {
                army.setMoved(false);
                if (army.getUnits().isEmpty()) {
                    toRemove.add(army);
                }
            }
            edge.getArmies().removeAll(toRemove);
        }
    }

    public void encounterRandomEvents() {
        List<EventRecord> events = new ArrayList<>();
        for (Node node : graphManager.getNodes()) {
            for (Army army : node.getArmies()) {
                events.add(encounterRandomEvent(army, node));
            }
        }
        for (Edge edge : graphManager.getEdges()) {
            for (Army army : edge.getArmies()) {
                events.add(encounterRandomEvent(army, edge));
            }
        }
        for (EventRecord event : events) {
            if (event != null) {
                graphManager.getEvents().add(0, event);
            }
        }
    }

    public EventRecord encounterRandomEvent(Army army, Selectable selectable) {
        if (Math.random() < 0.5) {
            if (!selectable.getEvents().isEmpty()) {
                Event event = selectable.getEvents().get((int) (Math.random() * selectable.getEvents().size()));
                event.execute(army);
                return new EventRecord(event, graphManager.getSimulationStep());
            }
        }
        return null;
    }
}
