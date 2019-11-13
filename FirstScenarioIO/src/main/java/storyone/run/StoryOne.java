package storyone.run;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import storyone.fileoperations.DependenciesCounter;
import storyone.fileoperations.FileParser;
import storyone.fileoperations.FileReader;
import storyone.graph.CustomEdge;
import storyone.graph.GraphMaker;
import storyone.graph.Node;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryOne {

  public static void runFirstStory() throws IOException {

      DependenciesCounter dependenciesCounter = new DependenciesCounter();
      FileReader fileReader = new FileReader();
      FileParser fileParser = new FileParser();
      List<File> fileList;
      List<Node> nodeList= new ArrayList<>();

      String projectPath = System.getProperty("user.dir");            //Project path
      String path = projectPath+"\\src\\main\\storyone";                      //lokalizacja plikow projektu
      fileList=fileReader.findAllFilesInDepth(path);

      //tworzenie listy Nodów, rozmiar jest równy liczbie plików w projekcie
      for (File f : fileList) {
          HashMap<String,Integer> map1 = new HashMap<>();     //mapa na pliki z projektu
          HashMap<String,Integer> map2 = new HashMap<>();     //mapa na pliki zaimportowane
          Node n= new Node();

          n.file=f;       //ustalenie jaki plik odpowiada danemu Nodowi

          map1=dependenciesCounter.findAllDependencies(fileReader.getAllClassesNamesInsideProject(path), f);      //java files dependencies znalezione w pliku f(File)

          List<String> dependenciesList = fileParser.parseFile(f);                    //lista otrzymana z storyone.fileoperations.FileParser, zawiera nazwy znalezionych zależności w pliku f

          map2=dependenciesCounter.findAllDependencies(dependenciesList, f);                                      //file dependencies znalezione w pliku f(File)

          map1.putAll(map2);                      //merge map1 i map2

          n.dependencies=map1;                    //przypisanie wszystkich zależności

          n.fileSize=f.length();                  //ustawienie wielkosci pliku

          nodeList.add(n);
      }

      GraphMaker g = new GraphMaker();  
      DirectedWeightedMultigraph<CustomVertex, CustomEdge> graf = g.makeGraph(nodeList);

      //tworzenie grafu w pliku
      File imgFile = new File(projectPath+"\\src\\main\\resources\\graf1.png");            //lokalizacja stworzonego grafu

      JGraphXAdapter<CustomVertex, CustomEdge> graphAdapter = new JGraphXAdapter<>(graf);
      mxHierarchicalLayout layout = new mxHierarchicalLayout(graphAdapter);
      //ustawienia wyświetlanego grafu
      layout.setIntraCellSpacing(10);
      layout.setInterRankCellSpacing(280);
      layout.execute(graphAdapter.getDefaultParent());

      BufferedImage img = mxCellRenderer.createBufferedImage(graphAdapter, null, 1.4, Color.WHITE, true, null);
      ImageIO.write(img, "PNG", imgFile);

  }
}
