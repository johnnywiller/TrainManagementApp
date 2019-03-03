package app.graph;

import app.graph.Interface.Vertex;
import lombok.Data;
import lombok.NonNull;

@Data
public abstract class IncidenceNode<T extends Vertex> {

    @NonNull
    T fromNode;
    @NonNull
    T node;

    // TODO consider using a Comparable Interface,
    // but also will need to add a 'Addable' Interface,
    // for the reasons that costs are summed up together
    @NonNull
    Integer cost;

}
