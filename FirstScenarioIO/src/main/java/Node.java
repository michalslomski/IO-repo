import java.io.File;
import java.util.HashMap;

public class Node {
    long fileSize;
    File file;                                  //file.getName() to sourceFileName w grafie
    HashMap<String,Integer> dependencies;       //key: targetFileName, value: weight of connection
                                                //zawiera wszystkie zależności jakie znaleziono dla pliku file
}
