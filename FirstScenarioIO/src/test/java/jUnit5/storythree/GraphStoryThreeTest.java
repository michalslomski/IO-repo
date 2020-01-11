package jUnit5.storythree;

import org.junit.jupiter.api.Test;
import storythree.DependencyObject;
import storythree.PackageDependencyFinder;
import storythree.PackageReader;
import storythree.graph.GraphDraw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GraphStoryThreeTest {

    @Test
    public void shouldCreateGraph(){
        try{
            List<DependencyObject> dependencyObjectList =  new ArrayList<>();
            PackageReader pr = new PackageReader();
            dependencyObjectList=pr.packageDependency();

            PackageDependencyFinder dependecyFinder= new PackageDependencyFinder();
            dependencyObjectList=dependecyFinder.packageDependencyFinder(dependencyObjectList);

            GraphDraw gd=new GraphDraw();
            gd.drawGraph(dependencyObjectList);
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void shouldThrowIllegalArgumentException(){
        Exception exception = assertThrows(IllegalArgumentException.class, () ->{
            List<DependencyObject> dependencyObjectList =  new ArrayList<>();

            GraphDraw gd=new GraphDraw();
            gd.drawGraph(dependencyObjectList);
        });

    }
}
