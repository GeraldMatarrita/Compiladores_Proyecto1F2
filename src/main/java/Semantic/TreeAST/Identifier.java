package Semantic.TreeAST;

import java.util.Map;

public class Identifier implements ASTnode{
    String name;


    public Identifier(String name) {
        this.name = name;
    }
    @Override
    public Object excecute(Map<String, Integer> symbolTable) {
        return symbolTable.get(this.name);
    }
}
