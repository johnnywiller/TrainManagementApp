package app.graph;

import app.graph.Interface.IncidenceMatrix;
import app.graph.Interface.Vertex;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ListIncidenceMatrix<T extends Vertex> implements IncidenceMatrix<T> {

    @Getter
    private HashMap<T, HashSet<IncidenceNode<T>>> incidenceMatrix;

    public ListIncidenceMatrix() {
        this.incidenceMatrix = new HashMap<>();
    }

    public Set<T> getAllVertexes() {
        var all = new HashSet<T>(incidenceMatrix.keySet());
        return all;
    }

    public Set<IncidenceNode<T>> getAllIncidences(T node) {
        return incidenceMatrix.get(node);
    }

    public IncidenceNode<T> getIncidence(T node, T other) {
        incidenceMatrix.getOrDefault(node, new HashSet<>()).stream().forEach(inc -> inc.getNode().equals(""));

        return incidenceMatrix
                .getOrDefault(node, new HashSet<>()) // empty set to avoid NPE on stream()

                .stream()

                .filter(p -> p.getNode().equals(other))
                .findFirst()
                .orElse(null);
    }

    public void addOrReplaceIncidenceNode(T from, IncidenceNode<T> incidenceNode) {

        ensureInitialListSpace(from);

        // if we only add the "from node" with could end up
        // with missing vertexes in the keys.
        // So we must ensure destination node is also on the map
        ensureInitialListSpace(incidenceNode.getNode());

        var incidence = getIncidence(from, incidenceNode.getNode());

        // don't have a list of incidences to that vertex yet so add one
        if (Objects.isNull(incidence)) {
            incidenceMatrix.get(from).add(incidenceNode);
        } else {
            incidence.setNode(incidenceNode.getNode());
            incidence.setFromNode(incidenceNode.getFromNode());
            incidence.setCost(incidenceNode.getCost());
        }
    }

    private void ensureInitialListSpace(T vertex) {
        incidenceMatrix.putIfAbsent(vertex, new HashSet<>());
    }

}
