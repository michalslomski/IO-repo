package storythree;

/**
 * Author: Marcin Plywacz
 * Date: 07.11.2019
 */

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MethodBodyFinder {
    private Map<String, String> map;

    /**
     * Method maps all method names with it's bodies in java class source code.
     * @param javaFilePath path to java source code to be scanned
     * @return map with method names as keys and method bodies as values
     * @throws IOException
     */
    /*
     * Pobiera ścieżkę pliku źródłowego javy i zwraca mapę w której są nazwy metod jako klucze
     * i ciała metod jako wartośći przykład na zasadzie tego pliku:
     * getMethodsBodies -> {ciało tej metody}
     * visit -> {ciało tej metody} */
    public Map<String, String> getMethodsBodies(String javaFilePath) throws IOException {
        map = new HashMap<>();
        CompilationUnit cu = null;

        try (FileInputStream in = new FileInputStream(javaFilePath)) {
           // cu = JavaParser.parse(in); zakomentowałem to bo nie kompilował mi się program :((
        }
        MethodVisitor mv = new MethodVisitor();
        mv.visit(cu, null);

        return map;
    }

    private class MethodVisitor extends VoidVisitorAdapter {
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            String methodName = n.getName().toString();
            String methodBody = n.getBody().toString();

            map.put(methodName, methodBody);
        }
    }

}