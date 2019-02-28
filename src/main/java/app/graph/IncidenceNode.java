package app.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
public class IncidenceNode<T> {

    @NonNull
    T fromNode;
    @NonNull
    T node;
    @NonNull
    Integer cost;
    @NonNull
    Integer hops;

    @Override
    public String toString() {
        return fromNode + "->" + node + " " + cost;
    }

}