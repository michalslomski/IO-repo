package storythree;

/**
 * Author: Michal Slomski
 * Date: 09.11.2019
 */

import storyone.fileoperations.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class PackageReader {

    /**
     * Method creates list of DependencyObjects with packages names and
     * method names with its packages names.
     *
     *
     * @return list of DependencyObjects. See DependencyObject class
     */

    public List<DependencyObject> packageDependency() throws IOException {

        Map<String,String> map;
        List<DependencyObject> objectList = new ArrayList<>();
        MethodBodyFinder mbf = new MethodBodyFinder();
        BufferedReader bf;
        FilesForStoryThree files= new FilesForStoryThree();
        List<File> listOfFiles=files.getList();
        String tmp = " ";

        for(File nameOfFile: listOfFiles){

            DependencyObject obj;
            bf= new BufferedReader(new java.io.FileReader(nameOfFile));
            String [] split;
            String firstLine;
            firstLine= bf.readLine();
            split= firstLine.split(" ");
            String packageName=split[1].split(";")[0];
            obj= new DependencyObject(packageName ,"");


            if(!obj.getPackageName().contains(tmp))
                objectList.add(obj);

            map=mbf.getMethodsBodies(nameOfFile.toString());
            for(String key: map.keySet()){
                obj= new DependencyObject(packageName,key);
                objectList.add(obj);
            }


        }

        return objectList;
    }
}