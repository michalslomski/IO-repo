package storythree;

/**
 * Author: Michal Slomski
 * Date: 09.11.2019
 */
import java.util.HashMap;

public class DependencyObject implements Readable{

    private String packageName;
    private String methodName;
    private Integer weight;
    private HashMap<DependencyObject,Integer> mapOfDependenciesForEachObject = new HashMap<>();
    private Integer cyclomaticComplexity;

    public DependencyObject(String packageName, String methodName) {
        this.packageName = packageName;
        this.methodName = methodName;
        this.weight = 0;
        this.cyclomaticComplexity=0;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public HashMap<DependencyObject, Integer> getMapOfDependenciesForEachObject() {
        return mapOfDependenciesForEachObject;
    }

    public void setMapOfDependenciesForEachObject(HashMap<DependencyObject, Integer> mapOfDependenciesForEachObject) {
        this.mapOfDependenciesForEachObject = mapOfDependenciesForEachObject;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public HashMap<DependencyObject, Integer> getList() {
        return  mapOfDependenciesForEachObject;
    }

    public void setList(HashMap<DependencyObject, Integer> list) {
        this. mapOfDependenciesForEachObject = list;
    }
        @Override
    public String toString() {
        if(methodName != "" && cyclomaticComplexity!=null)
            return methodName + "\n" + Integer.toString(weight) + "\n" + "CC: " +cyclomaticComplexity;
        else
            return packageName + "\n" + Integer.toString(weight);
    }

    public Integer getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(Integer cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }
}
