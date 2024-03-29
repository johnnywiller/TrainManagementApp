package app.routemap;

import app.graph.Interface.Vertex;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Route<T extends Vertex> {

    private String name;

    @EqualsAndHashCode.Include
    private List<T> nodes;

    @EqualsAndHashCode.Include
    private Integer totalCost = 0;

    public Route(String name) {
        this.name = name;
        this.nodes = new ArrayList<>();
    }

    public Route() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(T node, Integer cost) {
        nodes.add(node);
        totalCost += cost;
    }

    public void addAllNode(List<T> nodes, Integer totalCost) {
        this.nodes.addAll(nodes);
        this.totalCost += totalCost;
    }



    @Override
    public String toString() {
        return nodes.stream().map(n -> n.toString()).collect(Collectors.joining(" -> ")) + " [Cost: " + totalCost + "]";
    }

}
