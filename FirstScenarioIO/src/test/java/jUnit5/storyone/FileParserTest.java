package jUnit5.storyone;

import org.junit.jupiter.api.Test;
import storyone.fileoperations.FileParser;
import storyone.fileoperations.FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileParserTest {
    FileReader fr = new FileReader();
    String projectPath = System.getProperty("user.dir");
    String wrongPath = projectPath+"\\src\\main\\java\\wrongPath";
    String path = projectPath+"\\src\\main\\java\\storyone";
    List<File> testList;

    @Test
    public void shouldNotThrowAnyException(){
        try {
            FileParser fp = new FileParser();
            testList = fr.findAllFilesInDepth(path);

            List<String> testDependencyList = fp.parseFile(testList.get(0));
            assertEquals(testDependencyList.size(),7);
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void shouldNotThrowAnyExceptionToo(){
        try {
            FileParser fp = new FileParser();
            FileParser testObject;
            testList = fr.findAllFilesInDepth(path);

            testObject = fp.parsingFile(testList.get(0)); // DependenciesCounter.java size = 2293
            assertFalse(testObject.getSize() == 0);
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void shouldThrowIoException(){
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            FileParser fp = new FileParser();
            File notExisitngFile = new File(wrongPath) ;

            fp.parseFile(notExisitngFile);
        });

        String expectedMessage = "File doesn't exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getterAndSetterTest(){
        FileParser fp = new FileParser();

        fp.setFileName("TestName");
        assertEquals(fp.getFileName(),"TestName");

        fp.setSize(200);
        assertEquals(fp.getSize(),200);
    }
}
