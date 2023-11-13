package Semantic;

import Auxiliar.SemanticException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the semantic phase, where the semantics of the AST are analyzed
 */
public class faseSemantica {

    private static final Map<Integer, String> symbolTable = new HashMap<>(); // Symbol table
    private static int lineNumber = 0; // Line number

    private static Integer count = 1; // Count for the symbol table

    /**
     * Analyzes the semantics of the AST
     * @param root The root of the AST
     * @throws SemanticException
     */
    public static void analyzeSemantics(ASTNode root) throws SemanticException{
        analyzeNode(root);
    }

    /**
     * Analyzes the semantics of a node
     * @param node The node to be analyzed
     * @throws SemanticException
     */
    public static void analyzeNode(ASTNode node) throws SemanticException{

        // If the node is null, return
        if (node == null) return;

        // Switch for the value of the node
        switch (node.getValue()) {

            // If the node is an expression
            case "E" -> {
                // If the node is an identifier
                if (node.getChildren().get(0).getValue().equals("IDENTIFICADOR")){
                    // Add the identifier to the symbol table
                    IdentifierASTNode identifier = (IdentifierASTNode) node.getChildren().get(0);
                    symbolTable.put(count++, identifier.getName());
                }

                // Analyze the children of the node
                for (ASTNode child : node.getChildren()) {
                    analyzeNode(child);
                }
            }

            // If the node is a term or a program
            case "P","T" -> {
                // If the node is a program, increment the line number
                if (node.getValue().equals("P")){
                    lineNumber++;
                }

                // Analyze the children of the node
                for (ASTNode child : node.getChildren()) {
                    analyzeNode(child);
                }
            }

            // If the node is an identifier
            case "IDENTIFICADOR" -> {
                // If the identifier is not in the symbol table, throw an exception
                IdentifierASTNode identifier = (IdentifierASTNode) node;
                if (!symbolTable.containsValue(identifier.getName())){
                    throw new SemanticException(identifier.getName());
                }
            }
        }
    }

    /**
     * Getter for the symbol table
     * @return The symbol table
     */
    public static Map<Integer, String> getSymbolTable() {
        return symbolTable;
    }

    /**
     * Getter for the line number
     * @return The line number
     */
    public static int getLineNumber() {
        return lineNumber;
    }
}
