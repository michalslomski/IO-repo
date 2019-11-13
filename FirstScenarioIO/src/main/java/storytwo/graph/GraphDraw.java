package storytwo.graph;

import org.jgrapht.graph.DirectedWeightedMultigraph;
import storytwo.graph.CustomEdge;
import storytwo.graph.NodeFunction;

import java.util.List;

//TODO: add counter(vertex weight) which counts how many times particular function is called in program

public class GraphDraw {


    public DirectedWeightedMultigraph<String, CustomEdge> graphDraw (List<NodeFunction> functionsList) { // graphDraw() adds vertexes and weights
                                                                                                         // between them and returns a graph

        DirectedWeightedMultigraph<String, CustomEdge> graph = new DirectedWeightedMultigraph<String, CustomEdge>(CustomEdge.class);

        for(int i=0; i<functionsList.size();i++) {                //adding functions names as vertexes
            NodeFunction nodeFunction = functionsList.get(i);
            String nodeFunctionName = nodeFunction.getFunctionName();

            graph.addVertex(nodeFunctionName);


            for (Object key : nodeFunction.getFunctions().keySet().toArray()) { //adding functions names for functions which are imported
                if (!graph.containsVertex(key.toString()))
                    graph.addVertex(key.toString());            
            }
        }


        for (int i = 0; i < functionsList.size(); i++) {     
            NodeFunction nodeFunction = functionsList.get(i);
            String nodeFunctionName = nodeFunction.getFunctionName();

            for (Object key : nodeFunction.getFunctions().keySet().toArray()) { //creating
                String targetVortex = key.toString();
                Integer weight = nodeFunction.getFunctions().get(targetVortex);

                if (!nodeFunctionName.equals(targetVortex) && weight != 0) {
                    graph.addEdge(nodeFunctionName, targetVortex, new storytwo.graph.CustomEdge(weight.toString()));
                }
            }
        }


        return graph;
        }
    }

