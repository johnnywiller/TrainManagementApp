package app.route_map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class StopHop<T> {
    @NonNull
    T from;
    @NonNull
    T to;
    @NonNull
    Integer cost;

    @Override
    public String toString() {
        return String.format("%d->%d[%d]", from, to, cost);
    }

}