package app;

import app.graph.IncidenceMatrix;
import app.graph.IncidenceNode;
import app.graph.MinimumPathBuilder;
import app.graph.MinimumPathBuilder2;
import lombok.Getter;

import java.util.List;

public class RouteMapBuilder {

    private List<TrainRoute> routes;

    @Getter
    private IncidenceMatrix<TrainStop> incidenceMatrix;

    private MinimumPathBuilder2<TrainStop> pathBuilder;

    public RouteMapBuilder(MinimumPathBuilder2<TrainStop> pathBuilder) {
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

//        incidenceMatrix.incidences().forEach(incidence -> {
//
//            var trainRoute = new TrainRoute(incidence.getNode().getName());
//
//            System.out.println(incidence.getNode());
//
//            incidence.getIncidences().forEach(incidenceToNode -> {
//
//                System.out.println("Minimum incidence from " + incidence.getNode() + " to " + incidenceToNode.getNode() +
//                " equal to " + incidenceToNode.getMinimumIncidence()
//                );
//
//            });
//        });


        incidenceMatrix.incidences().filter(p -> p.getNode().getName().equals("A"))
                .map(p -> p.getIncidences())
                .forEach(System.out::println);
//                .filter(p -> p.getNode().getName().equals("A"))
//                .flatMap(p -> p.getIncidences().stream())
//                .forEach(p -> p.toString());


//        incidenceMatrix.incidences()
//                .filter(p -> p.getNode().getName().equals("A"))
//                .flatMap(inc -> inc.getIncidences().stream())
//                .filter(p -> p.getNode().getName().equals("C"))
//                .forEach(p -> System.out.println("teste"));

        return null;
    }

    public void createRouteMap() {



    }

    public void buildIncidenceMatrix() {

        this.incidenceMatrix = new IncidenceMatrix<>();

        routes.stream()
                .flatMap(route -> route.getHops().stream())
                .forEach(hop -> incidenceMatrix
                        .addIncidenceNode(hop.from, new IncidenceNode<>(hop.from, hop.to, hop.distance, 1)));
    }

}
