package app.graph;

import app.graph.Interface.IncidenceMatrix;
import app.graph.Interface.Vertex;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;

public class MinimumPathBuilder<T extends Vertex> {

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

                traverseFromCurrent(incidenceMatrix, vertexes, currentVertex, (DefaultIncidenceNode) incidenceToPivot);
            }
        }

    }

    private void traverseFromCurrent(IncidenceMatrix<T> incidenceMatrix,
                                     Set<T> vertexes,
                                     T currentVertex,
                                     DefaultIncidenceNode<T> incidenceToPivot) {

        for (T indexVertex : vertexes) {

            var incidenceFromPivot = (DefaultIncidenceNode) incidenceMatrix.getIncidence(incidenceToPivot.node, indexVertex);

            var incidenceFromCur = incidenceMatrix.getIncidence(currentVertex, indexVertex);

            // don't have incidence from base,
            // so nothing will change
            if (Objects.isNull(incidenceFromPivot))
                continue;

            // if we don't have any incidence yet, create a incidence directly
            if (Objects.isNull(incidenceFromCur)) {

                incidenceMatrix.addOrReplaceIncidenceNode(currentVertex,
                        new DefaultIncidenceNode<T>(incidenceToPivot.node,
                                indexVertex,
                                incidenceToPivot.cost + incidenceFromPivot.cost,
                                incidenceToPivot.hops + incidenceFromPivot.hops));
            }
            // comparece costs and replace incidence
            else {
                if (incidenceFromCur.cost >
                        incidenceToPivot.cost + incidenceFromPivot.cost) {

                    var newIncidence = new DefaultIncidenceNode<>(incidenceToPivot.node,
                            indexVertex,
                            incidenceToPivot.cost + incidenceFromPivot.cost,
                            incidenceToPivot.hops + incidenceFromPivot.hops);

                    incidenceMatrix.addOrReplaceIncidenceNode(currentVertex, newIncidence);

                }
            }
        }
    }
}