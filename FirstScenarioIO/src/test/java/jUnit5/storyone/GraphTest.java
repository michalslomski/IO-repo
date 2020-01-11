package jUnit5.storyone;

import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.junit.jupiter.api.Test;
import storyone.fileoperations.DependenciesCounter;
import storyone.fileoperations.FileParser;
import storyone.fileoperations.FileReader;
import storyone.graph.CustomEdge;
import storyone.graph.CustomVertex;
import storyone.graph.GraphMaker;
import storyone.graph.Node;
import storyone.run.StoryOne;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphTest {

    @Test
    public void ShouldDrawGraph(){
        DependenciesCounter dependenciesCounter = new DependenciesCounter();
        FileReader fileReader = new FileReader();
        FileParser fileParser = new FileParser();
        List<File> fileList;
        List<Node> nodeList= new ArrayList<>();

        String projectPath = System.getProperty("user.dir");
        String path = projectPath+"\\src\\main\\java\\storyone";

        try {
            fileList = fileReader.findAllFilesInDepth(path);

            for (File f : fileList) {
                HashMap<String, Integer> map1 = new HashMap<>();
                HashMap<String, Integer> map2 = new HashMap<>();
                Node n = new Node();

                n.file = f;

                map1 = dependenciesCounter.findAllDependencies(fileReader.getAllClassesNamesInsideProject(path), f);

                List<String> dependenciesList = fileParser.parseFile(f);

                map2 = dependenciesCounter.findAllDependencies(dependenciesList, f);

                map1.putAll(map2);

                n.dependencies = map1;

                n.fileSize = f.length();

                nodeList.add(n);
            }

            GraphMaker g = new GraphMaker();
            DirectedWeightedMultigraph<CustomVertex, CustomEdge> graf = g.makeGraph(nodeList);
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void shouldNotThrowAnyException(){
        try{
            StoryOne.runFirstStory();
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }
}
