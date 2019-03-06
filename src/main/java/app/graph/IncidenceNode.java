package app.graph;

import app.graph.Interface.Vertex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
public abstract class IncidenceNode<T extends Vertex> {

    @NonNull
    private T fromNode;
    @NonNull
    private T node;

    // TODO consider using a Comparable Interface,
    // but also will need to add a 'Addable' Interface,
    // for the reasons that costs are summed up together
    @NonNull
    private Integer cost;

}
