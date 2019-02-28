package app.graph;

import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

public class IncidenceMatrix<T> {

    @Getter
    private HashMap<T, List<IncidenceNodeList<T>>> incidenceMatrix;

    // used to fast access all nodes
    // there are nodes who don't incide anyone
    private Set<T> allNodes;

    public IncidenceMatrix() {
        this.incidenceMatrix = new HashMap<>();
        this.allNodes = new HashSet<>();
    }

    public Set<T> getAllVertexes() {
        return allNodes;
    }

    public List<IncidenceNodeList<T>> getAllIncidences(T node) {
        return incidenceMatrix.get(node);
    }

    public Stream<Incidence<T>> incidences() {
        return incidenceMatrix.entrySet().stream().map(e -> new Incidence(e.getKey(), e.getValue()));
    }

    public IncidenceNodeList<T> getIncidenceList(T node, T other) {
        return incidenceMatrix
                .getOrDefault(node, Collections.emptyList())
                .stream()
                .filter(p -> p.getNode().equals(other))
                .findFirst()
                .orElse(null);
    }

    public void addIncidenceNode(T from, IncidenceNode<T> incidenceNode) {

        var incidence = incidenceMatrix
                .putIfAbsent(from, new LinkedHashSet<>(Arrays.asList(incidenceNode)));

        if (Objects.nonNull(incidence))
            incidence.add(incidenceNode);

        allNodes.add(from);
        allNodes.add(incidenceNode.node);
    }

    public void replaceIncidenceNode(T from, T to, IncidenceNode replace) {
        IncidenceNode incidence = getIncidence(from, to);
        incidence.fromNode = replace.fromNode;
        incidence.node = replace.node;
        incidence.cost = replace.cost;
    }

    public class Incidence<T> {
        @Getter
        T node;
        @Getter
        List<IncidenceNodeList<T>> incidences;

        public Incidence(T node, List<IncidenceNodeList<T>> incidences) {
            this.node = node;
            this.incidences = incidences;
        }
    }
}
