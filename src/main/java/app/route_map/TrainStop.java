package app.route_map;


import app.graph.Interface.Vertex;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TrainStop extends Vertex {

    private String stopInfo;

    public TrainStop() {
        super();
    }

    @Override
    public String toString() {
        return getName();
    }

}
