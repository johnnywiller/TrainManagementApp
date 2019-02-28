package app.graph;

import java.util.Objects;
import java.util.Set;

public class MinimumPathBuilder<T> {
//
//    public void buildPath(IncidenceMatrix<T> incidenceMatrix) {
//        calculateMinimumPath(incidenceMatrix);
//    }
//
//    /**
//     * Floyd-Warshall based minimum path
//     */
//    private void calculateMinimumPath(IncidenceMatrix<T> incidenceMatrix) {
//
//        var vertexes = incidenceMatrix.getAllVertexes();
//
//        for (T baseVertex : vertexes) {
//
//            for (T currentVertex : vertexes) {
//
//                var pivot = incidenceMatrix
//                        .getIncidenceList(currentVertex, baseVertex);
//
//                // we don't have a path from current
//                // vertex to base, so, no point in continue the loop
//                if (Objects.isNull(pivot))
//                    continue;
//
//                traverseFromCurrent(incidenceMatrix, vertexes, currentVertex, baseVertex, pivot);
//            }
//        }
//
//    }
//
//    private void traverseFromCurrent(IncidenceMatrix<T> incidenceMatrix,
//                                     Set<T> vertexes,
//                                     T currentVertex,
//                                     T baseVertex,
//                                     IncidenceNode pivot) {
//
//        for (T indexVertex: vertexes) {
//
//            var incidenceFromBase = incidenceMatrix.getIncidenceList(baseVertex, indexVertex);
//
//            var incidenceFromCur = incidenceMatrix.getIncidenceList(currentVertex, indexVertex);
//
//            // don't have incidence from base,
//            // so nothing will change
//            if (Objects.isNull(incidenceFromBase))
//                continue;
//
//            // if we don't have any incidence yet, create a incidence directly
//            if (Objects.isNull(incidenceFromCur)) {
//
//                incidenceFromBase.getIncidencesNodes().forEach(inc -> {
//
//                    incidenceMatrix.addIncidenceNode(currentVertex,
//                            new IncidenceNode<T>(baseVertex,
//                                    indexVertex,
//                                    pivot.cost + inc.cost,
//                                    inc.hops));
//
//                });
//            }
//
//
//                // comparece costs and replace incidence
//            else {
//
//                var newIncidence = new IncidenceNode<>(baseVertex,
//                        indexVertex,
//                        pivot.cost + incidenceFromBase.cost);
//
//                incidenceMatrix.replaceIncidenceNode(currentVertex, indexVertex, newIncidence);
//
//
//                if (incidenceFromCur.getMinimumIncidence().cost >
//                        pivot.cost + incidenceFromBase.getMinimumIncidence().cost) {
//
//
//                 }
//
//
//
//        }
//    }
}
