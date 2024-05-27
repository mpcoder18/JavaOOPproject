package nl.rug.oop.rts;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GraphManager implements Observable {
    @Getter
    private List<Node> nodes;
    @Getter
    private List<Edge> edges;
    private List<Observer> observers;

    public GraphManager() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void addNode(Node node){
        nodes.add(node);

    }

    public void removeNode(Node node) {
        for(Edge edge : node.getEdgeList()) {
            edges.remove(edge);
        }
        nodes.remove(node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update();
        }
    }
}
