package app;

import java.util.Arrays;

public class Main {

    public static void main (String[] args) {

        TrainStop ts1 = new TrainStop("stop1");
        TrainStop ts2 = new TrainStop("stop2");
        TrainStop ts3 = new TrainStop("stop3");
        TrainStop ts4 = new TrainStop("stop4");
        TrainStop ts5 = new TrainStop("stop5");

        TrainRoute route = new TrainRoute();
//        route.setStops(Arrays.asList(ts1, ts2, ts3, ts4, ts5));
        route.setName("Route1");

        TrainRoute route2 = new TrainRoute();
//        route2.setStops(Arrays.asList(ts1, ts2, ts5));
        route2.setName("Route2");

        TrainRoute route3 = new TrainRoute();
//        route3.setStops(Arrays.asList(ts1, ts2, ts4));
        route3.setName("Route3");

        RouteMapBuilder builder = new RouteMapBuilder();
        builder.addRoutes(Arrays.asList(route, route2, route3));

        builder.build();

//        IncidenceMatrixPrinter.print(builder.getIncidenceMatrix());



    }

}
