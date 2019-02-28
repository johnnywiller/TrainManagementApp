package app.graph;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class MinimumPathBuilder2<T> {

    public void buildPath(IncidenceMatrix<T> incidenceMatrix) {
        calculateMinimumPath(incidenceMatrix);
    }

    /**
     * Floyd-Warshall based minimum path
     */
    private void calculateMinimumPath(IncidenceMatrix<T> incidenceMatrix) {

        var vertexes = incidenceMatrix.getAllVertexes();

        // A B C
        for (T baseVertex : vertexes) {

            System.out.println("Base " + baseVertex);


            for (T currentVertex : vertexes) {

                System.out.println("Current " + currentVertex);

                var pivot = incidenceMatrix
                        .getIncidenceList(currentVertex, baseVertex);

                // we don't any path from current
                // vertex to base, so, no point in continue the loop
                if (Objects.isNull(pivot))
                    continue;


                // now for each destination vertex we need to store all routes we can traverse
                for (T indexVertex : vertexes) {

                    // all incidences that our pivot can traverse
                    var incidenceFromPivot = incidenceMatrix.getIncidenceList(baseVertex, indexVertex);

                    // don't have incidence from base,
                    // so nothing will change in this route, can skip
                    if (Objects.isNull(incidenceFromPivot))
                        continue;


                    // if we don't have any incidence yet, create a incidence directly
                    // copying all incidences from the pivot vertex

                    // all ways we can reach the pivot must be considered

                    var incidencesToAdd = new ArrayList<IncidenceNode<T>>();

                    pivot.getIncidencesNodes().stream().forEach(toPivot -> {

                        incidenceFromPivot.getIncidencesNodes().forEach(fromPivot -> {

                            incidencesToAdd.add(new IncidenceNode<T>(baseVertex,
                                    indexVertex,
                                    toPivot.cost + fromPivot.cost,
                                    toPivot.hops + fromPivot.hops));
                        });
                    });
                    incidencesToAdd.forEach(toAdd -> incidenceMatrix.addIncidenceNode(currentVertex, toAdd));
                }
            }
        }
    }
}
