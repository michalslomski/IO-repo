package storyseven;





import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import storythree.DependencyObject;
import storythree.FilesForStoryThree;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CyclomaticComplexityFinder {


    private CyclomaticComplexityCalculator complexityCalculator = new CyclomaticComplexityCalculator();
    private List<DependencyObject> listOfDependencyObjects;
    private List<Integer> ccList = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private  HashMap<List<String>,List<Integer>> map;

    public HashMap<List<String>, List<Integer>> getMap() {
        return map;
    }

    public void setMap(HashMap<List<String>, List<Integer>> map) {
        this.map = map;
    }

    public List<DependencyObject> find(List<DependencyObject> list) throws IOException {
        listOfDependencyObjects=list;
        FilesForStoryThree files = new FilesForStoryThree();
        List<File> listOfFiles = files.getList();

        for(File file:listOfFiles) {
            CompilationUnit cu = null;
            try (FileInputStream in = new FileInputStream(file)) {
                cu = StaticJavaParser.parse(in);
            }

            Calculate calculate = new Calculate();

            calculate.visit(cu,null);

        }

        int i=0;
        for(DependencyObject object: listOfDependencyObjects){

            if(!object.getMethodName().equals("")){
                object.setCyclomaticComplexity(ccList.get(i));
                i++;
            }


        }

        return listOfDependencyObjects;
    }



    private class Calculate extends VoidVisitorAdapter {
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            ccList.add(complexityCalculator.calculateCyclomaticComplexity(n));

        }
    }

}
