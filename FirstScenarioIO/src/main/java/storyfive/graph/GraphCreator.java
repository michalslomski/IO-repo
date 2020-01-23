package storyfive.graph;

import org.jgrapht.graph.DirectedWeightedMultigraph;
import storyone.graph.Node;
import storythree.DependencyObject;
import storythree.graph.Methods;
import storythree.graph.Packages;
import java.util.ArrayList;
import java.util.List;

public class GraphCreator {
    private List<Node> filesObjects;                    //story1
    private List<DependencyObject> methods;             //story2 oraz story3
    private List<DependencyObject> packages;            //story3

    private List<CustomVertex> vertexesFiles;            //story1
    private List<CustomVertex> vertexesMethods;          //story2 oraz story3
    public List<CustomVertex> vertexesPackages;         //story3

    private DirectedWeightedMultigraph<storyone.graph.CustomVertex, storyone.graph.CustomEdge> graphStory1;
    private DirectedWeightedMultigraph<storytwo.graph.CustomVertex, storytwo.graph.CustomEdge> graphStory2;
    private DirectedWeightedMultigraph<storythree.DependencyObject, storythree.graph.CustomEdge> graphStory3;

    public GraphCreator(List<DependencyObject> dependencyObjectList, List<Node> nodeObjectList){
        this.vertexesFiles = new ArrayList<>();
        this.vertexesMethods = new ArrayList<>();
        this.vertexesPackages = new ArrayList<>();

        Packages p = new Packages();
        packages = p.getPackages(dependencyObjectList);
        Methods m = new Methods();
        methods = m.getMethods(dependencyObjectList);

        storyone.graph.GraphMaker create1 = new storyone.graph.GraphMaker();
        graphStory1 = create1.makeGraph(nodeObjectList);

        //storytwo.graph.GraphDraw create2 = new storytwo.graph.GraphDraw();
        //graphStory2 = create2.graphDraw(methods2);

        storythree.graph.GraphCreate create3 = new storythree.graph.GraphCreate();
        graphStory3 = create3.createGraph(methods,packages);

        fillFilesVertexesList();
        fillPackagesVertexesList();
        fillMethodsVertexesList();
    }

    public DirectedWeightedMultigraph<CustomVertex, CustomEdge> createGraph(int variant) {
        DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph = new DirectedWeightedMultigraph<>(CustomEdge.class);

        switch (variant){                                                                                               //dodawanie wierzchołków do grafu w zależności od kombinacji historyjek na grafie
            case 0:
                break;
            case 1:
                addStory1(graph);
                break;
            case 2:
                addStory2(graph);
                break;
            case 3:
                addStory3(graph);
                break;
            case 12:
                addStory1(graph);
                addStory2(graph);
                break;
            case 13:
                addStory1(graph);
                addStory3(graph);
                break;
            case 23:
                addStory2(graph);
                addStory3(graph);
                break;
            case 123:
                addStory1(graph);
                addStory2(graph);
                addStory3(graph);
                break;
        }

        return graph;
    }

    private void addStory1(DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph)
    {
        for (CustomVertex vertex : vertexesFiles)
            graph.addVertex(vertex);
        addEdgesFromStory1(graph);
    }

    private void addStory2(DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph)
    {
        for (CustomVertex vertex : vertexesMethods)
            graph.addVertex(vertex);
        addEdgesFromStory2(graph);
    }

    private void addStory3(DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph)
    {
        for (CustomVertex vertex : vertexesMethods) {
            graph.addVertex(vertex);

        }
        for (CustomVertex vertex : vertexesPackages) {
            graph.addVertex(vertex);
        }
        addEdgesFromStory3(graph);
    }

    private void fillFilesVertexesList(){                                                                               //uzupełnia listę wierzchołków-plików, które będą dodawanie do grafu
        for (storyone.graph.CustomVertex fileNode: this.graphStory1.vertexSet()) {                                      //konwersja wierzchołków z hist1 do wierzchołków z hist5
            CustomVertex vFile = new CustomVertex(fileNode.toString(), VertexType.FILE);
            this.vertexesFiles.add(vFile);
        }
    }

    private void fillPackagesVertexesList(){                                                                            //uzupełnia listę wierzchołków-pakietów, które będą dodawanie do grafu
        for (DependencyObject pack: packages) {                                                                         //konwersja wierzchołków z hist3 do wierzchołków z hist5
            CustomVertex vPackage;
            if(pack.getWeight()!=0) {
                vPackage = new CustomVertex(pack.getPackageName(), Integer.toString(pack.getWeight()), VertexType.PACKAGE);
                vPackage.setPackageName(pack.getPackageName());
                this.vertexesPackages.add(vPackage);
            }
        }
    }

    private void fillMethodsVertexesList(){                                                                             //uzupełnia listę wierzchołków-metod, które będą dodawanie do grafu, lista jest wspólna dla hist2 i hist3
        for (DependencyObject method: methods) {                                                                        //konwersja wierzchołków z hist3 do wierzchołków z hist5
            CustomVertex vMethod = new CustomVertex(method.getMethodName(), Integer.toString(method.getWeight()), VertexType.METHOD);
            vMethod.setPackageName(method.getPackageName());
            if(method.getWeight()!=0)
                this.vertexesMethods.add(vMethod);
        }
    }

