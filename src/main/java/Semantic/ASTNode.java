package Semantic;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    String value;
    List<ASTNode> children;

    public ASTNode(String valor) {
        this.value = valor;
        this.children = new ArrayList<>();
    }

    public void addChildren(ASTNode ASTNode) {
        children.add(ASTNode);
    }
}
