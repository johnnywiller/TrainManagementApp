package app.graph.Interface;

import java.util.function.Supplier;

public interface GraphParser<T extends Vertex> {

    IncidenceMatrix<T> parseFromString(String graph, Supplier<T> supplier);

}
