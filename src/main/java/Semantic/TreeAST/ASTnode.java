package Semantic.TreeAST;

import java.util.Map;

public interface ASTnode {
    public Object excecute(Map<String, Integer> symbolTable);
}
