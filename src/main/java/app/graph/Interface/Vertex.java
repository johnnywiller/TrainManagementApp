package app.graph.Interface;

import lombok.Data;

@Data
public abstract class Vertex {

    private String name;

    public Vertex() {
    }

    public Vertex(String name) {
        this.name = name;
    }
}
