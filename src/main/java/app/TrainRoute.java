package app;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class TrainRoute {

    private String name;

    private List<TrainStopHop> hops;

    class TrainStopHop {
        @NonNull
        TrainStop from;
        @NonNull
        TrainStop to;
        @NonNull
        Integer distance;
    }

}
