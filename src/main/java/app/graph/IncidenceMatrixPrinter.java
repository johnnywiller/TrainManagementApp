package app.graph;

import app.TrainStop;
import app.graph.IncidenceMatrix;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;


public class IncidenceMatrixPrinter {

    public static void print(IncidenceMatrix<TrainStop> incidenceMatrix) {

        incidenceMatrix.getIncidenceMatrix().entrySet().forEach((entry) ->
                System.out.println(entry.getKey().getName() + " -> " + entry.getValue().stream()

                        .map(n -> n.toString())
                        .collect(Collectors.joining(" -> "))));


    }

}
