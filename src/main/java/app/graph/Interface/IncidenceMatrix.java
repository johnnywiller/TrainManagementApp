package app.graph.Interface;

import app.graph.IncidenceNode;

import java.util.Set;

public interface IncidenceMatrix<T extends Vertex> {

    Set<T> getAllVertexes();

    Set<IncidenceNode<T>> getAllIncidences(T node);

    IncidenceNode<T> getIncidence(T node, T other);

    void addOrReplaceIncidenceNode(T from, IncidenceNode<T> incidenceNode);
}
