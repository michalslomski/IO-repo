package storythree.graph;

import storythree.DependencyObject;

import java.util.ArrayList;
import java.util.List;

public class Methods {
    public List<DependencyObject> getMethods(List<DependencyObject> dependencyObjects){
        List<DependencyObject> methods=new ArrayList<>();

        for (DependencyObject dependencyObject : dependencyObjects) {
            if (!(dependencyObject.getMethodName() == "")) {
                methods.add(dependencyObject);
            }

        }

        return methods;
    }
}
