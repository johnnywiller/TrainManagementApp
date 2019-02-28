package app;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

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

    @Data
    class TrainStopHop {
        @NonNull
        TrainStop from;
        @NonNull
        TrainStop to;
        @NonNull
        Integer distance;
    }

}
