package app.route_map;

import app.graph.Interface.IncidenceMatrix;
import app.graph.Interface.PathTraversalEngine;
import app.graph.Interface.Vertex;
import app.graph.PathTraversalConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class RouteMap<T extends Vertex> {

    @Getter
    private IncidenceMatrix<T> minimumIncidenceMatrix;

    private PathTraversalEngine<T> pathTraversal;

    public List<Route<T>> getRoutes(PathTraversalConfiguration<T> config) {
        return pathTraversal.getPaths(config);
    }

    public Integer minimumDistanceFrom(T from, T to) {
        return minimumIncidenceMatrix.getIncidence(from, to).getCost();
    }




}
