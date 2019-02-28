package app.graph;

import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

public class SimpleIncidenceMatrix<T> {
//
//    @Getter
//    private HashMap<T, List<IncidenceNode<T>>> incidenceMatrix;
//
//    // used to fast access all nodes
//    // there are nodes who don't incide anyone
//    private ArrayList<T> allNodes;
//
//    public SimpleIncidenceMatrix() {
//        this.incidenceMatrix = new HashMap<>();
//        this.allNodes = new ArrayList<>();
//    }
//
//    public ArrayList<T> getAllVertexes() {
//        return allNodes;
//    }
//
//    public List<IncidenceNodeList<T>> getAllIncidences(T node) {
//        return incidenceMatrix.get(node);
//    }
//
//    public Stream<Incidence<T>> incidences() {
//        return incidenceMatrix.entrySet().stream().map(e -> new Incidence(e.getKey(), e.getValue()));
//    }
//
//    public IncidenceNodeList<T> getIncidenceList(T node, T other) {
//        return incidenceMatrix
//                .getOrDefault(node, Collections.emptyList())
//                .stream()
//
//                .filter(p -> p.getNode().equals(other))
//                .findFirst()
//                .orElse(null);
//    }
//
//    public void addIncidenceNode(T from, IncidenceNode<T> incidenceNode) {
//
//        ensureInitialListSpace(from);
//
//        var incidenceList = getIncidenceList(from, incidenceNode.node);
//
//        // don't have a list of incidences to that vertex yet so add one
//        if (Objects.isNull(incidenceList)) {
//            incidenceList = new IncidenceNodeList<T>(incidenceNode.node);
////            incidenceMatrix.get(from).add(incidenceList);
//        }
//
//        var minimum = incidenceList.getMinimumIncidence();
//
//        if (Objects.isNull(minimum) || minimum.cost > incidenceNode.cost) {
//            incidenceList.setMinimumIncidence(incidenceNode);
//        }
//
//        incidenceList.getIncidencesNodes().add(incidenceNode);
//
//        if (!allNodes.contains(from)) {
//            allNodes.add(from);
//        }
//
//        if (!allNodes.contains(incidenceNode.node)) {
//            allNodes.add(incidenceNode.node);
//        }
//    }
//
//    private void ensureInitialListSpace(T vertex) {
//        incidenceMatrix.putIfAbsent(vertex, new ArrayList<>());
//    }
//
//    public void replaceIncidenceNode(T from, T to, IncidenceNode replace) {
////        IncidenceNode incidence = getIncidence(from, to);
////        incidence.fromNode = replace.fromNode;
////        incidence.node = replace.node;
////        incidence.cost = replace.cost;
//    }
//
//    public class Incidence<T> {
//        @Getter
//        T node;
//        @Getter
//        List<IncidenceNodeList<T>> incidences;
//
//        public Incidence(T node, List<IncidenceNodeList<T>> incidences) {
//            this.node = node;
//            this.incidences = incidences;
//        }
//
//        public String toString() {
//            return node.toString();
//        }
//    }
}
