package app;

import app.graph.IncidenceMatrix;

import java.util.List;

public class RouteMapBuilder {

    private List<TrainRoute> routes;

    private IncidenceMatrix<TrainStop> incidenceMatrix;

    public RouteMapBuilder addRoutes(List<TrainRoute> routes) {
        this.routes = routes;
        return this;
    }

    public RouteMap build() {

        buildIncidenceMatrix();

        return null;
    }

    private void buildIncidenceMatrix() {

        this.incidenceMatrix = new IncidenceMatrix<>();

        routes.stream()
                .flatMap(route -> route.getHops().stream())
                .forEach(hop -> incidenceMatrix
                        .addIncidence(hop.from, hop.to, hop.distance));
    }

}
