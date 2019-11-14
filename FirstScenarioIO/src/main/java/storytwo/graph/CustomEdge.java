package storytwo.graph;

import org.jgrapht.graph.DefaultEdge;

public class CustomEdge extends DefaultEdge { // Basic class of an edge in graph which is necessary to create it
    private String label;

    public CustomEdge(String edgeWeight) {
        this.label = edgeWeight;
    }

    @Override
    public String toString() {
        return label;
    }
}
