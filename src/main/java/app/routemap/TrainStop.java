package app.routemap;


import app.graph.Interface.Vertex;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrainStop extends Vertex {

    @EqualsAndHashCode.Exclude
    private String stopInfo;

    public TrainStop() {
        super();
    }

    public TrainStop(String name) {
        super(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stopInfo);
    }

    @Override
    public String toString() {
        return getName();
    }

}
