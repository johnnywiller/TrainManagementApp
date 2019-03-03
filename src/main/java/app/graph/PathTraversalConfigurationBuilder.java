package app.graph;

import app.graph.Interface.Vertex;
import lombok.ToString;

import java.util.Objects;

@ToString
public class PathTraversalConfigurationBuilder<T extends Vertex> {

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

    private static final Integer DEFAULT_COST = 20;

    private static final Integer MINIMUM_COST = 0;

    private static final Integer DEFAULT_HOPS = 10;

    private static final Integer MINIMUM_HOPS = 0;

    private PathTraversalConfigurationBuilder() {
    }

    public PathTraversalConfiguration<T> build() {
        return new PathTraversalConfiguration<>(beginWith,
                endWith,
                beginWithSequence,
                minimumCost,
                maximumCost,
                minimumHops,
                maximumHops,
                exactlyCost,
                exactlyHops,
                limit);
    }

    public static PathTraversalConfigurationBuilder emptyConfiguration() {
        PathTraversalConfigurationBuilder builder = new PathTraversalConfigurationBuilder<>();
        return builder;
    }

    public static PathTraversalConfigurationBuilder defaultConfiguration() {
        PathTraversalConfigurationBuilder builder = new PathTraversalConfigurationBuilder();
        builder.maximumCost = DEFAULT_COST;
        return builder;
    }

    public static PathTraversalConfigurationBuilder defaultConfiguration(Integer cost) {
        ensureMinimumCost(cost);
        PathTraversalConfigurationBuilder builder = new PathTraversalConfigurationBuilder();
        builder.maximumCost = cost;
        return builder;
    }

    public PathTraversalConfigurationBuilder<T> beginWith(T node) {
        ensureNonNullNode(node);
        this.beginWith = node;
        return this;
    }

    public PathTraversalConfigurationBuilder<T> endWith(T node) {
        ensureNonNullNode(node);
        this.endWith = node;
        return this;
    }

    public PathTraversalConfigurationBuilder<T> beginWithSequence(T... nodes) {
        ensureNonNullNode(nodes);
        this.beginWithSequence = nodes;
        return this;
    }

    public PathTraversalConfigurationBuilder<T> withExactlySequence(T... nodes) {
        ensureNonNullNode(nodes);
        this.beginWithSequence = nodes;
        this.exactlyHops = nodes.length - 1;
        return this;
    }

    public PathTraversalConfigurationBuilder<T> withMaximumCost(Integer cost) {
        ensureMinimumCost(cost);
        this.maximumCost = cost;
        return this;
    }

    public PathTraversalConfigurationBuilder<T> withMinimumCost(Integer cost) {
        ensureMinimumCost(cost);
        this.minimumCost = cost;
        return this;
    }

    public PathTraversalConfigurationBuilder<T> withMinimumHops(Integer hops) {
        ensureMinimumHops(hops);
        this.minimumHops = hops;
        return this;
    }

    public PathTraversalConfigurationBuilder<T> withExactlyCost(Integer cost) {
        ensureMinimumCost(cost);
        this.exactlyCost = cost;
        return this;
    }

    public PathTraversalConfigurationBuilder<T> withExactlyHops(Integer hops) {
        ensureMinimumHops(hops);
        this.exactlyHops = hops;
        return this;
    }

    public PathTraversalConfigurationBuilder<T> withMaximumHops(Integer hops) {
        ensureMinimumHops(hops);
        this.maximumHops = hops;
        return this;
    }

    public PathTraversalConfigurationBuilder<T> limit(Integer limit) {
        if (limit < 1)
            throw new IllegalArgumentException("Limit must be positive");
        this.limit = limit;
        return this;
    }

    private void ensureNonNullNode(T... nodes) {
        if (Objects.isNull(nodes))
            throw new IllegalArgumentException("Node can't be null");
        for (T n : nodes)
            if (Objects.isNull(n))
                throw new IllegalArgumentException("None of nodes can't be null");
    }

    private static void ensureMinimumCost(Integer cost) {
        if (cost < MINIMUM_COST)
            throw new IllegalArgumentException("Invalid cost. Minimum cost is " + MINIMUM_COST);
    }

    private static void ensureMinimumHops(Integer hops) {
        if (hops < MINIMUM_HOPS)
            throw new IllegalArgumentException("Invalid hops. Minimum hops is " + MINIMUM_HOPS);
    }

}
