package app;

import app.graph.IncidenceMatrix;
import app.graph.IncidenceNode;
import app.graph.MinimumPathBuilder;
import lombok.Getter;

import java.util.List;

public class RouteMapBuilder {

    private List<TrainRoute> routes;

    @Getter
    private IncidenceMatrix<TrainStop> incidenceMatrix;

    private MinimumPathBuilder<TrainStop> pathBuilder;

    public RouteMapBuilder(MinimumPathBuilder<TrainStop> pathBuilder) {
        this.pathBuilder = pathBuilder;
    }

    public RouteMapBuilder addRoutes(List<TrainRoute> routes) {
        this.routes = routes;
        this.pathBuilder = pathBuilder;
        return this;
    }

    public RouteMap build() {

        buildIncidenceMatrix();

        pathBuilder.buildPath(incidenceMatrix);

        var routeMap = new RouteMap();

        incidenceMatrix.incidences().forEach(incidence -> {

            var trainRoute = new TrainRoute(incidence.getNode().getName());

//            incidence.getIncidences().



        });

        return null;
    }

    public void createRouteMap() {



    }

    public void buildIncidenceMatrix() {

        this.incidenceMatrix = new IncidenceMatrix<>();

        routes.stream()
                .flatMap(route -> route.getHops().stream())
                .forEach(hop -> incidenceMatrix
                        .addIncidenceNode(hop.from, new IncidenceNode<>(hop.from, hop.to, hop.distance)));
    }

}
