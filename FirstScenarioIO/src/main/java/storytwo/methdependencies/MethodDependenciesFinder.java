package storytwo.methdependencies;

/**
 * Author: Marcin Plywacz
 * Date: 07.11.2019
 */


import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
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
        List<MethodDeclaration> pathMethodsList = new ArrayList<>();
        for (CompilationUnit cu : compilationUnitList) {
            for (TypeDeclaration typeDeclaration : cu.getTypes()) {
                List<BodyDeclaration> bodyDeclarations = typeDeclaration.getMembers();
                for (BodyDeclaration bodyDeclaration : bodyDeclarations) {
                    if (!bodyDeclaration.isMethodDeclaration())
                        continue;
                    MethodDeclaration methodDeclaration = (MethodDeclaration) bodyDeclaration;
                    pathMethodsList.add(methodDeclaration);
                }
            }
        }

        List<MethodDependency> methodDependencies = new LinkedList<>();
        //for every method from root folder extract every called method and add to dependencies list
        pathMethodsList.forEach(method -> {
            MethodDependency methodDep = new MethodDependency(method.getNameAsString());

            method.walk(MethodCallExpr.class, methodInside -> {
                methodDep.addDependency(methodInside.getNameAsString());
            });
            methodDependencies.add(methodDep);
        });

        return methodDependencies;
    }
}