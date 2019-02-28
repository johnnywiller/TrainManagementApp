package app;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TrainRoute {

    private String name;

    private List<TrainStopHop> hops;

    public TrainRoute(String name) {
        this.name = name;
        this.hops = new ArrayList<>();
    }

    public void addStopHop(TrainStop from, TrainStop to, Integer distance) {
        hops.add(new TrainStopHop(from, to, distance));
    }

    @Override
    public String toString() {
        return hops.stream().map(TrainStopHop::toString).collect(Collectors.joining(" - "));
    }

    @Data
    class TrainStopHop {
        @NonNull
        TrainStop from;
        @NonNull
        TrainStop to;
        @NonNull
        Integer distance;

        @Override
        public String toString() {
            return String.format("%d->%d[%d]", from, to, distance);
        }

    }

}
