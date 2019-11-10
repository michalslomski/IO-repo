package storytwo.methdependencies;

/**
 * Author: Marcin Plywacz
 * Date: 07.11.2019
 */

import java.util.HashMap;
import java.util.Map;

/*
 * callingMethodName : nazwa metody dla ktorej ta klasa zawiera zaleznosci
 * dependencyMap: mapa ktora zawiera nazwy wywolanych metod jako klucze,
 *  liczbe razy jako wartosci
 *
 * kazdy klucz mapy oznacza nazwe metody ktora byla wywolana przez metode wywolujaca,
 * wartosc klucza to liczba razy
 */
public class MethodDependency {
    /**
     * contains name of method to which belongs dependencies
     */
    private String callingMethodName;
    /**
     * contains dependencies for calling method,
     * keys are names of called methods from callingMethodName's body
     * values are numbers meaning how many times
     * <p>
     * format of the dependencyMap:
     * "name of called method" -> how many times
     */
    private Map<String, Integer> dependencyMap = new HashMap<>();

    public MethodDependency(String methodName) {
        this.callingMethodName = methodName;
    }

    public void addDependency(String dependency) {
        dependencyMap.merge(dependency, 1, Integer::sum);
        //above code does the same as below code
//        Integer count = dependencyMap.get(dependency);
//        if (count == null)
//            dependencyMap.put(dependency, 1);
//        else
//            dependencyMap.put(dependency, count + 1);1

    }

    public String getCallingMethodName() {
        return callingMethodName;
    }

    public Map<String, Integer> getDependencyMap() {
        return dependencyMap;
    }

    @Override
    public String toString() {
        return "MethodDependency{" +
                "callingMethodName='" + callingMethodName + '\'' +
                ", dependencyMap=" + dependencyMap +
                '}';
    }
}
