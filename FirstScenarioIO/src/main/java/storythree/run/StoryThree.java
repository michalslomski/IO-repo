package storythree.run;

import storyseven.CyclomaticComplexityFinder;
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

        CyclomaticComplexityFinder complexityFinder = new CyclomaticComplexityFinder();
        dependencyObjectList=complexityFinder.find(dependencyObjectList);

        GraphDraw gd=new GraphDraw();
        gd.drawGraph(dependencyObjectList);
    }

    public static void main(String[] args) throws IOException {
        runThirdStory();
    }
}
