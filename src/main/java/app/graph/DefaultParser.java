package app.graph;

import app.graph.Interface.GraphParser;
import app.graph.Interface.IncidenceMatrix;
import app.graph.Interface.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class DefaultParser<T extends Vertex> implements GraphParser<T> {

    private String graphRegEx = "(([a-zA-Z]{2}[\\d]{1,3},)|([a-zA-Z]{2}[\\d]{1,3})$)+"; // the OR is to match last group without comma

    private String nodesRegEx = "(([a-zA-Z],)|([a-zA-Z])$)+"; // the OR is to match last group without comma

    /**
     * Default implementation to parse a string graph
     * The graph must be in the form of (<ABN>,)* where:
     * A and B are a one letter vertex name and N is a positive integer
     * defining the edge weight between both.
     * Spaces between groups or letters are optional, since all spaces will be removed before testing for validity.
     * Example: CD4,CE2, EF2, EE3,AB2. This would update the incidence matrix with
     * incidences between vertexes A, B, C, D, E, F
     * @param graph a string like graph representation
     * @return The same instance of IncidenceMatrix provided by parameter with new values added by the graph
     */
    @Override
    public IncidenceMatrix<T> parseFromString(String graph, Supplier<T> supplier, IncidenceMatrix<T> incidenceMatrix) {

        Objects.requireNonNull(incidenceMatrix);
        Objects.requireNonNull(supplier);

        checkStringValidity(graph, graphRegEx);

        String[] groups = parseGroups(graph);

        for (String group : groups) {

            var v1 = supplier.get();
            var v2 = supplier.get();
            v1.setName(String.valueOf(group.charAt(0)));
            v2.setName(String.valueOf(group.charAt(1)));

            var weight = Integer.parseInt(group.substring(2));

            var incidence = new DefaultIncidenceNode<>(v1, v2, weight);

            incidenceMatrix.addOrReplaceIncidenceNode(v1, incidence);
        }

        return incidenceMatrix;
    }

    public List<T> parseNodes(String sNodes, Supplier<T> supplier) {

        var nodes = new ArrayList<T>();

        Objects.requireNonNull(supplier);

        checkStringValidity(sNodes, nodesRegEx);

        String[] groups = parseGroups(sNodes);

        for (String group : groups) {

            var node = supplier.get();
            node.setName(String.valueOf(group.charAt(0)));
            nodes.add(node);
        }

        return nodes;
    }

    private void checkStringValidity(String string, String regEx) {

        if (Objects.isNull(string) || string.isBlank()) {
            throw new IllegalArgumentException("Invalid graph");
        }

        string = string.replaceAll("\\s", "");

        if (!string.matches(regEx))
            throw new IllegalArgumentException("Invalid graph");

    }

    private String[] parseGroups(String graph) {
        return graph.replaceAll("\\s", "").split(",");
    }

}