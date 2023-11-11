package Semantic;

import java.util.ArrayList;
import java.util.List;

public class ASTNodeBuilder {
    public static ASTNode builderAST(String rule, ArrayList<ASTNode> children) {
        ASTNode ASTNode = new ASTNode(rule);
        for (ASTNode child : children) {
            ASTNode.addChildren(child);
        }
        return ASTNode;
    }

    public static ASTNode buildExpression(ArrayList<ASTNode> children) {
        return builderAST("E", children);
    }

    public static ASTNode buildTerm(ArrayList<ASTNode> children) {
        return builderAST("T", children);
    }

    public static ASTNode buildProgram(ArrayList<ASTNode> children) {
        return builderAST("P", children);
    }

    public static ASTNode buildTerminal(String value) {
        return new ASTNode(value);
    }

    public static ASTNode buildReducedNode(String reducedSymbol, ArrayList<ASTNode> childrenASTNodes) {
        return switch (reducedSymbol) {
            case "P" -> buildProgram(childrenASTNodes);
            case "E" -> buildExpression(childrenASTNodes);
            case "T" -> buildTerm(childrenASTNodes);
            default -> null;
        };
    }
}
