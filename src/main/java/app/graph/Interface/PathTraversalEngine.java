package app.graph.Interface;

import app.Route;
import app.graph.PathTraversalConfiguration;

import java.util.List;

public interface PathTraversalEngine<T> {

    void setIncidenceMatrix(IncidenceMatrix<T> incidenceMatrix);

    List<Route<T>> getPaths(PathTraversalConfiguration config);

}
