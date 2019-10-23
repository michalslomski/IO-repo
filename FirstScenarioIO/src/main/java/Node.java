import java.io.File;
import java.util.HashMap;

public class Node {
    int fileSize;               			//pobrać z FileParser
    File file;
    HashMap<String,Integer> connectionsWeights;    	//key: className, value: weight of connection

}
