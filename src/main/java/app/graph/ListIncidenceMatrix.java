package app.graph;

import app.graph.Interface.IncidenceMatrix;
import lombok.Getter;

import java.util.*;

public class ListIncidenceMatrix<T> implements IncidenceMatrix<T> {

    @Getter
    private HashMap<T, List<IncidenceNode<T>>> incidenceMatrix;

    // used to track nodes that don't have incidence to others
    private HashMap<T, Void> endNodes;

    public ListIncidenceMatrix() {

        this.incidenceMatrix = new HashMap<>();
        this.endNodes = new HashMap<>();
    }

    public List<T> getAllVertexes() {
        var all = new ArrayList<T>();
        all.addAll(incidenceMatrix.keySet());
        all.addAll(endNodes.keySet());
        return all;
    }

    public List<IncidenceNode<T>> getAllIncidences(T node) {
        return incidenceMatrix.get(node);
    }

    public IncidenceNode<T> getIncidence(T node, T other) {
        return incidenceMatrix
                .getOrDefault(node, Collections.emptyList())
                .stream()
                .filter(p -> p.getNode().equals(other))
                .findFirst()
                .orElse(null);
    }

    public void addOrReplaceIncidenceNode(T from, IncidenceNode<T> incidenceNode) {

        ensureInitialListSpace(from);

        var incidence = getIncidence(from, incidenceNode.node);

        // don't have a list of incidences to that vertex yet so add one
        if (Objects.isNull(incidence)) {
            incidenceMatrix.get(from).add(incidenceNode);
        } else {
            incidence.node = incidenceNode.node;
            incidence.fromNode = incidenceNode.fromNode;
            incidence.cost = incidenceNode.cost;
        }

        endNodes.putIfAbsent(incidenceNode.node, null);
        endNodes.remove(incidenceNode.fromNode);
    }

    private void ensureInitialListSpace(T vertex) {
        incidenceMatrix.putIfAbsent(vertex, new ArrayList<>());
    }

}
