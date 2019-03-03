package app.graph;

import app.graph.Interface.Vertex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
class DefaultIncidenceNode<T extends Vertex> extends IncidenceNode<T> {

    public DefaultIncidenceNode(T fromNode, T node, @NonNull Integer cost) {
        super(fromNode, node, cost);
        this.hops = 1;
    }

    public DefaultIncidenceNode(T fromNode, T node, @NonNull Integer cost, @NonNull Integer hops) {
        super(fromNode, node, cost);
        this.hops = hops;
    }

    @NonNull
    Integer hops;

    @Override
    public String toString() {
        return getFromNode() + "->" + getNode() + " " + getCost();
    }

}