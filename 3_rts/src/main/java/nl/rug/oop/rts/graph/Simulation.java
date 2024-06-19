package nl.rug.oop.rts.graph;

import lombok.AllArgsConstructor;
import nl.rug.oop.rts.graph.controller.GraphController;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventRecord;
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
    private GraphController graphController;

    private void resolveArmyBattle(Army armyA, Army armyB) {
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
        graphController.setSimulationStep(graphController.getSimulationStep() + 1);
        graphController.getModel().notifyAllObservers();
    }

    private void resolveBattles() {
        for (Node node : graphController.getNodes()) {
            if (shouldBattle(node)) {
                resolveBattle(node.getArmies(), node);
            }
        }
        for (Edge edge : graphController.getEdges()) {
            if (shouldBattle(edge)) {
                resolveBattle(edge.getArmies(), edge);
            }
        }
    }

    private void resolveBattle(List<Army> armies, Selectable selectable) {
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
                graphController.removeArmy(armyA, selectable);
            } else {
                armiesTeamB.remove(armyB);
                graphController.removeArmy(armyB, selectable);
            }
        }
        graphController.getModel().notifyAllObservers();
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
        for (Node node : graphController.getNodes()) {
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
        graphController.getModel().notifyAllObservers();
    }

    private void moveArmiesEdge() {
        // Every army moves to their destination node
        for (Edge edge : graphController.getEdges()) {
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
        graphController.getModel().notifyAllObservers();
    }

    private void resetState() {
        for (Node node : graphController.getNodes()) {
            resetAllArmiesSelectable(node.getArmies(), node);
        }
        for (Edge edge : graphController.getEdges()) {
            resetAllArmiesSelectable(edge.getArmies(), edge);
        }
    }

    private void resetAllArmiesSelectable(List<Army> armies, Selectable selectable) {
        List<Army> toRemove = new ArrayList<>();
        for (Army army : armies) {
            army.setMoved(false);
            if (army.getUnits().isEmpty()) {
                toRemove.add(army);
            }
        }
        armies.removeAll(toRemove);
        selectable.getArmies().removeAll(toRemove);
    }

    /**
     * Encounter random events on all nodes and edges.
     */
    public void encounterRandomEvents() {
        List<EventRecord> events = new ArrayList<>();
        for (Node node : graphController.getNodes()) {
            for (Army army : node.getArmies()) {
                events.add(encounterRandomEvent(army, node));
            }
        }
        for (Edge edge : graphController.getEdges()) {
            for (Army army : edge.getArmies()) {
                events.add(encounterRandomEvent(army, edge));
            }
        }
        for (EventRecord event : events) {
            if (event != null) {
                graphController.getEventRecords().add(0, event);
            }
        }
    }

    /**
     * Randomly select an event to execute.
     *
     * @param army      The army to execute the event on.
     * @param selectable The selectable to select the event from.
     * @return The event record of the event executed.
     */
    public EventRecord encounterRandomEvent(Army army, Selectable selectable) {
        if (Math.random() < 0.5) {
            if (!selectable.getEvents().isEmpty()) {
                Event event = selectable.getEvents().get((int) (Math.random() * selectable.getEvents().size()));
                event.execute(army);
                return new EventRecord(event, selectable, graphController.getSimulationStep());
            }
        }
        return null;
    }
}
