package app.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class PathTraversalConfiguration<T> {

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
