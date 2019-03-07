package app.graph;

import app.routemap.Route;
import app.routemap.RouteMap;
import app.routemap.RouteMapBuilder;
import app.routemap.TrainStop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        RouteMapBuilder<TrainStop> builder = new RouteMapBuilder<TrainStop>(new FloydWarshallMinimumPathBuilder<>(),
                new DFSPathTraversal<>()).fromString("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7", TrainStop::new);

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

        List<Route<TrainStop>> routes = routeMap.from(tsC).to(tsC).maxStops(3).get();

        assertEquals(2, routes.size());

        assertTrue(routes.contains(routeCDC));
        assertTrue(routes.contains(routeCEBC));
    }

    @Test
    void numberOfTripsStartingAtCandEndWithClessDistance30() {
        List<Route<TrainStop>> routes = routeMap.from(tsC).to(tsC).maxDistance(29).get();
        assertEquals(7, routes.size());
    }

    @Test
    void numberOfTripsStartingAtAandEndWithCExaclty3stops() {
        List<Route<TrainStop>> routes = routeMap.from(tsA).to(tsC).exactlyStops(4).get();

        assertEquals(3, routes.size());
    }

    @Test
    void distanceOfRouteABCShouldBeN() {
        int expectedDistance = distances[a][b] + distances[b][c];
        List<Route<TrainStop>> routeABC = routeMap.sequence(tsA, tsB, tsC).get();
        assertEquals(expectedDistance, routeABC.get(0).getTotalCost());
    }

    @Test
    void distanceOfRouteADShouldBeN() {
        int expectedDistance = distances[a][d];
        List<Route<TrainStop>> routeABC = routeMap.sequence(tsA, tsD).get();
        assertEquals(expectedDistance, routeABC.get(0).getTotalCost());
    }

    @Test
    void numberOfRoutesABCShouldBe1() {
        List<Route<TrainStop>> route = routeMap.sequence(tsA, tsB, tsC).get();
        assertTrue(route.size() == 1);
    }

    @Test
    void distanceOfRouteADCShouldBeN() {
        int expectedDistance = distances[a][d] + distances[d][c];
        List<Route<TrainStop>> route = routeMap.sequence(tsA, tsD, tsC).get();
        assertEquals(expectedDistance, route.get(0).getTotalCost());
    }

    @Test
    void distanceOfRouteAEBCDShouldBeN() {
        int expectedDistance = distances[a][e] + distances[e][b] + distances[b][c] + distances[c][d];
        List<Route<TrainStop>> route = routeMap.sequence(tsA, tsE, tsB, tsC, tsD).get();
        assertEquals(expectedDistance, route.get(0).getTotalCost());
    }

    @Test
    void routeAEDShouldNotExist() {
        List<Route<TrainStop>> route = routeMap.sequence(tsA, tsE, tsD).get();
        assertTrue(route.isEmpty());
    }
    @Test
    void shouldHaveNoSuchRoute() {
        assertEquals(null, routeMap.minimumDistanceFrom(tsA, tsA));
    }


}