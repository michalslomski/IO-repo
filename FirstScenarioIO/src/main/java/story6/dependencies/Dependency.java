package story6.dependencies;
/*
Author: BeGieU
Date: 04.12.2019
*/

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Dependency {
    private final String className;
    private final Set<String> methodNames = new HashSet<>();

    public Dependency(String className) {
        this.className = className;
    }

    public void addMethodName(String mName) {
        methodNames.add(mName);
    }

    public String getClassName() {
        return className;
    }

    public Set<String> getMethodNames() {
        return methodNames;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Dependency that = (Dependency) o;
        return className.equals(that.className);
    }

    @Override public int hashCode() {
        return Objects.hash(className);
    }
}
