package storythree.run;

import storythree.DependencyObject;
import storythree.PackageDependencyFinder;
import storythree.PackageReader;
import storythree.graph.GraphDraw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoryThree {



    public static void runThirdStory() throws IOException {

        List<DependencyObject> dependencyObjectList =  new ArrayList<>();
        PackageReader pr = new PackageReader();
        dependencyObjectList=pr.packageDependency();
        
        PackageDependencyFinder dependecyFinder= new PackageDependencyFinder();
        dependencyObjectList=dependecyFinder.packageDependencyFinder(dependencyObjectList);

        GraphDraw gd=new GraphDraw();
        gd.drawGraph(dependencyObjectList);


    }
}
