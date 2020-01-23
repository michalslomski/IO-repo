package storyfour.export;

import com.jamesmurty.utils.XMLBuilder2;
import storyfour.model.UniversalObject;
import storyfour.model.XMLMaker;
import storythree.DependencyObject;
import storythree.PackageDependencyFinder;
import storythree.PackageReader;
import storythree.graph.Methods;
import storythree.graph.Packages;

import java.io.*;
import java.util.*;

/**
 * Loading dependencies from story three to UniversalObjectList
 * Each dependency and object is made as an object of UniversalObject class
 */
public class ExportDiagramThree {

    public static void exportStory3() throws IOException {

        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "\\FirstScenarioIO\\src\\main\\java";

        List<DependencyObject> dependencyObjectList = new ArrayList<>();
        PackageReader pr = new PackageReader();
        dependencyObjectList = pr.packageDependency();

        PackageDependencyFinder dependencyFinder = new PackageDependencyFinder();
        dependencyObjectList = dependencyFinder.packageDependencyFinder(dependencyObjectList);

        List<UniversalObject> universalObjectsList = new ArrayList<>();

        List<DependencyObject> packages = new ArrayList<>();
        List<DependencyObject> methods = new ArrayList<>();
        Packages p = new Packages();
        packages = p.getPackages(dependencyObjectList);
        Methods m = new Methods();
        methods = m.getMethods(dependencyObjectList);
        List<String> methodListNames = new ArrayList<>();
        List<String> packagesListNames = new ArrayList<>();


        for (DependencyObject dependencyObject : dependencyObjectList) {
            String methodName = dependencyObject.getMethodName();
            String packageName = dependencyObject.getPackageName();
            if (methodName.equals("")) continue;
            boolean contains = methodListNames.contains(methodName);
            if (!contains) {
                methodListNames.add(methodName);

                UniversalObject universalObject = new UniversalObject(methodName);

                universalObjectsList.add(universalObject);

                Map<DependencyObject, Integer> map = new HashMap<>();
                map = dependencyObject.getMapOfDependenciesForEachObject();

                if (!(packagesListNames.contains(packageName))) {

                    UniversalObject universalObjectPackage = new UniversalObject(packageName);
                    packagesListNames.add(packageName);
                    universalObjectsList.add(universalObjectPackage);
                    universalObjectPackage.addDependency(universalObject);

                }

                for (DependencyObject dep : map.keySet()) {
                    String dependencyName = dep.getMethodName();
                    UniversalObject newDependency = new UniversalObject(dependencyName);
                    universalObject.addDependency(newDependency);

                }
            }
            if (contains) {
                for (int i1 = 0; i1 < universalObjectsList.size(); i1++) {
                    if (methodName.equals(universalObjectsList.get(i1).getName())) {
                        Map<DependencyObject, Integer> map = new HashMap<>();
                        map = dependencyObject.getMapOfDependenciesForEachObject();

                        for (DependencyObject dep : map.keySet()) {
                            String dependencyName = dep.getMethodName();
                            UniversalObject newDependency = new UniversalObject(dependencyName);
                            universalObjectsList.get(i1).addDependency(newDependency);
                        }
                    }
                }
            }

            XMLMaker creator = new XMLMaker();
            creator.addClassesWithDependencies(universalObjectsList);

            final String xmlFilePath = projectPath + "\\FirstScenarioIO\\src\\main\\resources\\xmlfileThirdDiagram2.xml";
            File file = new File(xmlFilePath);
            try {
                XMLBuilder2 builder = creator.getBuilder();
                PrintWriter writer = new PrintWriter(new FileOutputStream(file));
                Properties properties = creator.getProperties();
                builder.toWriter(writer, properties);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        exportStory3();
    }
}