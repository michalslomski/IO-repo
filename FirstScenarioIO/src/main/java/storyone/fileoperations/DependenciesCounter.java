package storyone.fileoperations; /**
 *  Author: Michał Prochwicz
 *  Data :  23.10.2019
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Counting how many dependencies of some class were used in file
 */

public class DependenciesCounter {
    public DependenciesCounter() {}

    /**
     * @param classesNamesList list of classes names to check
     * @param file where searching for dependencies
     * @return map of classes names (as keys) and numbers of how many time class were used in file (as values)
     */
    public HashMap<String,Integer> findAllDependencies(List<String> classesNamesList, File file){
        HashMap<String,Integer> weightsMap = new HashMap<String,Integer>();

        for (String str : classesNamesList) {
            int depsFound = 0;
            String[] split = str.split("\\.");
            String searchingName = split[split.length - 1];
            if(searchingName.equals("java"))
                searchingName= split[0];
            try {
                depsFound = findDependencies(file, searchingName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            weightsMap.put(str, depsFound);
        }
        return weightsMap;
    }

    /**
     * @param className class name to check
     * @param file where searching for dependencies
     * @return number of found class matches in file
     */
    public int findDependencies(File file, String className) throws FileNotFoundException {

        int dependenciesFound=0;
	String[] split = className.split(";");
        className = split[0];
        String phrase=className;

        Scanner fileScanner = new Scanner(file);
        Pattern pattern =  Pattern.compile(phrase);
        Matcher matcher = null;
	    
        while(fileScanner.hasNextLine()){
            String line = fileScanner.nextLine();
            matcher = pattern.matcher(line);
            while (matcher.find())
            {
                dependenciesFound++;
            }
        }
        return dependenciesFound;
    }
}

