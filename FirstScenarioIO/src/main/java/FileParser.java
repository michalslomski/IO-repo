/**
 *  Author: Damian Rodziewicz
 *  Data :  23.10.2019
 */

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds file name list of imported classes and size in bytes
 */

public class FileParser {
    private String fileName;
    private List<String> importList;
    private long size;

    public FileParser() {
        this.importList = new ArrayList<>();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getImportList() {
        return importList;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    /**
     *
     * @param filename - method takes file as parametr
     * @throws IOException - throws exceptions when file is not found
     */
    public FileParser parsingFile(File filename) throws IOException {
        FileParser fp = new FileParser();
        if(!filename.exists()){
            throw new FileNotFoundException("File doesn't exist");
        }
        if(!filename.canRead()){
            throw new IOException("Cannot read file");
        }

        BufferedReader br = new BufferedReader(new FileReader(filename));
        fp.setFileName(filename.getName());
        fp.setSize(filename.length());

        String st;
        String[] split;
        while ((st = br.readLine()) != null) {
            split = st.split(" ",2);
            if (st.contains("import"))
                if(!split[1].contains("*") && !split[1].contains("import"))
                    fp.importList.add(split[1]);
        }



        /**
         * Zwraca obiekt typu FileParser w którym zawarte są pola:
         * String nazwa_pliku
         * ArrayList<String> lista importowanych klas
         * long rozmiar_pliku
         */
        return fp;
    }

}