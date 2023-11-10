package Semantic.TreeAST;

import java.util.Map;

public class Addition implements ASTnode{
    ASTnode term1;
    ASTnode term2;

    Integer addition;
    public Addition(ASTnode term1, ASTnode term2) {
        this.term1 = term1;
        this.term2 = term2;
    }

    @Override
    public Object excecute(Map<String, Integer> symbolTable) {
        addition = Integer.parseInt(term1.excecute(symbolTable).toString())+Integer.parseInt(term2.excecute(symbolTable).toString());
        return null;
    }
}
