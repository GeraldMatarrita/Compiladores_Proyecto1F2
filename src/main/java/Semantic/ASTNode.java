package Semantic;

import java.util.ArrayList;
import java.util.List;
/**
 * This class represents a node of the AST
 */
public class ASTNode {
    private final String value; // Value of the node
    private final List<ASTNode> children; // Children of the node

    /**
     * Constructor for the node
     * @param valor
     */
    public ASTNode(String valor) {
        this.value = valor;
        this.children = new ArrayList<>();
    }

    /**
     * Adds a child to the node
     * @param ASTNode The child to be added
     */
    public void addChildren(ASTNode ASTNode) {
        children.add(ASTNode);
    }

    /**
     * Getter for the children of the node
     * @return The children of the node
     */
    public List<ASTNode> getChildren() {
        return children;
    }

    /**
     * Getter for the value of the node
     * @return The value of the node
     */
    public String getValue() {
        return value;
    }
}
