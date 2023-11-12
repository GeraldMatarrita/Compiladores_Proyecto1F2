package Semantic;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    private final String value;
    private final List<ASTNode> children;

    public ASTNode(String valor) {
        this.value = valor;
        this.children = new ArrayList<>();
    }

    public void addChildren(ASTNode ASTNode) {
        children.add(ASTNode);
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public String getValue() {
        return value;
    }
}
