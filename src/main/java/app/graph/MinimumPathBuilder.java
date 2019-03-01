package app.graph;

import app.graph.Interface.IncidenceMatrix;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

public class MinimumPathBuilder<T> {

    @Getter
    private IncidenceMatrix<T> incidenceMatrix;

    public void buildMinimumPath(IncidenceMatrix<T> incidenceMatrix) {
        this.incidenceMatrix = incidenceMatrix;
        calculateMinimumPath(incidenceMatrix);
    }

    /**
     * Floyd-Warshall based minimum path
     */
    private void calculateMinimumPath(IncidenceMatrix<T> incidenceMatrix) {

        var vertexes = incidenceMatrix.getAllVertexes();

        for (T baseVertex : vertexes) {

            for (T currentVertex : vertexes) {

                var incidenceToPivot = incidenceMatrix
                        .getIncidence(currentVertex, baseVertex);

                // we don't have a path from current
                // vertex to base, so, no point in continue the loop
                if (Objects.isNull(incidenceToPivot))
                    continue;

                traverseFromCurrent(incidenceMatrix, vertexes, currentVertex, incidenceToPivot);
            }
        }

    }

    private void traverseFromCurrent(IncidenceMatrix<T> incidenceMatrix,
                                     List<T> vertexes,
                                     T currentVertex,
                                     IncidenceNode<T> incidenceToPivot) {

        for (T indexVertex : vertexes) {

            var incidenceFromPivot = incidenceMatrix.getIncidence(incidenceToPivot.node, indexVertex);

            var incidenceFromCur = incidenceMatrix.getIncidence(currentVertex, indexVertex);

            // don't have incidence from base,
            // so nothing will change
            if (Objects.isNull(incidenceFromPivot))
                continue;

            // if we don't have any incidence yet, create a incidence directly
            if (Objects.isNull(incidenceFromCur)) {

                incidenceMatrix.addOrReplaceIncidenceNode(currentVertex,
                        new IncidenceNode<T>(incidenceToPivot.node,
                                indexVertex,
                                incidenceToPivot.cost + incidenceFromPivot.cost,
                                incidenceToPivot.hops + incidenceFromPivot.hops));
            }
            // comparece costs and replace incidence
            else {
                if (incidenceFromCur.cost >
                        incidenceToPivot.cost + incidenceFromPivot.cost) {

                    var newIncidence = new IncidenceNode<>(incidenceToPivot.node,
                            indexVertex,
                            incidenceToPivot.cost + incidenceFromPivot.cost,
                            incidenceToPivot.hops + incidenceFromPivot.hops);

                    incidenceMatrix.addOrReplaceIncidenceNode(currentVertex, newIncidence);

                }
            }
        }
    }
}