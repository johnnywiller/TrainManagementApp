package app.route_map;

import app.graph.IncidenceNode;
import app.graph.Interface.IncidenceMatrix;
import app.graph.Interface.PathTraversalEngine;
import app.graph.ListIncidenceMatrix;
import app.graph.MinimumPathBuilder;

import java.util.List;

public class RouteMapBuilder<T> {

    private List<StopHop<T>> hops;

    private MinimumPathBuilder<T> minimumPathBuilder;

    private PathTraversalEngine<T> pathTraversalEngine;

    public RouteMapBuilder(MinimumPathBuilder<T> pathBuilder, PathTraversalEngine<T> pathTraversalEngine) {
        this.minimumPathBuilder = pathBuilder;
        this.pathTraversalEngine = pathTraversalEngine;
    }

    public void addHops(List<StopHop<T>> hops) {
        this.hops = hops;
    }

    public RouteMap build() {

        // we are building incidence matrix two times, must provide a way to clone instead
        minimumPathBuilder.buildMinimumPath(buildIncidenceMatrix());

        pathTraversalEngine.setIncidenceMatrix(buildIncidenceMatrix());

        var routeMap = new RouteMap(minimumPathBuilder.getIncidenceMatrix(), pathTraversalEngine);

        return routeMap;
    }

    public IncidenceMatrix buildIncidenceMatrix() {

        var incidenceMatrix = new ListIncidenceMatrix<>();

        hops.stream()
                .forEach(hop -> incidenceMatrix
                        .addOrReplaceIncidenceNode(hop.from,
                                new IncidenceNode<>(hop.from, hop.to, hop.cost, 1)));

        return incidenceMatrix;
    }

}
