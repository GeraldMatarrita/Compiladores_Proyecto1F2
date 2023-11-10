package Semantic.TreeAST;

import java.util.Map;

public class Expression implements ASTnode{

    ASTnode operation;

    public Expression() {

    }

    public ASTnode getOperation() {
        return operation;
    }

    public void setOperation(ASTnode operation) {
        this.operation = operation;
    }

    @Override
    public Object excecute(Map<String, Integer> symbolTable) {
        return operation.excecute(symbolTable);
    }
}
