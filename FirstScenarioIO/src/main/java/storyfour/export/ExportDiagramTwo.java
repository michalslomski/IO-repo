package storyfour.export;

import com.jamesmurty.utils.XMLBuilder2;
import storyfour.model.UniversalObject;
import storyfour.model.XMLMaker;

import storytwo.graph.NodeFunction;
import storytwo.methdependencies.MethodDependenciesFinder;
import storytwo.methdependencies.MethodDependency;


import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Loading dependencies from story two to UniversalObjectList
 * Each dependency and object is made as an object of UniversalObject class
 */
public class ExportDiagramTwo {
    public static void exportStory2() throws IOException {

        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "\\FirstScenarioIO\\src\\main\\java";

        MethodDependenciesFinder dependenciesFinder = new MethodDependenciesFinder();
        List<MethodDependency> methodDependencies = new LinkedList<>();
        methodDependencies = dependenciesFinder.getMethodDependencies(path);

        List<UniversalObject> universalObjectsList = new ArrayList<>();

        for (int i = 0; i < methodDependencies.size(); i++) {
            String name = methodDependencies.get(i).getCallingMethodFullName();
            UniversalObject universalObject = new UniversalObject(name);
            universalObjectsList.add(universalObject);
            Map<String, Integer> map = new HashMap<>();
            map = methodDependencies.get(i).getDependencyMap();
            for (String dName : map.keySet()) {
                UniversalObject dependency = new UniversalObject(dName);
                universalObject.addDependency(dependency);
                universalObjectsList.add(dependency);

            }

        }

        XMLMaker creator = new XMLMaker();
        creator.addClassesWithDependencies(universalObjectsList);

        final String xmlFilePath = projectPath + "\\FirstScenarioIO\\src\\main\\resources\\xmlfileSecondDiagram.xml";
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

    public static void main(String[] args) throws IOException{
        exportStory2();
    }

}
