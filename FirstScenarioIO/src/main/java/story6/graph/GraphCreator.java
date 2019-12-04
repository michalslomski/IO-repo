package story6.graph;
/*
Author: BeGieU
Date: 04.12.2019
*/

import story6.dependencies.Dependency;
import story6.dependencies.MethodDependenciesFinder;

import java.util.Set;

public class GraphCreator {
    private final MethodDependenciesFinder dependenciesFinder = new MethodDependenciesFinder();
    private final GraphContainer graphContainer = new GraphContainer();

    public GraphContainer makeGraph(String path) {
        Set<Dependency> dependencySet = dependenciesFinder.getMethodFileDependency(path);

        dependencySet.forEach(dependency -> {
            String classS = dependency.getClassName();
            graphContainer.addVertex(classS);

            Set<String> classMethods = dependency.getMethodNames();
            classMethods.forEach(classMethod -> {
                graphContainer.addVertex(classMethod);
                graphContainer.addEdge(classMethod, classS);

            });
        });
        return graphContainer;
    }

    public static void main(String[] args) {
        new GraphCreator().makeGraph("D:\\Studia\\sem_5\\IO\\IO-repo-actual\\FirstScenarioIO\\src\\main\\java\\storytwo\\methdependencies");
    }
}
