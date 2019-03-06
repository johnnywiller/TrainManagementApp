package app.routemap;

import app.graph.Interface.IncidenceMatrix;
import app.graph.Interface.PathTraversalEngine;
import app.graph.Interface.Vertex;
import app.graph.PathTraversalConfiguration;
import app.graph.PathTraversalConfigurationBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class RouteMap<T extends Vertex> {

    private PathTraversalConfigurationBuilder<T> currentConfig;

    @Getter
    private IncidenceMatrix<T> minimumIncidenceMatrix;

    private PathTraversalEngine<T> pathTraversal;

    public RouteMap() {
        this.currentConfig = PathTraversalConfigurationBuilder.defaultConfiguration();
    }

    public RouteMap(IncidenceMatrix<T> minimumIncidenceMatrix, PathTraversalEngine<T> pathTraversal) {
        this.minimumIncidenceMatrix = minimumIncidenceMatrix;
        this.pathTraversal = pathTraversal;
        this.currentConfig = PathTraversalConfigurationBuilder.defaultConfiguration();
    }

    public List<Route<T>> getRoutes(PathTraversalConfiguration<T> config) {
        return pathTraversal.getPaths(config);
    }

    public List<Route<T>> get() {
        var routes = pathTraversal.getPaths(this.currentConfig.build());
        resetConfig();
        return routes;
    }

    public Integer minimumDistanceFrom(T from, T to) {
        return minimumIncidenceMatrix.getIncidence(from, to).getCost();
    }

    public void resetConfig() {
        this.currentConfig = PathTraversalConfigurationBuilder.defaultConfiguration();
    }

    public String printCurrentConfig() {
        return currentConfig.printCurrentConfig();
    }

    public RouteMap<T> from(T... begin) {
        this.currentConfig = this.currentConfig.beginWithSequence(begin);
        return this;
    }

    public RouteMap<T> to(T end) {
        this.currentConfig = this.currentConfig.endWith(end);
        return this;
    }

    public RouteMap<T> maxStops(Integer maxStops) {
        this.currentConfig = this.currentConfig.withMaximumHops(maxStops);
        return this;
    }

    public RouteMap<T> maxDistance(Integer maxDistance) {
        this.currentConfig = this.currentConfig.withMaximumCost(maxDistance);
        return this;
    }

    public RouteMap<T> exactlyStops(Integer stops) {
        this.currentConfig = this.currentConfig.withExactlyHops(stops);
        return this;
    }

    public RouteMap<T> exactlyDistance(Integer distance) {
        this.currentConfig = this.currentConfig.withExactlyCost(distance);
        return this;
    }

    public RouteMap<T> sequence(T... sequence) {
        this.currentConfig = this.currentConfig.withExactlySequence(sequence);
        return this;
    }

    public RouteMap<T> limit(Integer limit) {
        this.currentConfig = this.currentConfig.limit(limit);
        return this;
    }

}
