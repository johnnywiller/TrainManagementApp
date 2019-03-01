package app;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Route<T> {

    private String name;

    private List<T> nodes;

    private Integer totalCost = 0;

    public Route(String name) {
        this.name = name;
        this.nodes = new ArrayList<>();
    }

    public void addNode(T node, Integer cost) {
        nodes.add(node);
        totalCost += cost;
    }

    @Override
    public String toString() {
        return nodes.stream().map(n -> n.toString()).collect(Collectors.joining(" -> ")) + " [Cost: " + totalCost + "]";
    }

}
