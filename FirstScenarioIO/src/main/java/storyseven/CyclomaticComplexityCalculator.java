package storyseven;


import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;

/**
 * Author: Michal Slomski
 * Date: 09.11.2019
 */

import java.util.List;

public class CyclomaticComplexityCalculator {

    private int E;
    private int N;
    private int P;
    private MethodDeclaration method;

    public CyclomaticComplexityCalculator() {

    }

    public int calculateCyclomaticComplexity(MethodDeclaration methodDeclaration){
        this.method = methodDeclaration;
        this.P = 1;
        this.E=0;
        this.N=0;
        calculateNumberOfIfs();
        calculateNumberOfForLoops();
        calculateNumberOfWhileLoops();
        calculateNumberOfSwitches();
        calculateNumberOfReturns();
        calculateNumberOfForeach();
        return E-N+P;
    }

    private void calculateNumberOfIfs() {
        List<IfStmt> ifStmts = method.getChildNodesByType(IfStmt.class);
        E += ifStmts.size() * 2;
        N += ifStmts.size();
    }

    private void calculateNumberOfForeach() {
        List<ForEachStmt> foreachStmts = method.getChildNodesByType(ForEachStmt.class);
        E += foreachStmts.size() * 2;
        N += foreachStmts.size();
    }
    private void calculateNumberOfForLoops() {

        List<ForStmt> forStmts = method.getChildNodesByType(ForStmt.class);
        E += forStmts.size() * 2;
        N += forStmts.size();
    }

    private void calculateNumberOfWhileLoops() {

        List<WhileStmt> whileStmts = method.getChildNodesByType(WhileStmt.class);
        E += whileStmts.size() * 2;
        N += whileStmts.size();
    }

    private void calculateNumberOfSwitches() {

        List<SwitchStmt> switchStmts = method.getChildNodesByType(SwitchStmt.class);
        for (SwitchStmt switchStmt : switchStmts) {
            NodeList<SwitchEntry> cases = switchStmt.getEntries();
            E += cases.size();
            N ++;
        }
    }

    private void calculateNumberOfReturns() {

        List<ReturnStmt> returnStmts = method.getChildNodesByType(ReturnStmt.class);
        P += returnStmts.size();
        N += returnStmts.size();
    }


}
