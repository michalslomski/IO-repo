package storyfour.model;

import com.jamesmurty.utils.XMLBuilder2;

import javax.xml.transform.OutputKeys;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * CLass creates builder of XML type file
 * The idea of XML file in this case is to be imported to VisualParadigm as a diagram
 * Class< ID = id of an object, Name = object name > - description of an object
 * Usage<From = from which object(id), To - to which object(id), ID - id of a connection> - representation of an edge between two objects
 */

public class XMLMaker {

    private XMLBuilder2 builder;
    private Properties properties;
    private  Integer elementsId ;

    public XMLMaker() {
        builder = XMLBuilder2.create("Project")
                .a("DocumentationType", "html")
                .a("ExporterVersion", "12.2")
                .a("Name", "untitled")
                .a("UmlVersion", "2.x")
                .a("Xml_structure", "simple")
                .e("Models");
        properties = new Properties();
        properties.put(OutputKeys.METHOD, "xml");
        properties.put(OutputKeys.INDENT, "yes");
        properties.put("{http://xml.apache.org/xslt}indent-amount", "2");
       elementsId = UniversalObject.getIds();
    }

    private List<Generalization> doGeneralizations(List<UniversalObject> dependencies) {
        List<Generalization> generalizations = new ArrayList<>();
        for (UniversalObject fromDependency : dependencies) {
            for (Map.Entry<UniversalObject, Integer> entry : fromDependency.getDependencyList().entrySet()) {
                UniversalObject toDependency = entry.getKey();
                Generalization generalization = new Generalization(fromDependency.getId(), toDependency.getId());
                generalizations.add(generalization);
            }
        }
        return generalizations;
    }


    public XMLBuilder2 getBuilder() {
        return builder;
    }

    public Properties getProperties() {
        return properties;
    }

    public void addClassesWithDependencies(List<UniversalObject> dependencies) {
        for (UniversalObject dependencyObj : dependencies) {
            builder.e("Class")
                    .a("Id", dependencyObj.getId().toString())
                    .a("Name", dependencyObj.getName())
                    .up();
        }

        List<Generalization> generalizations = doGeneralizations(dependencies);
        for (Generalization generalization : generalizations) {
            builder.e("Usage")
                    .a("Id", elementsId.toString())
                    .a("From", generalization.getFrom().toString())
                    .a("To", generalization.getTo().toString())
                    .up();
            elementsId++;
        }
    }

}
