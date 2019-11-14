package storyone.graph;

import org.jgrapht.graph.DirectedWeightedMultigraph;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GraphMaker {		
     private HashMap<String,String> vertexMap= new HashMap<>();          //mapa unikalnych, niepowtarzających się węzłów/wierzchołków grafu
  
    //https://stackoverflow.com/questions/20246409/how-to-include-weight-in-edge-of-graph           , stąd wiadomo jak można robic graf
    public DirectedWeightedMultigraph<CustomVertex, CustomEdge> makeGraph(List<Node> sourceVertexList) {
        DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph = new DirectedWeightedMultigraph<CustomVertex, CustomEdge>(CustomEdge.class);

        //uzupełnianie vertexMap
        for (Node n : sourceVertexList) {
            vertexMap.put(n.file.getName(), Objects.toString(n.fileSize) );          //węzły które odpowiadają plikom w projekcie
            
            for (Object key : n.dependencies.keySet().toArray()) {
                if (!vertexMap.containsKey(key.toString()))
                    vertexMap.put(key.toString(), "");                  //węzły które odpowiadają plikom importowanym, nieznane są wielkości tych plików, stąd ""
            }  //chodzi o te tworzone w tej pętli                       //!!!tzn. że nie wszystkie węzły/wierzcholki pokazane na grafie zostały utworzone z klasy storyone.graph.Node
            
        }

        //dodawanie węzłów do grafu z vertexMap
        for (String key :vertexMap.keySet()) {
            CustomVertex sourceVortex = new CustomVertex(key,vertexMap.get(key));
            graph.addVertex(sourceVortex);
        }

        //tworzenie połącznień w grafie
        //dla każdego Noda z listy
        for (int i = 0; i < sourceVertexList.size(); i++) {
            Node n = sourceVertexList.get(i);
            CustomVertex sourceVortex = new CustomVertex(n.file.getName(),Objects.toString(n.fileSize));       //CustomVertex(Nazwa pliku w wierzcholku, rozmiar pliku)
            System.out.println(sourceVortex);

            //dla każdej zależności w konkretnym Nodzie
            for (String fileName : n.dependencies.keySet()) {
                CustomVertex targetVortex = new CustomVertex(fileName,vertexMap.get(fileName));
                Integer weight = n.dependencies.get(fileName);

                if (!sourceVortex.equals(targetVortex) && weight != 0) {
                    graph.addEdge(sourceVortex, targetVortex, new CustomEdge(weight.toString()));
                }
            }
        }
        return graph;
    }

}
