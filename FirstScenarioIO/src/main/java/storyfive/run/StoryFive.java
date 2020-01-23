package storyfive.run;

import storyfive.fileoperations.FileDependencyFinder;
import storyfive.graph.GraphDraw;
import storyone.graph.Node;
import storythree.DependencyObject;
import storythree.PackageDependencyFinder;
import storythree.PackageReader;

import java.io.IOException;
import java.util.List;

public class StoryFive {

    public static void runFifthStory() throws IOException {

        List<DependencyObject> dependencyObjectList;
        PackageReader pr = new PackageReader();
        PackageDependencyFinder dependecyFinder = new PackageDependencyFinder();
        FileDependencyFinder fdf = new FileDependencyFinder();
        GraphDraw gd = new GraphDraw();

        dependencyObjectList = pr.packageDependency();
        dependencyObjectList = dependecyFinder.packageDependencyFinder(dependencyObjectList);                           //lista metod i pakietów z hist3
        List<Node> nodeList = fdf.fileDependencies();                                                                   //lista plków z hist1

        //gd.drawGraph(dependencyObjectList,nodeList,123);
        gd.drawAllGraphs(dependencyObjectList,nodeList);
    }
    
    public static void main(String[] args) throws IOException {
        runFifthStory();
    }
}
