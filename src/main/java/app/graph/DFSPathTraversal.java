package app.graph;

import app.Route;
import app.graph.Interface.IncidenceMatrix;
import app.graph.Interface.PathTraversalEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DFSPathTraversal<T> implements PathTraversalEngine<T> {

    private IncidenceMatrix<T> incidenceMatrix;

    public List<Route<T>> getPaths(PathTraversalConfiguration config) {

        ensureValidConfiguration(config);

        buildPath(config);

        return null;
    }

    private void ensureValidConfiguration(PathTraversalConfiguration config) {

    }

    private void buildPath(PathTraversalConfiguration config) {

        int hops = 0;

        int distance = 0;

        for (T v : incidenceMatrix.getAllVertexes()) {
            var stops = new ArrayList<T>();
            stops.add(v);
            traversePath(config, stops, v, hops, distance);
        }
    }

    private boolean traversePath(PathTraversalConfiguration config, List<T> stops,
                                 T node, int hops, int distance) {

        if (!ensureTraversalConditions(config, stops, hops, distance))
            return false;

        for (IncidenceNode<T> incidence : incidenceMatrix.getAllIncidences(node)) {

            T next = incidence.node;

            stops.add(next);

            var _hops = hops + 1;
            var _distance = distance + incidence.cost;

            boolean canApplyRoute = traversePath(config, stops, next, _hops, _distance);

            if (canApplyRoute && ensureFinalTraversalConditions(config, stops, _hops, _distance))
                System.out.println(stops);

            stops.remove(stops.size() - 1);

        }
        return true;
    }



    private boolean ensureTraversalConditions(PathTraversalConfiguration config,
                                              List<T> stops,
                                              int hops,
                                              int cost) {

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
                stops.size() > config.getLimit())
            return false;

        return true;
    }

    private boolean ensureFinalTraversalConditions(PathTraversalConfiguration config,
                                                   List<T> stops,
                                                   int hops,
                                                   int cost) {

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

        return true;
    }

    @Override
    public void setIncidenceMatrix(app.graph.Interface.IncidenceMatrix<T> incidenceMatrix) {
        this.incidenceMatrix = incidenceMatrix;
    }
}


