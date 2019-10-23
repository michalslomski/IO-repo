import java.io.File;
import java.util.HashMap;

public class Node {
    int fileSize;               //gdzieś trzeba uzupełnić to pole
								//double bytes = file.length();
    File file;
    HashMap<String,Integer> connectionsWeights;     //key: className, value: weight of connection

}
