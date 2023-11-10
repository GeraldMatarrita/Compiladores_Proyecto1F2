package Semantic.TreeAST;

import java.util.Map;

public class Substraction implements ASTnode{
    ASTnode term1;
    ASTnode term2;

    Integer substraction;
    @Override
    public Object excecute(Map<String, Integer> symbolTable) {
        substraction = Integer.parseInt(term1.excecute(symbolTable).toString())-Integer.parseInt(term2.excecute(symbolTable).toString());
        return null;
    }
}
