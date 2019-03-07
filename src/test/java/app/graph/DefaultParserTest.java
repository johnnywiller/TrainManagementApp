package app.graph;

import app.graph.Interface.IncidenceMatrix;
import app.routemap.TrainStop;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultParserTest {

    private DefaultParser<TrainStop> defaultParser;
    private IncidenceMatrix<TrainStop> incidenceMatrix;

    @BeforeEach
    void setup() {
        defaultParser = new DefaultParser<>();
        incidenceMatrix = new ListIncidenceMatrix<>();
    }

    @Test
    void shouldContainOnlyA() {

        var ts = new TrainStop("a");

        defaultParser.parseFromString("aa1", TrainStop::new, incidenceMatrix);

        assertEquals(1, incidenceMatrix.getAllVertexes().size());
        assertEquals(ts, incidenceMatrix.getAllVertexes().iterator().next());
        assertEquals(ts, incidenceMatrix.getAllIncidences(ts).iterator().next().getNode());
    }

    @Test
    void shouldContainAB() {

        var tsA = new TrainStop("a");
        var tsB = new TrainStop("b");

        defaultParser.parseFromString("ab1", TrainStop::new, incidenceMatrix);

        assertEquals(2, incidenceMatrix.getAllVertexes().size());
        assertTrue(incidenceMatrix.getAllVertexes().contains(tsA));
        assertTrue(incidenceMatrix.getAllVertexes().contains(tsB));
        assertEquals(tsB, incidenceMatrix.getAllIncidences(tsA).iterator().next().getNode());
    }
    @Test
    void shouldContainTwoIncidences() {

        var tsA = new TrainStop("a");
        var tsB = new TrainStop("b");
        var tsC = new TrainStop("c");

        IncidenceNode<TrainStop> inB = new DefaultIncidenceNode<>(tsA, tsB, 1);
        IncidenceNode<TrainStop> inC = new DefaultIncidenceNode<>(tsA, tsC, 1);

        defaultParser.parseFromString("ab1, ac1, ab1", TrainStop::new, incidenceMatrix);

        assertEquals(3, incidenceMatrix.getAllVertexes().size());
        assertEquals(2, incidenceMatrix.getAllIncidences(tsA).size());
        assertTrue(incidenceMatrix.getAllIncidences(tsA).contains(inB));
        assertTrue(incidenceMatrix.getAllIncidences(tsA).contains(inC));
    }

}