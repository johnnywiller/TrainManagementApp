package app.routemap;

import app.graph.DefaultParser;
import app.graph.Interface.*;
import app.graph.ListIncidenceMatrix;

import java.util.function.Supplier;

public class RouteMapBuilder<T extends Vertex> {

    private MinimumPathBuilder<T> minimumPathBuilder;

    private PathTraversalEngine<T> pathTraversalEngine;

    private GraphParser<T> graphParser = new DefaultParser<>();

    private IncidenceMatrix<T> minimumIncidenceMatrix;

    private IncidenceMatrix<T> traversalIncidenceMatrix;

    public RouteMapBuilder(MinimumPathBuilder<T> pathBuilder, PathTraversalEngine<T> pathTraversalEngine) {
        this.minimumPathBuilder = pathBuilder;
        this.pathTraversalEngine = pathTraversalEngine;
    }

    public RouteMap build() {

        // we are building incidence matrix two times, must provide a way to clone instead
        minimumPathBuilder.buildMinimumPath(minimumIncidenceMatrix);

        pathTraversalEngine.setIncidenceMatrix(traversalIncidenceMatrix);

        var routeMap = new RouteMap(minimumPathBuilder.getIncidenceMatrix(), pathTraversalEngine);

        return routeMap;
    }

    public RouteMapBuilder<T> fromString(String graph, Supplier<T> supplier) {

        // we compute incidence matrix 2 times because of different use cases for both
        // TODO consider implement ListIncidenceMatrix#clone()
        this.minimumIncidenceMatrix = graphParser
                .parseFromString(graph, supplier, new ListIncidenceMatrix<>());

        this.traversalIncidenceMatrix = graphParser
                .parseFromString(graph, supplier, new ListIncidenceMatrix<>());

        return this;

    }
}
