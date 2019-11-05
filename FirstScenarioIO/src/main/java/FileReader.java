/**
 * Author: Marcin Plywacz
 * Date: 22.10.2019
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/***
 * Reads all files  with DEMANDED_EXTENSION from given directory and it's subdirectories
 */
public class FileReader {
    public static final String DEMANDED_EXTENSION = ".java";

    private final Queue<File> directoriesQueue = new LinkedList<>();

    public FileReader() {
    }

    /**
     * @param path path to dir to be scanned
     * @return list of files with DEMANDED_EXTENSION in baseDir and it's subdirectories
     * @throws IOException when path is incorrect
     */
    public List<File> findAllFilesInDepth(final String path) throws IOException {
        File baseDir = this.getDir(path);
        directoriesQueue.add(baseDir);

        List<File> filesList = new LinkedList<>();
        while (!directoriesQueue.isEmpty()) {
            File currentDir = directoriesQueue.remove();
            File[] currentFiles = currentDir.listFiles();
            if (currentFiles == null) {
                continue;
            }
            for (File file : currentFiles) {
                if (file.isDirectory()) {
                    directoriesQueue.add(file);
                }
                else if (file.getName().endsWith(DEMANDED_EXTENSION)) {
                    filesList.add(file);
                }
            }
        }
        return filesList;
    }

    /**
     * checks if given path leads to directory
     * * @throws IOException
     */
    private File getDir(String path) throws IOException {
        File tmpFile = new File(path);
        if (!tmpFile.exists()) {
            throw new FileNotFoundException(" given directory doesnt exist");
        }
        else if (!tmpFile.canRead()) {
            throw new IOException("cannot read directory");
        }
        else if (!tmpFile.isDirectory()) {
            throw new IllegalArgumentException("given path should lead to directory not file ");
        }
        return tmpFile;
    }
    /*
    Zwraca listę klas które są w naszym projekcie np:
    DependenciesCounter.java
    "FileParser.java",
    "FileReader.java",
    "Node.java"
     */
    public List<String> getAllClassesNamesInsideProject(String path) throws IOException {
        List<File> filesList = findAllFilesInDepth(path);

        List<String>projectClassList = new ArrayList<>();
        filesList.forEach(file -> {
            projectClassList.add(file.getName());
        });

        return projectClassList;
    }
}
