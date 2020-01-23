package story6.graph;
/**
 Author: Marcin Pływacz
 Date: 04.12.2019
 */

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphContainer {
    private final DirectedGraph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

    public void addVertex(String label) {
        directedGraph.addVertex(label);
    }

    public void addEdge(String vertexLabel1, String vertexLabel2) {
        directedGraph.addEdge(vertexLabel1, vertexLabel2);
    }

    public void createPNG(String pathToWrite) throws IOException {
        File imgFile = new File(pathToWrite);         //lokalizacja stworzonego grafu

        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<>(directedGraph);
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graphAdapter);

        layout.setIntraCellSpacing(10);
        layout.setInterRankCellSpacing(280);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage img = mxCellRenderer.createBufferedImage(graphAdapter, null, 1.4, Color.WHITE, true, null);
        ImageIO.write(img, "PNG", imgFile);
    }

    public DirectedGraph<String, DefaultEdge> getDirectedGraph() {
        return directedGraph;
    }
}
