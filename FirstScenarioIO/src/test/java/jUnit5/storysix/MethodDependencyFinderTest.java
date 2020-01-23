package jUnit5.storysix;

import org.junit.jupiter.api.Test;
import story6.dependencies.Dependency;
import story6.dependencies.MethodDependenciesFinder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MethodDependencyFinderTest {

    @Test
    public void shouldNotThrowAnyException(){
        MethodDependenciesFinder mdfTest = new MethodDependenciesFinder();
        Set<Dependency> testSet;
        String projectPath = System.getProperty("user.dir");
        String testPath = projectPath+"\\src\\main\\java\\storyone";

        testSet = mdfTest.getMethodFileDependency(testPath);
    }

    @Test
    public void shouldThrowIllegalArgumentException(){
        Exception exception = assertThrows(IllegalArgumentException.class, () ->{
            MethodDependenciesFinder mdfTest = new MethodDependenciesFinder();
            Set<Dependency> testSet;
            String projectPath = System.getProperty("user.dir");
            String testWrongPath = projectPath+"\\src\\main\\java\\notexistingdir";

            testSet = mdfTest.getMethodFileDependency(testWrongPath);
        });
    }
}
