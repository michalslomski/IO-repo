package storyone.graph;

import org.jgrapht.graph.DirectedWeightedMultigraph;
import java.util.List;

public class GraphMaker {		//aka GraphDraw
    //https://stackoverflow.com/questions/20246409/how-to-include-weight-in-edge-of-graph
    //stąd wiem jak można robic graf
    public DirectedWeightedMultigraph<String, CustomEdge> makeGraph(List<Node> sourceVertexList) {
        DirectedWeightedMultigraph<String, CustomEdge> graph = new DirectedWeightedMultigraph<String, CustomEdge>(CustomEdge.class);

        //dodawanie wyłącznie węzłów
        for (int i = 0; i < sourceVertexList.size(); i++) {
            Node n = sourceVertexList.get(i);
            String sourceVortex = n.file.getName();

            graph.addVertex(sourceVortex);          //węzły które odpowiadają plikom w projekcie

            //
            for (Object key : n.dependencies.keySet().toArray()) {
                if (!graph.containsVertex(key.toString()))
                    graph.addVertex(key.toString());            //węzły które odpowiadają plikom importowanym
            }                                                   //!!!!!!tzn. że nie wszystkie węzły pokazane na grafie zostały utworzone z klasy storyone.graph.Node
                                                                //chodzi własnie o te tworzone w tej pętli
        }

        //tworzenie połącznień w grafie
        //dla każdego Noda z listy
        for (int i = 0; i < sourceVertexList.size(); i++) {
            Node n = sourceVertexList.get(i);
            String sourceVortex = n.file.getName();
            System.out.println(sourceVortex);

            //dla każdej zależności w konkretnym Nodzie
            for (Object key : n.dependencies.keySet().toArray()) {
                String targetVortex = key.toString();
                Integer weight = n.dependencies.get(targetVortex);

                if (!sourceVortex.equals(targetVortex) && weight != 0) {
                    graph.addEdge(sourceVortex, targetVortex, new CustomEdge(weight.toString()));
                }
            }

            /*
            for (int j = 0; j < n.dependencies.size(); j++) {
                String targetVortex = n.dependencies.keySet().toArray()[j].toString();
                Integer weight = n.dependencies.get(targetVortex);


                System.out.println("////////////////////////////////");
                System.out.println(n.dependencies.keySet().toArray()[j].toString());

                if (!sourceVortex.equals(targetVortex) && weight != 0) {
                    storyone.graph.addEdge(sourceVortex, targetVortex, new storyone.graph.CustomEdge(weight.toString()));
                }
            }*/

        }
        return graph;
    }

}