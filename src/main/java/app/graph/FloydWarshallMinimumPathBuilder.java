package app.graph;

import app.graph.Interface.IncidenceMatrix;
import app.graph.Interface.MinimumPathBuilder;
import app.graph.Interface.Vertex;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;

public class FloydWarshallMinimumPathBuilder<T extends Vertex> implements MinimumPathBuilder<T> {

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

            var incidenceFromPivot = (DefaultIncidenceNode) incidenceMatrix.getIncidence(incidenceToPivot.getNode(), indexVertex);

            var incidenceFromCur = incidenceMatrix.getIncidence(currentVertex, indexVertex);

            // don't have incidence from base,
            // so nothing will change
            if (Objects.isNull(incidenceFromPivot))
                continue;

            // if we don't have any incidence yet, create a incidence directly
            if (Objects.isNull(incidenceFromCur)) {

                incidenceMatrix.addOrReplaceIncidenceNode(currentVertex,
                        new DefaultIncidenceNode<T>(incidenceToPivot.getNode(),
                                indexVertex,
                                incidenceToPivot.getCost() + incidenceFromPivot.getCost(),
                                incidenceToPivot.hops + incidenceFromPivot.hops));
            }
            // comparece costs and replace incidence
            else {
                if (incidenceFromCur.getCost() >
                        incidenceToPivot.getCost() + incidenceFromPivot.getCost()) {

                    var newIncidence = new DefaultIncidenceNode<>(incidenceToPivot.getNode(),
                            indexVertex,
                            incidenceToPivot.getCost() + incidenceFromPivot.getCost(),
                            incidenceToPivot.hops + incidenceFromPivot.hops);

                    incidenceMatrix.addOrReplaceIncidenceNode(currentVertex, newIncidence);

                }
            }
        }
    }
}