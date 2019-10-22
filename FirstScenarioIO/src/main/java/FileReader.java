/**
Author: Marcin Plywacz
Date: 22.10.2019
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

    private final List<File> fileList = new ArrayList<>();
    private final Queue<File> directoriesQueue = new LinkedList<>();
    private final File primaryDir; //folder from where we start search for files and subdirectories

    public FileReader(String primaryDirPath) throws IOException {
        File tmpFile = new File(primaryDirPath);
        if (!tmpFile.exists()) {
            throw new FileNotFoundException("cannot load given directory");
        }
        else if (!tmpFile.canRead()) {
            throw new IOException("cannot read directory");
        }
        else if (!tmpFile.isDirectory()) {
            throw new IllegalArgumentException("given path should lead to directory ont file ");
        }
        primaryDir = tmpFile;
        findAllFilesInDepth();
    }

    /**
     * fills in fileList  with files that ends with DEMANDED_EXTENSION
     */
    private void findAllFilesInDepth() {
        directoriesQueue.add(primaryDir);
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
                    fileList.add(file);
                }
            }
        }
    }

    /**
     * @returns all files with ".java" extension in given path and it's subdirectories
     */
    public List<File> getFileList() {
        return fileList;
    }

    /**
     * @returns returns folder where search begun
     */
    public File getPrimaryDir() {
        return primaryDir;
    }

}
