package app.graph;

import app.graph.Interface.GraphParser;
import app.graph.Interface.IncidenceMatrix;
import app.graph.Interface.Vertex;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultParser<T extends Vertex> implements GraphParser<T> {

    private IncidenceMatrix<T> incidenceMatrix;

    /**
     * @throws NullPointerException if {@code incidenceMatrix} is {@code null}
     * @param incidenceMatrix
     */
    public DefaultParser(IncidenceMatrix<T> incidenceMatrix) {
        this.incidenceMatrix = Objects.requireNonNull(incidenceMatrix);
    }


    /**
     * Default implementation to parse a string graph
     * The graph must be in the form of (<ABN>,)* where:
     * A and B are a one letter vertex name and N is a positive integer
     * defining the edge weight between both.
     * Spaces between groups or letters are optional, since all spaces will be removed before testing for validity.
     * Example: CD4,CE2, EF2, EE3,AB2. This would update the incidence matrix with
     * incidences between vertexes A, B, C, D, E, F
     * @param graph a string like graph representation
     * @return an updated IncidenceMatrix provided in this class's constructor
     */
    @Override
    public IncidenceMatrix<T> parseFromString(String graph, Supplier<T> supplier) {

        checkStringValidity(graph);

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

    private void checkStringValidity(String graph) {

        if (Objects.isNull(graph) || graph.isBlank()) {
            throw new IllegalArgumentException("Invalid graph");
        }

        graph = graph.replaceAll("\\s", "");

        if (!graph.matches("([a-zA-Z]{2}[\\d]{1,3},?)+"))
            throw new IllegalArgumentException("Invalid graph");

    }

    private String[] parseGroups(String graph) {
        return graph.replaceAll("\\s", "").split(",");
    }

}