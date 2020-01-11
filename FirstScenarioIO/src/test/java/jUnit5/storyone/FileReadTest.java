package jUnit5.storyone;



import org.junit.jupiter.api.Test;
import storyone.fileoperations.FileReader;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileReadTest {
    FileReader fr = new FileReader();

    @Test
    public void shouldNotThrowAnyException(){
        try {
            List<File> testFilesList;
            String projectPath = System.getProperty("user.dir");
            String path = projectPath+"\\src\\main\\java\\storyone";
            testFilesList = fr.findAllFilesInDepth(path);

            assertEquals(testFilesList.size(), 8);

        } catch (IOException exception) {
            exception.printStackTrace();
        }


    }

    @Test
    public void shouldThrowFileNotFoundException(){
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            List<File> testFileList;
            String projectPath = System.getProperty("user.dir");
            String path = projectPath+"\\src\\main\\java\\wrongdirectory";
            testFileList = fr.findAllFilesInDepth(path);
        });

        String expectedMessage = " given directory doesnt exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldGetClassNameList(){
        try{
            List<String> testClassNameList;
            String projectPath = System.getProperty("user.dir");
            String path = projectPath+"\\src\\main\\java\\storyone";
            testClassNameList = fr.getAllClassesNamesInsideProject(path);

            assertEquals(testClassNameList.size(),8);
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }

}
