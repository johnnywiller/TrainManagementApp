package app;

import app.graph.IncidenceMatrixPrinter;
import app.graph.MinimumPathBuilder;
import app.graph.MinimumPathBuilder2;
import app.graph.PathTraversal;

import java.util.Arrays;

public class Main {

    public static void main (String[] args) {

        TrainStop ts1 = new TrainStop("A");
        TrainStop ts2 = new TrainStop("B");
        TrainStop ts3 = new TrainStop("C");
        TrainStop ts4 = new TrainStop("D");
        TrainStop ts5 = new TrainStop("E");

        TrainRoute route = new TrainRoute("Route1");
        route.addStopHop(ts1, ts2, 5);
        route.addStopHop(ts2, ts3, 4);
        route.addStopHop(ts3, ts4, 8);
        route.addStopHop(ts4, ts3, 8);
        route.addStopHop(ts4, ts5, 6);
        route.addStopHop(ts1, ts4, 5);
        route.addStopHop(ts3, ts5, 2);
        route.addStopHop(ts5, ts2, 3);
        route.addStopHop(ts1, ts5, 7);

        RouteMapBuilder builder = new RouteMapBuilder(new MinimumPathBuilder2());
        builder.addRoutes(Arrays.asList(route));

        builder.buildIncidenceMatrix();
//
//        IncidenceMatrixPrinter.print(builder.getIncidenceMatrix());

//        builder.build();

        PathTraversal path = new PathTraversal();

        path.buildPossiblePaths(ts3, builder.getIncidenceMatrix());

        System.out.println("-----------------------");

//        IncidenceMatrixPrinter.print(builder.getIncidenceMatrix());


    }

}
