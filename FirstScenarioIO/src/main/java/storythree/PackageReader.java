package storythree;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.lang.Object;

import static java.util.stream.Collectors.toList;

/***
 *  Change "src\\main" to your directory with project
 *  Class returns set with name of packages in project
 */

public class PackageReader {
    public Set<String> getPackages() {

        Set<String> packages=new HashSet<>();
        listOfPackage("src\\main",packages);

        return packages;
    }

    public static void listOfPackage(String directoryName, Set<String> pack) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                String path=file.getPath();
                String packName=path.substring(path.indexOf("src")+4, path.lastIndexOf('\\'));
                pack.add(packName.replace('\\', '.'));
            } else if (file.isDirectory()) {

                listOfPackage(file.getAbsolutePath(), pack);
            }
        }
    }
}