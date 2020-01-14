package storythree;

import storyone.fileoperations.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FilesForStoryThree {

    private List<File> listOfFiles;

    public FilesForStoryThree() throws IOException {

        List<File> ListStoryMain;
        List<File> ListStory1;
        List<File> ListStory2;
        List<File> ListStory3;

        FileReader fileReader = new FileReader();
        String projectPath = System.getProperty("user.dir");

        String pathMain = projectPath+"\\src\\main\\java\\mainpackage";
        ListStoryMain=fileReader.findAllFilesInDepth(pathMain);

        String path1 = projectPath+"\\src\\main\\java\\storyone";
        ListStory1=fileReader.findAllFilesInDepth(path1);

        String path2 = projectPath+"\\src\\main\\java\\storytwo";
        ListStory2=fileReader.findAllFilesInDepth(path2);

        String path3 = projectPath+"\\src\\main\\java\\storythree";
        ListStory3=fileReader.findAllFilesInDepth(path3);

        ListStoryMain.addAll(ListStory1);
        ListStoryMain.addAll(ListStory2);
        ListStoryMain.addAll(ListStory3);

        listOfFiles=ListStoryMain;
    }

    public List<File> getList(){
        return this.listOfFiles;
    }





}
