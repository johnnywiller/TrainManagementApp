package app.graph.Interface;

import app.graph.IncidenceNode;

import java.util.List;

public interface IncidenceMatrix<T> {

    List<T> getAllVertexes();

    List<IncidenceNode<T>> getAllIncidences(T node);

    IncidenceNode<T> getIncidence(T node, T other);

    void addOrReplaceIncidenceNode(T from, IncidenceNode<T> incidenceNode);
}
