package app.graph;

import app.TrainRoute;
import app.TrainStop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathTraversal {


    public void buildPossiblePaths(TrainStop from, IncidenceMatrix<TrainStop> incidenceMatrix) {
        HashMap<TrainStop, Integer> visitedNodes;
        visitedNodes = new HashMap<>();

        var trainStops = new ArrayList<TrainStop>();

        int hops = 0;

        trainStops.add(from);

        traversePath(incidenceMatrix, trainStops, from, visitedNodes, hops, 0);
    }


    private void traversePath(IncidenceMatrix<TrainStop> incidenceMatrix, ArrayList<TrainStop> trainStops,
                              TrainStop node, HashMap<TrainStop, Integer> visitedNodes, int hops, int distance) {

//        if (visitedNodes.getOrDefault(node, 0) >= 1)
//            return;


        visitedNodes.put(node, visitedNodes.getOrDefault(node, 0) + 1);

        for (IncidenceNodeList<TrainStop> incidences : incidenceMatrix.getAllIncidences(node)) {

            TrainStop next = incidences.getIncidencesNodes().get(0).node;

//                if (visitedNodes.getOrDefault(next, 0) < 1) {

            if (distance + incidences.getIncidencesNodes().get(0).cost < 30) {

                trainStops.add(next);

                traversePath(incidenceMatrix, trainStops, next, visitedNodes, hops + 1, distance + incidences.getIncidencesNodes().get(0).cost);

                if (next.getName().equals("C")) {
                    System.out.println("Hops Necessary = " + hops);
                    System.out.println(trainStops);
                }
//                        CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
                trainStops.remove(trainStops.size() - 1);
            }

//                }

        }

        visitedNodes.put(node, visitedNodes.getOrDefault(node, 0) - 1);

    }

}
