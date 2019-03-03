package app.graph;

import app.graph.Interface.Vertex;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PathTraversalConfiguration<T extends Vertex> {

    private T beginWith;

    private T endWith;

    private T[] beginWithSequence;

    private Integer minimumCost;

    private Integer maximumCost;

    private Integer minimumHops;

    private Integer maximumHops;

    private Integer exactlyCost;

    private Integer exactlyHops;

    private Integer limit;

}
