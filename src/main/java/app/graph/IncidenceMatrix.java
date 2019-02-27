package app.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;

public class IncidenceMatrix<T> {

    @Getter
    private HashMap<T, Set<Node>> incidenceMatrix;

    public void addIncidence(T from, T to, Integer cost) {

        var incidence = incidenceMatrix
                .putIfAbsent(from, new LinkedHashSet(Arrays.asList(new Node(to, cost))));

        if (Objects.nonNull(incidence))
            incidence.add(new Node(to, cost));
    }

    @Data
    @EqualsAndHashCode
    class Node<T> {
        @NonNull
        T node;
        @NonNull
        Integer cost;
    }
}
