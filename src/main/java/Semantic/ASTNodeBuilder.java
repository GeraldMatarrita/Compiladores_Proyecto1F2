package Semantic;

import java.util.ArrayList;

/**
 * This class represents a node of the AST
 */
public class ASTNodeBuilder {

    /**
     * Constructor for the node
     * @param rule The rule of the node
     * @param children The children of the node
     * @return ASTNode The node
     */
    public static ASTNode builderAST(String rule, ArrayList<ASTNode> children) {
        ASTNode ASTNode = new ASTNode(rule);
        for (ASTNode child : children) {
            ASTNode.addChildren(child);
        }
        return ASTNode;
    }

    /**
     * Constructor for the expression node
     * @param children The children to be added
     * @return The node
     */
    public static ASTNode buildExpression(ArrayList<ASTNode> children) {
        return builderAST("E", children);
    }

    /**
     * Constructor for the term node
     * @param children The children to be added
     * @return The node
     */
    public static ASTNode buildTerm(ArrayList<ASTNode> children) {
        return builderAST("T", children);
    }

    /**
     * Constructor for the program node
     * @param children The children to be added
     * @return The node
     */
    public static ASTNode buildProgram(ArrayList<ASTNode> children) {
        return builderAST("P", children);
    }

    /**
     * Constructor for the terminal node
     * @param value The value of the node
     * @return The node
     */
    public static ASTNode buildTerminal(String value) {
        return new ASTNode(value);
    }

    /**
     * Constructor for the identifier node
     * @param value The value of the node
     * @return The node
     */
    public static ASTNode buildIdentifier(String value) {
        return new IdenfierASTNode(value);
    }

    /**
     * Constructor for the reduced node
     * @param reducedSymbol The reduced symbol
     * @param childrenASTNodes The children to be added
     * @return The node
     */
    public static ASTNode buildReducedNode(String reducedSymbol, ArrayList<ASTNode> childrenASTNodes) {
        return switch (reducedSymbol) {
            case "P" -> buildProgram(childrenASTNodes);
            case "E" -> buildExpression(childrenASTNodes);
            case "T" -> buildTerm(childrenASTNodes);
            default -> null;
        };
    }
}
