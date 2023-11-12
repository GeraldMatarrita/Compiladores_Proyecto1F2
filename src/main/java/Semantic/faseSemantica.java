package Semantic;

import Auxiliar.SemanticException;

import java.util.HashMap;
import java.util.Map;

public class faseSemantica {

    private static final Map<Integer, String> symbolTable = new HashMap<>();
    private static int lineNumber = 0;

    private static Integer count = 1;

    public static void analyzeSemantics(ASTNode root) throws SemanticException{
        analyzeNode(root);
    }

    public static void analyzeNode(ASTNode node) throws SemanticException{
        if (node == null) return;

        switch (node.getValue()) {
            case "E" -> {
                if (node.getChildren().get(0).getValue().equals("IDENTIFICADOR")){
                    IdenfierASTNode identifier = (IdenfierASTNode) node.getChildren().get(0);
                    symbolTable.put(count++, identifier.getName());
                }
                for (ASTNode child : node.getChildren()) {
                    analyzeNode(child);
                }
            }
            case "P","T" -> {
                if (node.getValue().equals("P")){
                    lineNumber++;
                }
                for (ASTNode child : node.getChildren()) {
                    analyzeNode(child);
                }
            }
            case "IDENTIFICADOR" -> {
                IdenfierASTNode identifier = (IdenfierASTNode) node;
                if (!symbolTable.containsValue(identifier.getName())){
                    throw new SemanticException(identifier.getName());
                }
            }
        }
    }

    public static Map<Integer, String> getSymbolTable() {
        return symbolTable;
    }

    public static int getLineNumber() {
        return lineNumber;
    }
}
