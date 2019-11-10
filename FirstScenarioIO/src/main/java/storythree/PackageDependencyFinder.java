package storythree;
/***
 * Author: Damian Rodziewicz
 * Date: 10.11.2019
 */

import storyone.fileoperations.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PackageDependencyFinder {
    /***
     * This method gets the list of DependecyObjects and add a list of dependency to every object in this list
     * DependecyObject weight is referred to weight from method to package, and values in mapOfDependenciesForEachObject
     * is referred to weight of single vertex, weight of package package dependency is same as method package.
     * @param listOfDependencyObjects - list of DependencyObject from PackageReader class
     * @return ListOfDependencyObjects filled with list of DependencyObjects
     * @throws IOException
     */

    public List<DependencyObject> main(List<DependencyObject> listOfDependencyObjects) throws IOException {
        PackageReader pr = new PackageReader();
        storyone.fileoperations.FileReader fr = new FileReader();
        MethodBodyFinder mbf = new MethodBodyFinder();

        Integer dependencyCounter = 0;

        String projectPath=System.getProperty("user.dir");
        List<File> listOfFiles=fr.findAllFilesInDepth(projectPath+"\\FirstScenarioIO\\src\\main\\");

        Map<String,String> mapOfMethodBodies;

        for(DependencyObject obj : listOfDependencyObjects){

            for(File file : listOfFiles) {
                mapOfMethodBodies = mbf.getMethodsBodies(file.toString());
                for (Map.Entry<String, String> entry : mapOfMethodBodies.entrySet()){

                    if (entry.getValue().contains("." + obj.getMethodName() + "(")) {
                        dependencyCounter++;
                        obj.setWeight(obj.getWeight()+1);
                        DependencyObject tmp = new DependencyObject(obj.getPackageName(),entry.getKey());
                        tmp.setWeight(tmp.getWeight()+1);
                        obj.getList().put(tmp,dependencyCounter);

                    }
                }
            }
            dependencyCounter=0;
        }
        return listOfDependencyObjects;
    }
}
