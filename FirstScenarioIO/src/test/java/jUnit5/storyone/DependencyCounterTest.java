package jUnit5.storyone;

import org.junit.jupiter.api.Test;
import storyone.fileoperations.DependenciesCounter;
import storyone.fileoperations.FileParser;
import storyone.fileoperations.FileReader;
import storyone.graph.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DependencyCounterTest {
    FileReader fileReader = new FileReader();
    FileParser fileParser = new FileParser();
    List<Node> nodeList= new ArrayList<>();

    String projectPath = System.getProperty("user.dir");
    String path = projectPath+"\\src\\main\\java\\storyone\\fileoperations\\FileParser.java";

    @Test
    public void findDependenciesInSingleFile(){
        try {
            DependenciesCounter dcTest = new DependenciesCounter();
            File testFile = new File(path);
            List<String> testList = fileParser.parseFile(testFile);
            HashMap<String,Integer> dependeciesTest = new HashMap<>();

            dependeciesTest = dcTest.findAllDependencies(testList,testFile);
            System.out.println(dependeciesTest);
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void findSingleDependency(){
        try {
            DependenciesCounter dcTest = new DependenciesCounter();
            File testFile = new File(path);
            int testDependency;

            testDependency = dcTest.findDependencies(testFile,"java.util.List");
            assertEquals(testDependency,1);
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }
}
