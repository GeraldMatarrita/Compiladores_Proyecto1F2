package Semantic;

// This class represents an identifier node in the AST
public class IdenfierASTNode extends ASTNode{

    // The name of the identifier
    private final String name;

    /**
     * Constructor for the identifier node
     * @param name The name of the identifier
     */
    public IdenfierASTNode(String name) {
        super("IDENTIFICADOR");
        this.name = name;
    }

    /**
     * Getter for the name of the identifier
     * @return The name of the identifier
     */
    public String getName() {
        return name;
    }
}
