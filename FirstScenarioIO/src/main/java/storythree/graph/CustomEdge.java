package storythree.graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

public class CustomEdge extends DefaultWeightedEdge {
    private String label;

    public CustomEdge(String edgeWeight) {
        this.label = edgeWeight;
    }

    @Override
    public String toString() {
        return label;
    }
}
