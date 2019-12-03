package storythree.graph;

import org.jgrapht.graph.DefaultEdge;

public class CustomEdge extends DefaultEdge {
    private String label;

    public CustomEdge(String edgeWeight) {
        this.label = edgeWeight;
    }

    @Override
    public String toString() {
        return label;
    }
}
