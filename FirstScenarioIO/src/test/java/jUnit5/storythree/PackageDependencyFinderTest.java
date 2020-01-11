package jUnit5.storythree;

import org.junit.jupiter.api.Test;
import storythree.DependencyObject;
import storythree.PackageDependencyFinder;
import storythree.PackageReader;

import java.io.IOException;
import java.util.List;

public class PackageDependencyFinderTest {

    @Test
    public void shouldNotThrowException() {
        try {
            PackageReader prTest = new PackageReader();
            PackageDependencyFinder pdfTest = new PackageDependencyFinder();
            List<DependencyObject> testList = prTest.packageDependency();
            List<DependencyObject> testReturnList = pdfTest.packageDependencyFinder(testList);

        }
        catch(IOException exception){
            exception.printStackTrace();
        }

    }
}
