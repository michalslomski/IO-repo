package story6.dependencies;
/**
 Author: Marcin Pływacz
 Date: 04.12.2019
 */

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.utils.SourceRoot;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class MethodDependenciesFinder {
    /**
     * maps declared method names with class name where declaration is
     *
     * @param path path to the directory to be scanned for classes
     * @return map that contains method name as Key and class name  where this method was declared as value
     */
    /*
     *  (nazwa deklarowanej metody) -> (nazwa klasy gdzie ta metoda została zadeklarownana)
     *
     */
    public Set<Dependency> getMethodFileDependency(String path) {
        SourceRoot root = new SourceRoot(Paths.get(path));
        List<ParseResult<CompilationUnit>> parsingResults = root.tryToParseParallelized();

        //we get every compiled unit for existing class
        List<CompilationUnit> compilationUnitList =
                parsingResults
                        .stream()
                        .map(ParseResult::getResult)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

        Set<Dependency> dependencySet = new HashSet<>();
        for (CompilationUnit cu : compilationUnitList) {
            for (TypeDeclaration typeDeclaration : cu.getTypes()) {
                List<BodyDeclaration> bodyDeclarations = typeDeclaration.getMembers();

                StringBuilder className = new StringBuilder();
                cu.getPackageDeclaration().ifPresent(pd -> className.append(pd.getNameAsString()).append(" "));
                className.append(typeDeclaration.getNameAsString());
                Dependency dependency = new Dependency(className.toString());

                for (BodyDeclaration bodyDeclaration : bodyDeclarations) {
                    if (!bodyDeclaration.isMethodDeclaration())
                        continue;
                    MethodDeclaration methodDeclaration = (MethodDeclaration) bodyDeclaration;
                    String methodName = methodDeclaration.getNameAsString();
                    dependency.addMethodName(methodName);

                    dependencySet.add(dependency);
                }
            }
        }

        return dependencySet;
    }


    public static void main(String[] args) {
        new MethodDependenciesFinder()
                .getMethodFileDependency(
                        "D:\\Studia\\sem_5\\IO\\IO-repo-actual\\FirstScenarioIO\\src\\main\\java\\storytwo\\methdependencies");
    }
}
