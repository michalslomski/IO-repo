package jUnit5.storythree;

import org.junit.jupiter.api.Test;
import storythree.DependencyObject;
import storythree.PackageReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PackageReaderTest {

    @Test
    public void shouldReturnPackageDependencyList(){
        try {
            PackageReader prTest = new PackageReader();
            List<DependencyObject> objectTestList = new ArrayList<>();
            objectTestList = prTest.packageDependency();

            assertNotEquals(objectTestList.size(),0);
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }


}
