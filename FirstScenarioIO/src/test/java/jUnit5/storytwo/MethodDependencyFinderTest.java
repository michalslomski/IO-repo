package jUnit5.storytwo;

import org.junit.jupiter.api.Test;
import storytwo.methdependencies.MethodDependenciesFinder;
import storytwo.methdependencies.MethodDependency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodDependencyFinderTest {


    @Test
    public void shouldNotThrowExceptions(){
        MethodDependenciesFinder mdfTest = new MethodDependenciesFinder();
        List<MethodDependency> testList;
        String path = System.getProperty("user.dir");
        String dirPath = path + "\\src\\main\\java";

        try{
            testList = mdfTest.getMethodDependencies(dirPath);
        }
        catch(RuntimeException exception){
            exception.printStackTrace();
        }


    }
    @Test
    public void shouldThrowIllegalArgumentExceptions(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        MethodDependenciesFinder mdfTest = new MethodDependenciesFinder();
        List<MethodDependency> testList;
        String path = System.getProperty("user.dir");
        String dirPath = path + "\\src\\java\\storytwo";

        testList = mdfTest.getMethodDependencies(dirPath);
        });

    }
}
