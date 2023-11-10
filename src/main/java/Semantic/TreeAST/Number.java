package Semantic.TreeAST;

import java.util.Map;

public class Number implements ASTnode{

    Integer value;
    @Override
    public Object excecute(Map<String, Integer> symbolTable) {
        return value;
    }
}
