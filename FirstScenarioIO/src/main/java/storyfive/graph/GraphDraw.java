package storyfive.graph;

import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import storyone.graph.Node;
import storythree.DependencyObject;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GraphDraw {

    public void drawAllGraphs(List<DependencyObject> dependencyObjectList, List<Node> nodeObjectList) throws IOException {
        int[] storyCombinations={1,2,3,12,13,23,123};

        for (int i=0; i<7; i++){
            this.drawGraph(dependencyObjectList,nodeObjectList,storyCombinations[i]);
        }
    }

    public void drawGraph(List<DependencyObject> dependencyObjectList, List<Node> nodeObjectList, int storyCombination) throws IOException {

        GraphCreator creator = new GraphCreator(dependencyObjectList,nodeObjectList);
        DirectedWeightedMultigraph<CustomVertex, CustomEdge> graph = creator.createGraph(storyCombination);

        String projectPath=System.getProperty("user.dir");
        File imgFile = new File(projectPath+"\\src\\main\\resources\\graph5_"+storyCombination+".png");       //lokalizacja stworzonego grafu

        JGraphXAdapter<CustomVertex, CustomEdge> graphAdapter = new JGraphXAdapter<>(graph);

        int numberOfPackages = creator.vertexesPackages.size();

        mxParallelEdgeLayout layout=this.createCustomLayout(graphAdapter, numberOfPackages);
        layout.execute(graphAdapter.getDefaultParent());                                                                //ustawianie layoutu w grafie (graphAdapter)
        this.changeGraphCellsStyle(graphAdapter);                                                                       //ustawianie styli w grafie

        BufferedImage img = mxCellRenderer.createBufferedImage(graphAdapter, null, 1.4, Color.WHITE, true, null);
        ImageIO.write(img, "PNG", imgFile);
    }


    public mxParallelEdgeLayout createCustomLayout(JGraphXAdapter<CustomVertex, CustomEdge> graphAdapter, int numberOfPackages)
    {
        mxParallelEdgeLayout layout = new mxParallelEdgeLayout(graphAdapter);
        layout.setUseBoundingBox(false);

        int sizeOfFilesGroup=0;                                                                                         //osobna grupa dla vertex-file
        int[] sizeOfEachGroup = new int[numberOfPackages];                                                              //liczby metod w grupach(pakietach)
        int groupNumber=0;                                                                                              //numer grupy(pakietu)
        HashMap<String, Integer> groups=new HashMap<>();                                                                //key: grupy(pakiety), value: numer grupy

        for ( CustomVertex vertex : graphAdapter.getVertexToCellMap().keySet() ) {
            mxICell vCell=graphAdapter.getVertexToCellMap().get(vertex);

            if( vertex.getPackageName() != null ){                                                                      //sprawdzanie czy nie jest vertex-file

                try {
                    groupNumber = groups.get(vertex.getPackageName());                                                  //wyznaczanie grupy(pakietu) dla metody
                } catch (NullPointerException e) {                                                                      //tworzenie grupy(pakietu) jesli nie istnieje
                    groupNumber = groups.size();
                    sizeOfEachGroup[groupNumber] = 0;
                    groups.put( vertex.getPackageName() , groupNumber);
                }


                if ( vertex.getType().equals(VertexType.PACKAGE) )                                                      //pozycjonowanie vertex-package
                {
                    layout.setVertexLocation(vCell,40+(groupNumber * 300),100 );
                }
                else //VertexType.METHOD
                {                                                                                                       //pozycjonowanie wierzchołka-metody
                    layout.setVertexLocation(vCell,20+((groupNumber) * 300) + sizeOfEachGroup[groupNumber] * 40, 200 + ((sizeOfEachGroup[groupNumber] % 5) * 60) );
                    sizeOfEachGroup[groupNumber]++;
                }
            }
            else {  //VertexType.FILE                                                                                   //pozycjonowanie vertex-file
                layout.setVertexLocation(vCell, ((sizeOfFilesGroup % 8) * 300), numberOfPackages * 50 + ((sizeOfFilesGroup /8) * 60));
                sizeOfFilesGroup++;
            }
        }
        return layout;
    }

    public void changeGraphCellsStyle(JGraphXAdapter<CustomVertex, CustomEdge> graphAdapter)                            //mxICell w  JGraphXAdapter oznacza vertex LUB edge
    {
        for (CustomVertex vertex : graphAdapter.getVertexToCellMap().keySet() ) {
            mxICell vCell=graphAdapter.getVertexToCellMap().get(vertex);
                                                                                                                        //zmiana stylu wierzchołków grafu
            if(vertex.getType().equals(VertexType.METHOD))
                vCell.setStyle("fillColor=lightgreen");                                                                 //css      wartość atrybutu tylko malymi literami
            else if (vertex.getType().equals(VertexType.PACKAGE))
                vCell.setStyle("fillColor=crimson");
            else  //VertexType.FILE
                vCell.setStyle("fillColor=skyblue");
        }

        /*
        for (CustomEdge edge : graphAdapter.getEdgeToCellMap().keySet() ) {
            mxICell eCell=graphAdapter.getEdgeToCellMap().get(edge);
            eCell.setStyle("edgeStyle="+mxConstants.EDGESTYLE_ORTHOGONAL);                                              //zmiana stylu krawędzi grafu
        }*/
    }
}
