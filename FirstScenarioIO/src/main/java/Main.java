import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.ext.JGraphXAdapter;

import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) throws IOException {

        DependenciesCounter dependenciesCounter = new DependenciesCounter();
        FileReader fileReader = new FileReader();
        FileParser fileParser = new FileParser();
        List<File> fileList;
        List<Node> nodeList= new ArrayList<>();

        String projectPath = System.getProperty("user.dir");            //Project path
        String path = projectPath+"\\src\\main\\";                      //lokalizacja plikow projektu
        fileList=fileReader.findAllFilesInDepth(path);

        //tworzenie listy Nodów, rozmiar jest równy liczbie plików w projekcie
        for (File f : fileList) {
            HashMap<String,Integer> map1 = new HashMap<>();     //mapa na pliki z projektu
            HashMap<String,Integer> map2 = new HashMap<>();     //mapa na pliki zaimportowane
            Node n= new Node();

            n.file=f;       //ustalenie jaki plik odpowiada danemu Nodowi

            map1=dependenciesCounter.findAllDependencies(fileReader.getAllClassesNamesInsideProject(path), f);      //java files dependencies znalezione w pliku f(File)

            List<String> dependenciesList = fileParser.parseFile(f);                    //lista otrzymana z FileParser, zawiera nazwy znalezionych zależności w pliku f

            map2=dependenciesCounter.findAllDependencies(dependenciesList, f);                                      //file dependencies znalezione w pliku f(File)

            map1.putAll(map2);                      //merge map1 i map2

            n.dependencies=map1;                    //przypisanie wszystkich zależności

            n.fileSize=f.length();                  //ustawienie wielkosci pliku

            nodeList.add(n);
        }

        GraphMaker g = new GraphMaker();   //zmienic na graphmaker



        DirectedWeightedMultigraph<String, CustomEdge> graf = g.makeGraph(nodeList);

        //tworzenie grafu w pliku
        //tu może da się coś zmienić żeby lepiej wyglądał graf
        File imgFile = new File(projectPath+"\\src\\main\\resources\\graf.png");            //lokalizacja stworzonego grafu

        JGraphXAdapter<String, CustomEdge> graphAdapter = new JGraphXAdapter<>(graf);

        mxIGraphLayout layout = new mxHierarchicalLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage img = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        ImageIO.write(img, "PNG", imgFile);

    }
}