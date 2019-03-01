package app.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.function.ToDoubleBiFunction;

@Data
@EqualsAndHashCode
public class IncidenceNode<T> {

    @NonNull
    T fromNode;
    @NonNull
    T node;

    // TODO consider using a Comparable Interface,
    // but also will need to add a 'Addable' Interface,
    // for the reasons that costs are summed up together
    @NonNull
    Integer cost;
    @NonNull
    Integer hops;

    @Override
    public String toString() {
        return fromNode + "->" + node + " " + cost;
    }

}