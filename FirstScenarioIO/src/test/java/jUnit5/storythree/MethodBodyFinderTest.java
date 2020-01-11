package jUnit5.storythree;

import org.junit.jupiter.api.Test;
import storythree.MethodBodyFinder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MethodBodyFinderTest {



    @Test
    public void singleFileMethodBodyTest(){
        try {
            String projectPath = System.getProperty("user.dir");
            String path = projectPath+"\\src\\main\\java\\storythree\\MethodBodyFinder.java";
            MethodBodyFinder mbfTest = new MethodBodyFinder();
            Map<String, String> testMap = mbfTest.getMethodsBodies(path);

        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void shouldThrowFileNotFoundException(){
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            String projectPath = System.getProperty("user.dir");
            String path = projectPath+"\\src\\main\\java\\storythree\\";

            MethodBodyFinder mbfTest = new MethodBodyFinder();
            Map<String, String> testMap = mbfTest.getMethodsBodies(path);
                });
    }


}
