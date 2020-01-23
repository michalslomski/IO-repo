package storyfour.model;

import java.util.HashMap;
import java.util.Map;

/**
 Class that represents an object of any type of dependency
 */
public class UniversalObject {

    String name;
    HashMap<UniversalObject,Integer> dependencyList = new HashMap<>();
    Integer id;
    static Integer ids = 1;

    public UniversalObject(String name) {
        this.name = name;
        id = ids;
        ids++;
    }


    public boolean checkIfListContainsName(String name){
        for (Map.Entry<UniversalObject, Integer> entry : dependencyList.entrySet()) {
            if(entry.getKey().getName().equals(name)){return true;}
        }
        return false;
    }

    public void addDependency(UniversalObject universalObject) {
        if (dependencyList.containsKey(universalObject)) {
            dependencyList.put(universalObject, (dependencyList.get(universalObject) + 1));
        } else {
            dependencyList.put(universalObject, 1);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<UniversalObject, Integer> getDependencyList() {
        return dependencyList;
    }


    public Integer getId() {
        return id;
    }


    public static Integer getIds() {
        return ids;
    }


    @Override
    public String toString() {
        return "UniversalObject{" +
                "name='" + name + '\'' +
                ", dependencyList=" + dependencyList +
                ", id=" + id +
                '}';
    }
}
