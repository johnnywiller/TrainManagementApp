package app.view;

import app.routemap.TrainStop;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class OptionParserTest {

    @Test
    void parseOptionShouldThrowExceptionWhenNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            OptionParser.parseOption(null);
        });
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            OptionParser.parseOption("");
        });
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> {
            OptionParser.parseOption("        ");
        });
    }

    @Test
    void parseOptionShouldThrowExceptionWhenInvalid() {
        var e = assertThrows(IllegalArgumentException.class, () -> {
            OptionParser.parseOption("1 0");
        });
       assertTrue(e.getMessage().contains("Invalid string"));

       var e2 = assertThrows(IllegalArgumentException.class, () -> {
            OptionParser.parseOption("1 ab");
        });
       assertTrue(e.getMessage().contains("Invalid string"));
    }

    @Test
    void parseOptionShouldReturnCorrectStop() {
        var ts = new TrainStop("a");
        var op = OptionParser.parseOption("1 a");

        assertEquals(1, op.stops.size());
        assertEquals(ts, op.stops.get(0));
    }

    @Test
    void parseOptionShouldReturnCorrectTwoStop() {
        var ts1 = new TrainStop("a");
        var ts2 = new TrainStop("b");
        var op = OptionParser.parseOption("7 a,b");

        assertEquals(2, op.stops.size());
        assertEquals(ts1, op.stops.get(0));
        assertEquals(ts2, op.stops.get(1));

    }



}