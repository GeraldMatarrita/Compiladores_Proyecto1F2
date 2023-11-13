package Auxiliar;

/*
    * Exception to be thrown when a semantic error is found
 */
public class SemanticException extends Exception{

    /**
        * Constructor
        * @param message the message to be shown
     **/
    public SemanticException(String message) {
        super(message);
    }

    /**
     * Returns the message of the exception
     * @return message to be shown
     */
    @Override
    public String toString() {
        return getMessage();
    }
}
