package app.graph;

import app.route_map.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteMapTest {

    RouteMap<TrainStop> routeMap;

    TrainStop tsA;
    TrainStop tsB;
    TrainStop tsC;
    TrainStop tsD;
    TrainStop tsE;

    int a = 0, b = 1, c = 2, d = 3, e = 4;
    int[][] distances = {
            {0, 5, 0, 5, 7},
            {0, 0, 4, 0, 0},
            {0, 0, 0, 8, 2},
            {0, 0, 8, 0, 6},
            {0, 3, 0, 0, 0},
    };

    @BeforeEach
    void initializeStopHops() {

        tsA = new TrainStop("A");
        tsB = new TrainStop("B");
        tsC = new TrainStop("C");
        tsD = new TrainStop("D");
        tsE = new TrainStop("E");

        ArrayList<StopHop<TrainStop>> hops = new ArrayList<>();

        // TODO parametrize distances
        hops.add(new StopHop<>(tsA, tsB, distances[a][b]));
        hops.add(new StopHop<>(tsB, tsC, distances[b][c]));
        hops.add(new StopHop<>(tsC, tsD, distances[c][d]));
        hops.add(new StopHop<>(tsD, tsC, distances[d][c]));
        hops.add(new StopHop<>(tsD, tsE, distances[d][e]));
        hops.add(new StopHop<>(tsA, tsD, distances[a][d]));
        hops.add(new StopHop<>(tsC, tsE, distances[c][e]));
        hops.add(new StopHop<>(tsE, tsB, distances[e][b]));
        hops.add(new StopHop<>(tsA, tsE, distances[a][e]));

        RouteMapBuilder<TrainStop> builder = new RouteMapBuilder<>(new MinimumPathBuilder<>(),
                new DFSPathTraversal<>());

        builder.addHops(hops);

        routeMap = builder.build();
    }

    @Test
    void shortestRouteFromAtoCshouldBe9() {

        int distance = routeMap.minimumDistanceFrom(tsA, tsC);
        assertEquals(9, distance);
    }

    @Test
    void shortestRouteFromBtoBshouldBe9() {

        int distance = routeMap.minimumDistanceFrom(tsB, tsB);
        assertEquals(9, distance);
    }

    @Test
    void tripsStartingAtCandEndWithCwithMax3stops() {

        Route<TrainStop> routeCDC = new Route<>();
        Route<TrainStop> routeCEBC = new Route<>();

        routeCDC.addAllNode(Arrays.asList(tsC, tsD, tsC), distances[c][d] + distances[d][c]);
        routeCEBC.addAllNode(Arrays.asList(tsC, tsE, tsB, tsC), distances[c][e] +
                distances[e][b] +
                distances[b][c]);

        var config = PathTraversalConfigurationBuilder
                .emptyConfiguration()
                .withMaximumHops(3)
                .beginWith(tsC)
                .endWith(tsC)
                .build();

        List<Route<TrainStop>> routes = routeMap.getRoutes(config);

        assertEquals(2, routes.size());

        assertTrue(routes.contains(routeCDC));
        assertTrue(routes.contains(routeCEBC));
    }

    @Test
    void numberOfTripsStartingAtCandEndWithClessDistance30() {

        var config = PathTraversalConfigurationBuilder
                .emptyConfiguration()
                .withMaximumCost(29)
                .beginWith(tsC)
                .endWith(tsC)
                .build();

        List<Route<TrainStop>> routes = routeMap.getRoutes(config);
        assertEquals(7, routes.size());
    }

    @Test
    void numberOfTripsStartingAtAandEndWithCExaclty3stops() {

        var config = PathTraversalConfigurationBuilder
                .emptyConfiguration()
                .withExactlyHops(4)
                .beginWith(tsA)
                .endWith(tsC)
                .build();

        List<Route<TrainStop>> routes = routeMap.getRoutes(config);
        assertEquals(3, routes.size());
    }

    @Test
    void distanceOfRouteABCShouldBeN() {
        var config = PathTraversalConfigurationBuilder
                .defaultConfiguration()
                .withExactlySequence(tsA, tsB, tsC)
                .build();

        int expectedDistance = distances[a][b] + distances[b][c];

        List<Route<TrainStop>> routeABC = routeMap.getRoutes(config);

        assertEquals(expectedDistance, routeABC.get(0).getTotalCost());
    }

    @Test
    void distanceOfRouteADShouldBeN() {
        var config = PathTraversalConfigurationBuilder
                .defaultConfiguration()
                .withExactlySequence(tsA, tsD)
                .build();

        int expectedDistance = distances[a][d];

        List<Route<TrainStop>> routeABC = routeMap.getRoutes(config);

        assertEquals(expectedDistance, routeABC.get(0).getTotalCost());
    }

    @Test
    void numberOfRoutesABCShouldBe1() {
        var config = PathTraversalConfigurationBuilder
                .defaultConfiguration()
                .withExactlySequence(tsA, tsB, tsC)
                .build();

        List<Route<TrainStop>> route = routeMap.getRoutes(config);

        assertTrue(route.size() == 1);
    }

    @Test
    void distanceOfRouteADCShouldBeN() {
        var config = PathTraversalConfigurationBuilder
                .defaultConfiguration()
                .withExactlySequence(tsA, tsD, tsC)
                .build();

        int expectedDistance = distances[a][d] + distances[d][c];

        List<Route<TrainStop>> route = routeMap.getRoutes(config);

        assertEquals(expectedDistance, route.get(0).getTotalCost());
    }

    @Test
    void distanceOfRouteAEBCDShouldBeN() {
        var config = PathTraversalConfigurationBuilder
                .defaultConfiguration()
                .withExactlySequence(tsA, tsE, tsB, tsC, tsD)
                .build();

        int expectedDistance = distances[a][e] + distances[e][b] + distances[b][c] + distances[c][d];

        List<Route<TrainStop>> route = routeMap.getRoutes(config);

        assertEquals(expectedDistance, route.get(0).getTotalCost());
    }

    @Test
    void routeAEDShouldNotExist() {
        var config = PathTraversalConfigurationBuilder
                .defaultConfiguration()
                .withExactlySequence(tsA, tsE, tsD)
                .build();

        List<Route<TrainStop>> route = routeMap.getRoutes(config);

        assertTrue(route.isEmpty());

    }


}