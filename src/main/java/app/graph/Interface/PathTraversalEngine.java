package app.graph.Interface;

import app.graph.PathTraversalConfiguration;
import app.route_map.Route;

import java.util.List;

public interface PathTraversalEngine<T extends Vertex> {

    void setIncidenceMatrix(IncidenceMatrix<T> incidenceMatrix);

    List<Route<T>> getPaths(PathTraversalConfiguration config);

}
