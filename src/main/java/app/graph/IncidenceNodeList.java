package app.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class IncidenceNodeList<T> {

    @Getter
    private T node;

    @Getter
    private List<IncidenceNode<T>> incidencesNodes;

    @Getter
    @Setter
    private IncidenceNode<T> minimumIncidence;

    public IncidenceNodeList(T node) {
        this.node = node;
        this.incidencesNodes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return incidencesNodes.stream().map(node -> node.toString()).collect(Collectors.joining(","));
    }
}
