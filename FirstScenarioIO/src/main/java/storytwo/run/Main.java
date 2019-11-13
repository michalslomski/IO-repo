package storytwo.run;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import storytwo.graph.CustomEdge;
import storytwo.graph.GraphDraw;
import storytwo.graph.NodeFunction;
import storytwo.methdependencies.MethodDependenciesFinder;
import storytwo.methdependencies.MethodDependency;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        String projectPath = System.getProperty("user.dir");
        String path = projectPath+"\\FirstScenarioIO\\src\\main\\java";

        MethodDependenciesFinder dependenciesFinder = new MethodDependenciesFinder();
        List<MethodDependency> methodDependencies = new LinkedList<>();
        methodDependencies = dependenciesFinder.getMethodDependencies(path);

        List<NodeFunction> nodeFunctionsList = new ArrayList<>(methodDependencies.size());


       for(int i=0;i<methodDependencies.size();i++){
           String nodeName = methodDependencies.get(i).getCallingMethodName();
           MethodDependency methodDependency = new MethodDependency(nodeName);

           Map<String,Integer> map = new HashMap<>();

           map = methodDependencies.get(i).getDependencyMap();

           NodeFunction nodeFunction = new NodeFunction(nodeName,map);
           nodeFunctionsList.add(nodeFunction);

        }

        GraphDraw g = new GraphDraw();
        DirectedWeightedMultigraph<String, CustomEdge> graph = g.graphDraw(nodeFunctionsList);

        File imgFile = new File(projectPath+"\\FirstScenarioIO\\src\\main\\resources\\graph2.png");

        JGraphXAdapter<String, storytwo.graph.CustomEdge> graphAdapter = new JGraphXAdapter<>(graph);
        mxIGraphLayout layout = new mxHierarchicalLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage img = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        ImageIO.write(img, "PNG", imgFile);







    }
}
