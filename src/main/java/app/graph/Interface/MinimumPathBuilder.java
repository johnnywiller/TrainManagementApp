package app.graph.Interface;

public interface MinimumPathBuilder<T extends Vertex> {

    void buildMinimumPath(IncidenceMatrix<T> incidenceMatrix);

    IncidenceMatrix<T> getIncidenceMatrix();
}
