package app.graph.Interface;

import java.util.List;
import java.util.function.Supplier;

public interface GraphParser<T extends Vertex> {

    IncidenceMatrix<T> parseFromString(String graph, Supplier<T> supplier, IncidenceMatrix<T> incidenceMatrix);

    List<T> parseNodes(String sNodes, Supplier<T> supplier);

}
