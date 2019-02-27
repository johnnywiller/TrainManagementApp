package app;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;


public class IncidenceMatrixPrinter {

    public static void print(HashMap<TrainStop, Set<TrainStop>> incidenceMatrix) {

        incidenceMatrix.entrySet().forEach((entry) ->
                System.out.println(entry.getKey().getName() + " -> "
                        + entry.getValue()
                        .stream()
                        .map(TrainStop::getName)
                        .collect(Collectors.joining(" -> "))));


    }

}
