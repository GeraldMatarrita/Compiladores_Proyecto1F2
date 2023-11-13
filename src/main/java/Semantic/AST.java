package Semantic;

/**
 * This class represents the Abstract Syntax Tree
 */
public class AST {

    private ASTNode root; // Root of the tree

    /**
     * Constructor for the AST
     */
    public AST() {
        this.root = null;
    }

    /**
     * Getter for the root of the tree
     * @return The root of the tree
     */
    public ASTNode getRoot() {
        return root;
    }

    /**
     * Setter for the root of the tree
     * @param root The root of the tree
     */
    public void setRoot(ASTNode root) {
        this.root = root;
    }
}