    private void addEdgesFromStory1(DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph){                        //dodaje krawędzie z hist1 do grafu
        for (storyone.graph.CustomEdge edge1: graphStory1.edgeSet()) {                                                  //konwersja krawędzi z hist1 do krawędzi z hist5
            storyone.graph.CustomVertex tmpSourceVertex = graphStory1.getEdgeSource(edge1);
            CustomVertex sourceVertex = new CustomVertex(tmpSourceVertex.toString(),  VertexType.FILE);

            storyone.graph.CustomVertex tmpTargetVertex = graphStory1.getEdgeTarget(edge1);
            CustomVertex targetVertex = new CustomVertex(tmpTargetVertex.toString(),  VertexType.FILE);

            int edgeWeight = (int) graphStory1.getEdgeWeight(edge1);
            graph.addEdge(sourceVertex, targetVertex, new CustomEdge(Integer.toString(edgeWeight)));
        }
    }

    private void addEdgesFromStory2(DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph){                        //dodaje krawędzie z hist2 do grafu
        CustomVertex sourceVertex;
        CustomVertex targetVertex;

        for (DependencyObject tmpSourceVertex : methods) {                                                              //konwersja krawędzi z hist2 do krawędzi z hist5
            sourceVertex = new CustomVertex(tmpSourceVertex.getMethodName(), Integer.toString(tmpSourceVertex.getWeight()),  VertexType.METHOD);

            for (DependencyObject tmpTargetVertex : tmpSourceVertex.getMapOfDependenciesForEachObject().keySet()) {
                targetVertex = new CustomVertex(tmpTargetVertex.getMethodName(), Integer.toString(tmpTargetVertex.getWeight()),  VertexType.METHOD);
                ///////
                for(DependencyObject target : methods){                                                                 //uzgadnianie wagi wierzchołka-metody
                    if(target.getMethodName().equals(tmpTargetVertex.getMethodName())){
                        targetVertex = new CustomVertex(target.getMethodName(), Integer.toString(target.getWeight()),  VertexType.METHOD);
                    }
                }
                //////
                int edgeWeight = tmpSourceVertex.getMapOfDependenciesForEachObject().get(tmpTargetVertex);
                if (!sourceVertex.equals(targetVertex) && edgeWeight != 0)
                    graph.addEdge(sourceVertex, targetVertex, new CustomEdge(Integer.toString(edgeWeight)));
            }
        }
    }

    private void addEdgesFromStory3(DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph){                        //dodaje krawędzie z hist3 do grafu
        for (storythree.graph.CustomEdge edge3: graphStory3.edgeSet()) {                                                //konwersja krawędzi z hist3 do krawędzi z hist5
            CustomVertex sourceVertex = null;
            CustomVertex targetVertex = null;
            ///////
            storythree.DependencyObject tmpSourceVertex = graphStory3.getEdgeSource(edge3);
            if(!tmpSourceVertex.getMethodName().equals("")) {                                                           //wykonywane jeśli wierzchołkiem początkowym jest metoda
                for(DependencyObject source : methods){                                                                 //uzgadnianie wagi wierzchołka
                    if(source.getMethodName().equals(tmpSourceVertex.getMethodName())&& (source.getPackageName().equals(tmpSourceVertex.getPackageName())) )
                        sourceVertex = new CustomVertex(source.getMethodName(), Integer.toString(tmpSourceVertex.getWeight()),  VertexType.METHOD);
                }
            }
            else {                                                                                                      //wykonywane jeśli wierzchołkiem początkowym jest pakiet
                for (DependencyObject source : packages) {                                                              //uzgadnianie wagi wierzchołka
                    if (source.getPackageName().equals(tmpSourceVertex.getPackageName())&&source.getWeight()!=0)
                        sourceVertex = new CustomVertex(source.getPackageName(), Integer.toString(tmpSourceVertex.getWeight()), VertexType.PACKAGE);
                }
            }
            ////////
            storythree.DependencyObject tmpTargetVertex = graphStory3.getEdgeTarget(edge3);
            if(!tmpTargetVertex.getMethodName().equals("")) {                                                           //wykonywane jeśli wierzchołkiem docelowym jest metoda
                for(DependencyObject target : methods){                                                                 //uzgadnianie wagi wierzchołka
                    if( (target.getMethodName().equals(tmpTargetVertex.getMethodName()) && (target.getPackageName().equals(tmpTargetVertex.getPackageName()))) )
                        targetVertex = new CustomVertex(target.getMethodName(), Integer.toString(tmpTargetVertex.getWeight()),  VertexType.METHOD);
                }
            }
            else {                                                                                                      //wykonywane jeśli wierzchołkiem docelowym jest pakiet
                for (DependencyObject target : packages) {                                                              //uzgadnianie wagi wierzchołka
                    if (target.getPackageName().equals(tmpTargetVertex.getPackageName())&&target.getWeight()!=0)
                        targetVertex = new CustomVertex(target.getPackageName(), Integer.toString(tmpTargetVertex.getWeight()), VertexType.PACKAGE);
                }
            }

            int edgeWeight = (int) graphStory3.getEdgeWeight(edge3);
            graph.addEdge(sourceVertex, targetVertex, new CustomEdge(Integer.toString(edgeWeight)));
        }
    }
}
