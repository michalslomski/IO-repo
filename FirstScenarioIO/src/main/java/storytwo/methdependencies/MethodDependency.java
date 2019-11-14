package storytwo.methdependencies;

/**
 * Author: Marcin Plywacz
 * Date: 07.11.2019
 */

import com.github.javaparser.ast.body.MethodDeclaration;

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
    private Integer weight;
    MethodDeclaration methodDeclaration;
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
    }

    public String getCallingMethodName() {
        return callingMethodName;
    }

    public Map<String, Integer> getDependencyMap() {
        return dependencyMap;
    }

    public void setCallingMethodName(String callingMethodName) {
        this.callingMethodName = callingMethodName;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
    }

    public void setDependencyMap(Map<String, Integer> dependencyMap) {
        this.dependencyMap = dependencyMap;
    }

    @Override
    public String toString() {
        return "MethodDependency{" +
                "callingMethodName='" + callingMethodName + '\'' +
                ", dependencyMap=" + dependencyMap +
                '}';
    }


}
