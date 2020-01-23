package storyfour.export;

import com.jamesmurty.utils.XMLBuilder2;
import storyfour.model.UniversalObject;
import storyfour.model.XMLMaker;
import storyone.fileoperations.DependenciesCounter;
import storyone.fileoperations.FileParser;
import storyone.fileoperations.FileReader;
import storyone.graph.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Loading dependencies from story one to UniversalObjectList
 * Each dependency and object is made as an object of UniversalObject class
 */
public class ExportDiagramOne {

    public static void exportStory1() throws IOException {
        DependenciesCounter dependenciesCounter = new DependenciesCounter();
        FileReader fileReader = new FileReader();
        FileParser fileParser = new FileParser();
        List<File> fileList;

        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "\\FirstScenarioIO\\src\\main\\java\\storyone";
        fileList = fileReader.findAllFilesInDepth(path);

        List<UniversalObject> universalObjectsList = new ArrayList<>();

        for (File file : fileList) {
            String name = file.getName();
            UniversalObject newUniversalObject = new UniversalObject(name);
            universalObjectsList.add(newUniversalObject);

            HashMap<String, Integer> map1 = new HashMap<>();
            HashMap<String, Integer> map2 = new HashMap<>();

            map1 = dependenciesCounter.findAllDependencies(fileReader.getAllClassesNamesInsideProject(path), file);
            List<String> dependenciesList = fileParser.parseFile(file);
            map2 = dependenciesCounter.findAllDependencies(dependenciesList, file);
            map1.putAll(map2);

            for (String dName : map2.keySet()) {

                UniversalObject newDependency = new UniversalObject(dName);
                newUniversalObject.addDependency(newDependency);
                universalObjectsList.add(newDependency);

            }

        }


        XMLMaker creator = new XMLMaker();
        creator.addClassesWithDependencies(universalObjectsList);

        final String xmlFilePath = projectPath + "\\FirstScenarioIO\\src\\main\\resources\\xmlfileFirstDiagram.xml";
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

    public static void main(String[] args) throws IOException {
        exportStory1();
    }

}
