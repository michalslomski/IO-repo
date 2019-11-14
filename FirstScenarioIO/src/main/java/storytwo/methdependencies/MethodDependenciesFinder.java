package storytwo.methdependencies;

/**
 * Author: Marcin Plywacz
 * Date: 07.11.2019
 */


import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.utils.SourceRoot;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MethodDependenciesFinder {
    /**
     * Method scans given directory for classes and finds every method
     * declared in those classes. Then extracts every inside method called from ours class'es methods.
     * then makes dependencies
     *
     * @param dirPath path to directory to be scanned
     * @return list of dependencies. See MethodDependency class
     */

    public List<MethodDependency> getMethodDependencies(String dirPath) {
        //root is folder where we start scanning for classes
        SourceRoot root = new SourceRoot(Paths.get(dirPath));
        List<ParseResult<CompilationUnit>> parsingResults = root.tryToParseParallelized();

        //we get every compiled unit for existing class
        List<CompilationUnit> compilationUnitList =
                parsingResults
                        .stream()
                        .map(ParseResult::getResult)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

        //finds all defined methods from classes that are in root folder.
        List<MethodDependency> methodsDefinedInsidePath = new ArrayList<>();
        for (CompilationUnit cu : compilationUnitList) {
            for (TypeDeclaration typeDeclaration : cu.getTypes()) {
                List<BodyDeclaration> bodyDeclarations = typeDeclaration.getMembers();
                for (BodyDeclaration bodyDeclaration : bodyDeclarations) {
                    if (!bodyDeclaration.isMethodDeclaration())
                        continue;
                    if (typeDeclaration.getClass() != ClassOrInterfaceDeclaration.class)
                        throw new RuntimeException("Unexpected Output");

                    //builds class name
                    StringBuilder className = new StringBuilder();
                    cu.getPackageDeclaration().ifPresent(pd -> className.append(pd.getNameAsString()).append(" "));

                    className.append(typeDeclaration.getNameAsString()).append(".");

                    MethodDeclaration methodDeclaration = (MethodDeclaration) bodyDeclaration;
                    className.append(methodDeclaration.getNameAsString());

                    MethodDependency methodDep = new MethodDependency(
                            className.toString());
                    methodDep.setMethodDeclaration(methodDeclaration);
                    methodsDefinedInsidePath.add(methodDep);
                }
            }
        }

        List<MethodDependency> methodDependencies = new LinkedList<>();
        //for every method from root folder extract every called method and add to dependencies list
        methodsDefinedInsidePath.forEach(outsideMethod -> {
            MethodDependency methodDep = new MethodDependency(outsideMethod.getCallingMethodFullName());

            outsideMethod.getMethodDeclaration().walk(MethodCallExpr.class, methodInside -> {
                methodDep.addDependency(methodInside.getNameAsString());
            });
            methodDependencies.add(methodDep);
        });

        //go through all inside methods, and make weights
        methodsDefinedInsidePath.forEach(outsideMethod -> {
            outsideMethod.getMethodDeclaration().walk(MethodCallExpr.class, methodInside -> {
               makeWeights(methodsDefinedInsidePath, methodDependencies, methodInside);
            });
        });

        return methodDependencies;
    }

    //checks if inside method is one of methods defined in path if so increment appropriate weight
    private void makeWeights(List<MethodDependency> methodsDefinedInPath,
                             List<MethodDependency> methodDependencies,
                             MethodCallExpr insideMethod) {


        methodsDefinedInPath.forEach(definedMethod -> {
            //if inside method is one of declared method
            if (definedMethod.getShortName().equals(insideMethod.getNameAsString())) {
                //then find that declared method in dependencies list and increment it's weight
                methodDependencies.forEach(methodDependency -> {
                    if (methodDependency.getShortName().equals(insideMethod.getNameAsString())){
                        methodDependency.incrementWeight();
                    }

                });
            }

        });

    }
}