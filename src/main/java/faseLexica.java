import java.util.*;

/**
 * This class provides methods for tokenizing a text line and detecting lexical errors.
 */
public class faseLexica {

    // Token types
    public static final String TOKEN_ASSIGN = "ASIGNACION";
    public static final String TOKEN_IDENTIFIER = "IDENTIFICADOR";
    public static final String TOKEN_INTEGER = "NUMERO";
    public static final String TOKEN_PLUS = "SUMA";
    public static final String TOKEN_MINUS = "RESTA";
    public static final String TOKEN_MULTIPLY = "MULTIPLICACION";
    public static final String TOKEN_DIVIDE = "DIVISION";
    public static final String TOKEN_DOTPOINT = "PUNTO_COMA";
    public static final String TOKEN_PARENTHESE_L = "PARENTESIS_IZQ";
    public static final String TOKEN_PARENTHESE_R = "PARENTESIS_DER";

    // Value for index of tokens
    private static int key = 0;

    // Stores the current line number.
    private static int currentLine = 1;

    //Indicates if the token is a number.
    private static boolean isNumber = false;

    // Indicates if a lexical error has occurred.
    private static boolean lexError = false;

    // Stores detected lexical errors.
    private static Map<Integer, String> errors = new HashMap<>();

    /**
     * Tokenizes a text line and returns a map containing the found tokens.
     *
     * @param line The text line to tokenize.
     * @return A map containing the found tokens along with their types.
     */
    public static Map<Integer, Pair<String, String>> tokenize(String line) {
        Map<Integer, Pair<String, String>> tokens = new HashMap<>(); // Stores the found tokens.
        key = 0; // Reset the key for the token map.

        String code = ""; // Stores the code of the current token.
        int pos = 0; // Indicates the current position in the line.
        int i = 0; // Index for iterating through the code of the current token.

        while (pos < line.length()) {
            char currentChar = line.charAt(pos);

            if (Character.isDigit(currentChar)) {
                // If the current character is a digit, it's a number.
                code = code.concat(String.valueOf(currentChar));
                i = 0;
                while (i < code.length() && Character.isDigit(currentChar)) {
                    currentChar = code.charAt(i);
                    i++;
                }
                if (Character.isLetter(currentChar)) {
                    // If the next character is a letter, it's a lexical error.
                    lexError = true;
                }
                pos++;
                isNumber = true;
                continue;
            }
            if (Character.isLetter(currentChar)) {
                // If the current character is a letter, it's an identifier.
                code = code + currentChar;

                i = 0;
                while (i < code.length() && Character.isLetter(currentChar)) {
                    currentChar = code.charAt(i);
                    i++;
                }
                if (Character.isDigit(currentChar)) {
                    // If the next character is a digit, it's a lexical error.
                    lexError = true;
                }
                pos++;
                continue;
            }

            // Handle whitespace and line breaks
            if (Character.isWhitespace(currentChar) || currentChar == '\n' || currentChar == '\r') {
                if (currentChar == '\n')
                    // If the current character is a line break, increase the line number.
                    currentLine++;

                // If the current token is not empty, check the code.
                checkToken(code, tokens);

                // Reset the code and continue with the next character.
                pos++;
                code = "";
                continue;
            } else {
                // If the current character is not a digit, letter, whitespace or line break, it's an operator.
                code = code.concat(String.valueOf(currentChar));
            }

            if (pos == line.length()-1){
                checkToken(String.valueOf(currentChar), tokens);
            }

            pos++;
        }
        return tokens;
    }

    /**
     * Checks the token type and stores it in the token map.
     *
     * @param code   The code of the token.
     * @param tokens The token map.
     */
    private static void checkToken(String code, Map<Integer, Pair<String, String>> tokens) {

        // It will store the token type and the token code.
        Pair<String, String> pair = new Pair<>();

        // Handle operators
        switch (code) {
            case "+" -> pair = new Pair<>(TOKEN_PLUS, "+");
            case "-" -> pair = new Pair<>(TOKEN_MINUS, "-");
            case "*" -> pair = new Pair<>(TOKEN_MULTIPLY, "*");
            case "/" -> pair = new Pair<>(TOKEN_DIVIDE, "/");
            case "(" -> pair = new Pair<>(TOKEN_PARENTHESE_L, "(");
            case ")" -> pair = new Pair<>(TOKEN_PARENTHESE_R, ")");
            case ";" -> pair = new Pair<>(TOKEN_DOTPOINT, ";");
            case "=" -> pair = new Pair<>(TOKEN_ASSIGN, "=");
            // If the token is not an operator, check if it's a number or an identifier.
            default -> {
                if (lexError) {
                    // If a lexical error has occurred, store the error.
                    errors.put(currentLine, code);
                } else if (isNumber) {
                    pair = new Pair<>(TOKEN_INTEGER, code);
                    isNumber = false;
                } else {
                    pair = new Pair<>(TOKEN_IDENTIFIER, code);
                }
            }
        }

        if (!lexError) {
            // If no lexical error has occurred, store the token.
            verifyPair(tokens, pair);
        } else {
            // Reset the lexical error flag.
            lexError = false;
        }
    }

    /**
     * Verifies if a (token, type) pair already exists in the token map.
     *
     * @param tokens The token map.
     * @param pair   The (token, type) pair to verify.
     */
    private static void verifyPair(Map<Integer, Pair<String, String>> tokens, Pair<String, String> pair) {
        for (Map.Entry<Integer, Pair<String, String>> entry : tokens.entrySet()) {
            if (entry.getValue().equals(pair)) {
                // If the pair already exists, get the key and break the loop.
                pair = entry.getValue();
                break;
            }
        }
        // Store the pair in the token map.
        tokens.put(key++, pair);
    }

    /**
     * Gets the lexical errors detected during the tokenization process.
     *
     * @return A map containing the lexical errors along with the line numbers where they occurred.
     */
    public static Map<Integer, String> getErrors() {
        return errors;
    }

    public static void updateCurrentLine(){
        currentLine++;
    }

}
