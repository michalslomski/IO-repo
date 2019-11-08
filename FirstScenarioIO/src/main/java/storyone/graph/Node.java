package storyone.graph;

import java.io.File;
import java.util.HashMap;

public class Node {
    public long fileSize;
    public File file;                                  //file.getName() to sourceFileName w grafie
    public HashMap<String,Integer> dependencies;       //key: targetFileName, value: weight of connection
                                                //zawiera wszystkie zależności jakie znaleziono dla pliku file
}
