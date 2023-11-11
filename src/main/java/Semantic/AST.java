package Semantic;

public class AST {

    private ASTNode root;

    public AST() {
        this.root = null;
    }

    public ASTNode getRoot() {
        return root;
    }

    public void setRoot(ASTNode root) {
        this.root = root;
    }
}
