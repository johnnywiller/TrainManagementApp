package app.route_map;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StopParser {

    public static List<TrainStop> parseStops(String s) {

        var stops = new ArrayList<TrainStop>();
        s = s.replaceAll("[^A-Za-z]", "");

        for (int i = 0; i < s.length(); i++) {
            var ts = new TrainStop(s.charAt(i) + "");
            stops.add(ts);
        }

        return stops;
    }

    public static List<StopHop<TrainStop>> parse(String s) {

        var list = new ArrayList<StopHop<TrainStop>>();

        if (Objects.isNull(s) || s.isBlank()) {
            throw new IllegalArgumentException("Invalid string");
        }

        String[] hops = s.split(" ");


        for (String hop : hops) {

            if (hop.length() < 3)
                throw new IllegalArgumentException("Invalid string");

            var t1 = new TrainStop(String.valueOf(hop.charAt(0)));
            var t2 = new TrainStop(String.valueOf(hop.charAt(1)));
            var stopHop = new StopHop<TrainStop>(t1, t2, Integer.parseInt(hop.replaceAll("\\D+", "")));

            list.add(stopHop);
        }

        return list;
    }

}
