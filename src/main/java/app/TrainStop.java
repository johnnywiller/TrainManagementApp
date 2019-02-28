package app;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
public class TrainStop {

    @NonNull
    private String name;

    @Override
    public String toString() {
        return name;
    }

}
