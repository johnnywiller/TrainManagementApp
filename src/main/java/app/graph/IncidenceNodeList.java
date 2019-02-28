package app.graph;

import lombok.Getter;

import java.util.List;

public class IncidenceNodeList<T> {

    @Getter
    private T node;

    @Getter
    private List<IncidenceNode<T>> incidencesNodes;

    @Getter
    private IncidenceNode<T> minimumIncidence;
}
