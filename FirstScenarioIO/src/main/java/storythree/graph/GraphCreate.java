package storythree.graph;

import org.jgrapht.graph.DirectedWeightedMultigraph;
import storythree.DependencyObject;

import java.util.List;
import java.util.Map;

public class GraphCreate {


    public DirectedWeightedMultigraph<DependencyObject, CustomEdge> createGraph(List<DependencyObject> methods, List<DependencyObject> packages) {

        DirectedWeightedMultigraph<DependencyObject, CustomEdge> graph = new DirectedWeightedMultigraph<>(CustomEdge.class);
        for (DependencyObject method : methods) {
            graph.addVertex(method);
        }
        for (DependencyObject pack : packages) {
            graph.addVertex(pack);
        }
        for (DependencyObject method : methods) {
            DependencyObject firstMethodVertex = method;
            DependencyObject firstPackageVertex = new DependencyObject("package name","method name");
            for(DependencyObject pack : packages){ //wyszukujemy odpowiedni węzeł-paczkę, w której znajduje się dana metoda
                if(pack.getPackageName().split("\n")[0].equals(method.getPackageName())){
                    firstPackageVertex = pack;
                }
            }
            for (Map.Entry<DependencyObject, Integer> entry : method.getMapOfDependenciesForEachObject().entrySet()) {
                DependencyObject secondMethodVertex = new DependencyObject("name", "package name");
                for(DependencyObject secondMethod : methods){
                    if(secondMethod.getMethodName().split("\n")[0].equals(entry.getKey().getMethodName())){
                        secondMethodVertex = secondMethod;
                    }
                }
                DependencyObject secondPackageVertex = new DependencyObject("name", "package name");
                for(DependencyObject pack : packages){ //wyszukujemy odpowiedni węzeł-paczkę
                    if(pack.getPackageName().split("\n")[0].equals(secondMethodVertex.getPackageName())){
                        secondPackageVertex = pack;
                    }
                }

                CustomEdge e1= graph.getEdge(firstMethodVertex, firstPackageVertex);
                if(graph.containsEdge(e1)){
                    graph.setEdgeWeight( e1,(graph.getEdgeWeight(e1)+entry.getValue()));
                }
                else
                    graph.addEdge(firstMethodVertex, firstPackageVertex, new CustomEdge(entry.getValue().toString()));
                firstPackageVertex.setWeight(firstPackageVertex.getWeight()+1);
                CustomEdge e2 = graph.getEdge(firstPackageVertex, secondPackageVertex);
                if(!firstPackageVertex.equals(secondPackageVertex)&&!graph.containsEdge(e2))
                    graph.addEdge(firstPackageVertex, secondPackageVertex, new CustomEdge(entry.getValue().toString()));
                else if(!firstPackageVertex.equals(secondPackageVertex)) {
                    graph.setEdgeWeight( e2,(graph.getEdgeWeight(e2)+entry.getValue()));
                }
                secondPackageVertex.setWeight(secondPackageVertex.getWeight()+1);
                CustomEdge e3 = graph.getEdge(secondPackageVertex, secondMethodVertex);
                if(graph.containsEdge(e3)){
                    graph.setEdgeWeight( e3,(graph.getEdgeWeight(e3)+entry.getValue()));
                }
                else
                graph.addEdge(secondPackageVertex, secondMethodVertex, new CustomEdge(entry.getValue().toString()));
                secondMethodVertex.setWeight(secondMethodVertex.getWeight()+entry.getValue());
            }

        }

        return graph;
    }
}
