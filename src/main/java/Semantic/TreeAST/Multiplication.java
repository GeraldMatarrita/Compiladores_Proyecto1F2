package Semantic.TreeAST;

import java.util.Map;

public class Multiplication implements ASTnode{

    ASTnode term1;
    ASTnode term2;

    Integer multiplication;
    @Override
    public Object excecute(Map<String, Integer> symbolTable) {
        multiplication = Integer.parseInt(term1.excecute(symbolTable).toString())*Integer.parseInt(term2.excecute(symbolTable).toString());
        return null;
    }
}
