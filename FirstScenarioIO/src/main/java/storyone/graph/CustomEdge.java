package storyone.graph;

import org.jgrapht.graph.DefaultEdge;
//potrzebne bo inaczej nie wyświetlało wag

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
