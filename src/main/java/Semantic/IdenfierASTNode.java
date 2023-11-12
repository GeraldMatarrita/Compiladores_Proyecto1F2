package Semantic;

public class IdenfierASTNode extends ASTNode{

    private final String name;

    public IdenfierASTNode(String name) {
        super("IDENTIFICADOR");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
