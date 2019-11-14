package storytwo.graph;

import org.jgrapht.graph.DirectedWeightedMultigraph;
import storytwo.graph.CustomEdge;
import storytwo.graph.NodeFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GraphDraw {
    private HashMap<String,String> vertexesMap= new HashMap<>();

    public DirectedWeightedMultigraph<CustomVertex, CustomEdge> graphDraw (List<NodeFunction> functionsList) { // graphDraw() adds vertexes and weights
                                                                                                         // between them and returns a graph
        DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph = new DirectedWeightedMultigraph<CustomVertex, CustomEdge>(CustomEdge.class);

        for( NodeFunction function : functionsList) {                //adding functions names and their weights as vertexes to vertexesMap
            vertexesMap.put(function.getFunctionName(), Objects.toString(function.getCallingsCounter()));
            int index = 0;                                               //variable which allows to find imported function and establish its weight

            for (Object key : function.getFunctions().keySet().toArray()) { //adding functions names and their weights for functions which are imported
                index++;                                                       // variable increments to find a proper function
                if (!vertexesMap.containsKey(key.toString())) {
                   NodeFunction temp = functionsList.get(index);
                   vertexesMap.put(key.toString(),Objects.toString(temp.getCallingsCounter()));}
            }
        }

        for (String key :vertexesMap.keySet()) {
            CustomVertex Vertex = new CustomVertex(key,vertexesMap.get(key)); // adding vertexes to graph (function name and its weight)
            graph.addVertex(Vertex);
        }

        for (int i = 0; i < functionsList.size(); i++) {     
            NodeFunction nodeFunction = functionsList.get(i);
            CustomVertex Vortex = new CustomVertex(nodeFunction.getFunctionName(),Objects.toString(nodeFunction.getCallingsCounter())); // vortex which edge starts from

            for (String functionName : nodeFunction.getFunctions().keySet()) {
               CustomVertex targetVortex = new CustomVertex(functionName,vertexesMap.get(functionName)); // vortex which edge leads to
                Integer weight = nodeFunction.getFunctions().get(functionName);  // establishing weights of edges

                if (!Vortex.equals(targetVortex) && weight != 0) {
                    graph.addEdge(Vortex, targetVortex, new CustomEdge(weight.toString())); // final edges adding
                }
            }
        }

        return graph;
    }
}

