
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DependenciesCounter {
    public DependenciesCounter() {}

    void findAllDependenciesforNodes(){

    }

    int findDependencies(File file, String className) throws FileNotFoundException {

        int dependenciesFound=0;
		
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

