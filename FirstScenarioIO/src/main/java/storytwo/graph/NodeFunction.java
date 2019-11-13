package storytwo.graph;

import java.util.HashMap;
import java.util.Map;

public class NodeFunction {             // Node which includes name of a function and map with all of the dependencies
                                       // between other functions
    private String functionName;

    private Map<String,Integer> functions = new HashMap<>(); //key: name of a function, value: weight of connections with 'functionName'
                                                            //HashMap which stores all of the functions dependencies with 'functionName'

    private int callingCounter;  // counter which counts how many times particular function is called in program

    public NodeFunction(String functionName, Map<String, Integer> functions) {
        this.functionName = functionName;
        this.functions = functions;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Map<String, Integer> getFunctions() {
        return functions;
    }
}
