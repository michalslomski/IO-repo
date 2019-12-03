package storythree.graph;

import storythree.DependencyObject;

import java.util.ArrayList;
import java.util.List;

public class Packages {
    public List<DependencyObject> getPackages(List<DependencyObject> dependencyObjects){
        List<DependencyObject> packages=new ArrayList<>();

        for (DependencyObject dependencyObject : dependencyObjects) {

            if (dependencyObject.getMethodName() == "") {
                packages.add(dependencyObject);
            }

        }

        return packages;
    }
}
