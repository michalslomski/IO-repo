package storytwo.run;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import storytwo.graph.CustomEdge;
import storytwo.graph.CustomVertex;
import storytwo.graph.GraphDraw;
import storytwo.graph.NodeFunction;
import storytwo.methdependencies.MethodDependenciesFinder;
import storytwo.methdependencies.MethodDependency;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class StoryTwo {

    public static void runSecondStory() throws IOException {

        String projectPath = System.getProperty("user.dir");
        String path = projectPath+"\\src\\main\\java";

        MethodDependenciesFinder dependenciesFinder = new MethodDependenciesFinder();
        List<MethodDependency> methodDependencies = new LinkedList<>();
        methodDependencies = dependenciesFinder.getMethodDependencies(path);

        List<NodeFunction> nodeFunctionsList = new ArrayList<>(methodDependencies.size());


        for(int i=0;i<methodDependencies.size();i++){
            String nodeName = methodDependencies.get(i).getCallingMethodFullName();
            Integer nodeWeight = methodDependencies.get(i).getWeight();
            Map<String,Integer> map = new HashMap<>();

            MethodDependency methodDependency = new MethodDependency(nodeName);
            map = methodDependencies.get(i).getDependencyMap();
            NodeFunction nodeFunction = new NodeFunction(nodeName,map,nodeWeight);

           if(!nodeFunction.getFunctions().isEmpty()) nodeFunctionsList.add(nodeFunction);

        }

        GraphDraw g = new GraphDraw();
        DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph = g.graphDraw(nodeFunctionsList);

        File imgFile = new File(projectPath+"\\src\\main\\resources\\graph2.png");

        JGraphXAdapter<CustomVertex, CustomEdge> graphAdapter = new JGraphXAdapter<>(graph);
        mxIGraphLayout layout = new mxHierarchicalLayout(graphAdapter);
        ((mxHierarchicalLayout) layout).setIntraCellSpacing(20);
        ((mxHierarchicalLayout) layout).setInterRankCellSpacing(500);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage img = mxCellRenderer.createBufferedImage(graphAdapter, null, 1.4, Color.WHITE, true, null);
        ImageIO.write(img, "PNG", imgFile);
    }

    public static void main(String[] args) throws IOException {
        runSecondStory();
    }
}
