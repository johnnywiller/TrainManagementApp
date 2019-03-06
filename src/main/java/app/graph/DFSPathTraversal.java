package app.graph;

import app.graph.Interface.IncidenceMatrix;
import app.graph.Interface.PathTraversalEngine;
import app.graph.Interface.Vertex;
import app.routemap.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DFSPathTraversal<T extends Vertex> implements PathTraversalEngine<T> {

    private IncidenceMatrix<T> incidenceMatrix;

    public List<Route<T>> getPaths(PathTraversalConfiguration config) {

        ensureValidConfiguration(config);

        return buildPath(config);

    }

    private void ensureValidConfiguration(PathTraversalConfiguration config) {

        var allowed = (Objects.nonNull(config.getMaximumCost())) ||
                      (Objects.nonNull(config.getMaximumHops())) ||
                      (Objects.nonNull(config.getExactlyCost())) ||
                      (Objects.nonNull(config.getExactlyHops()));

        if (!allowed)
            throw new IllegalArgumentException("One of the upper bounds must be provided");

    }

    private List<Route<T>> buildPath(PathTraversalConfiguration config) {

        int hops = 0;

        int distance = 0;

        var routeStore = new ArrayList<Route<T>>();

        for (T v : incidenceMatrix.getAllVertexes()) {
            var stops = new ArrayList<T>();
            stops.add(v);
            traversePath(config, stops, routeStore, v, hops, distance);
        }

        return routeStore;
    }

    private boolean traversePath(PathTraversalConfiguration config, List<T> stops,
                                 List<Route<T>> routeStore,
                                 T node, int hops, int cost) {

        if (!ensureTraversalConditions(config, stops, hops, cost, routeStore.size()))
            return false;

        for (IncidenceNode<T> incidence : incidenceMatrix.getAllIncidences(node)) {

            T next = incidence.getNode();

            stops.add(next);

            var _hops = hops + 1;
            var _cost = cost + incidence.getCost();

            boolean canApplyRoute = traversePath(config, stops, routeStore, next, _hops, _cost);

            if (canApplyRoute && ensureFinalTraversalConditions(config, stops, _hops, _cost, routeStore.size()))
                aggregateRoute(stops, _cost, routeStore);

            stops.remove(stops.size() - 1);

        }
        return true;
    }

    private void aggregateRoute(List<T> stops, Integer totalCost, List<Route<T>> routeStore) {
        var route = new Route<T>();
        route.addAllNode(stops, totalCost);
        routeStore.add(route);
    }

    private boolean ensureTraversalConditions(PathTraversalConfiguration config,
                                              List<T> stops,
                                              int hops,
                                              int cost,
                                              int routeStoreSize) {

        if (Objects.nonNull(config.getBeginWith()))
            if (stops.indexOf(config.getBeginWith()) != 0)
                return false;

        if (Objects.nonNull(config.getBeginWithSequence())) {
            Object[] sequence = config.getBeginWithSequence();
            if (hops < sequence.length && !sequence[hops].equals(stops.get(hops)))
                return false;
        }

        if (Objects.nonNull(config.getMaximumCost()) &&
                cost > config.getMaximumCost())
            return false;

        if (Objects.nonNull(config.getExactlyCost()) &&
                cost > config.getExactlyCost())
            return false;

        if (Objects.nonNull(config.getMaximumHops()) &&
                hops > config.getMaximumHops())
            return false;

        if (Objects.nonNull(config.getExactlyHops()) &&
                hops > config.getExactlyHops())
            return false;

        if (Objects.nonNull(config.getLimit()) &&
                routeStoreSize == config.getLimit())
            return false;

        return true;
    }

    private boolean ensureFinalTraversalConditions(PathTraversalConfiguration config,
                                                   List<T> stops,
                                                   int hops,
                                                   int cost,
                                                   int routeStoreSize) {

        if (Objects.nonNull(config.getBeginWithSequence())) {
            Object[] sequence = config.getBeginWithSequence();
            if (hops < sequence.length - 1)
                return false;
        }

        if (Objects.nonNull(config.getEndWith()))
            if (stops.lastIndexOf(config.getEndWith()) != stops.size() - 1)
                return false;

        if (Objects.nonNull(config.getExactlyCost()) &&
                cost != config.getExactlyCost())
            return false;

        if (Objects.nonNull(config.getExactlyHops()) &&
                hops != config.getExactlyHops())
            return false;

        if (Objects.nonNull(config.getLimit()) &&
                routeStoreSize == config.getLimit())
            return false;

        return true;
    }

    @Override
    public void setIncidenceMatrix(app.graph.Interface.IncidenceMatrix<T> incidenceMatrix) {
        this.incidenceMatrix = incidenceMatrix;
    }
}


