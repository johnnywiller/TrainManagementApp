package app;

import app.graph.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main (String[] args) {

        TrainStop ts1 = new TrainStop("A");
        TrainStop ts2 = new TrainStop("B");
        TrainStop ts3 = new TrainStop("C");
        TrainStop ts4 = new TrainStop("D");
        TrainStop ts5 = new TrainStop("E");

        ArrayList<StopHop<TrainStop>> hops = new ArrayList<>();

        hops.add(new StopHop<>(ts1, ts2, 5));
        hops.add(new StopHop<>(ts2, ts3, 4));
        hops.add(new StopHop<>(ts3, ts4, 8));
        hops.add(new StopHop<>(ts4, ts3, 8));
        hops.add(new StopHop<>(ts4, ts5, 6));
        hops.add(new StopHop<>(ts1, ts4, 5));
        hops.add(new StopHop<>(ts3, ts5, 2));
        hops.add(new StopHop<>(ts5, ts2, 3));
        hops.add(new StopHop<>(ts1, ts5, 7));

        RouteMapBuilder<TrainStop> builder = new RouteMapBuilder<>(new MinimumPathBuilder<>(),
                new DFSPathTraversal<>());

        builder.addHops(hops);

        var routeMap = builder.build();

        PathTraversalConfiguration config = PathTraversalConfigurationBuilder
                .emptyConfiguration()
                .withMaximumHops(5)
//                .withExactlyHops(4)
//                .withExactlyCost(110)
//                .beginWith(ts2)
                .endWith(ts3)
//                .limit(3)
//                .beginWithSequence(ts1, ts5, ts2, ts3)
                .build();

//        routeMap.getRoutes(config);

        System.out.println(routeMap.minimumDistanceFrom(ts2, ts2));

//        E, B, C, D, C]
//      [E, B, C, E, B, C]
//         [E, B, C]
        System.out.println("-----------------------");


    }

}
