package Semantic.TreeAST;

import java.util.Map;

public class Assignment implements ASTnode{

    ASTnode identifier;
    ASTnode expresion;

    Integer value;
    public Assignment(ASTnode identifier, ASTnode expresion) {
        this.identifier = identifier;
        this.expresion = expresion;
    }

    @Override
    public Object excecute(Map<String, Integer> symbolTable) {

        value = Integer.parseInt(expresion.excecute(symbolTable).toString());
        symbolTable.put(identifier.toString(),value);
        return null;
    }
}
