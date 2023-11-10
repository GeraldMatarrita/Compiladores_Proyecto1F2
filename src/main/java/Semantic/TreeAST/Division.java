package Semantic.TreeAST;

import java.util.Map;

public class Division implements ASTnode{
    ASTnode term1;
    ASTnode term2;

    Integer division;
    @Override
    public Object excecute(Map<String, Integer> symbolTable) {
        division = Integer.parseInt(term1.excecute(symbolTable).toString())/Integer.parseInt(term2.excecute(symbolTable).toString());
        return null;
    }
}
