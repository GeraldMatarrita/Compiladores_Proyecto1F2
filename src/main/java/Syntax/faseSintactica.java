package Syntax;

import Auxiliar.Pair;
import Auxiliar.SyntaxException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * This class provides methods for parsing a list of tokens and detecting syntax errors.
 */
public class faseSintactica {

    // Parsing table
    private static final String[][] parsingTable = {
        //     (      )      *      +      -      /      ;      =      i      n      $      E      P     T
            {"s1",  null,  null,  null,  null,  null,  null,  null,  "s5",  "s6",  null,  "2",   "3",   "4"},
            {"s1",  null,  null,  null,  null,  null,  null,  null,  "s5",  "s6",  null,  "7",   null,  "4"},
            {null,  null,  null,  "s8",  "s9",  null,  "s10", null,  null,  null,  null,  null,  null,  null},
            {null,  null,  null,  null,  null,  null,  "s11", null,  null,  null,  "acc", null,  null,  null},
            {null,  "r3",  "s12", "s13", "s14", "s15", "r3",  null,  null,  null,  null,  null,  null,  null},
            {null,  "r9",  "r9",  "r9",  "r9",  "r9",  "r9",  "s16", null,  null,  null,  null,  null,  null},
            {null,  "r10", "r10", "r10", "r10", "r10", "r10", null,  null,  null,  null,  null,  null,  null},
            {null,  "s17", null,  "s8",  "s9",  null,  null,  null,  null,  null,  null,  null,  null,  null},
            {"s1",  null,  null,  null,  null,  null,  null,  null,  "s5",  "s6",  null,  "18",  null,  "4"},
            {"s1",  null,  null,  null,  null,  null,  null,  null,  "s5",  "s6",  null,  "19",  null,  "4"},
            {null,  null,  null,  null,  null,  null,  "r1",  null,  null,  null,  "r1",  null,  null,  null},
            {null,  null,  null,  null,  null,  null,  "r2",  null,  null,  null,  "r2",  null,  null,  null},
            {"s1",  null,  null,  null,  null,  null,  null,  null,  "s21", "s6",  null,  null,  "20",  "20"},
            {"s1",  null,  null,  null,  null,  null,  null,  null,  "s21", "s6",  null,  null,  "22",  "22"},
            {"s1",  null,  null,  null,  null,  null,  null,  null,  "s21", "s6",  null,  null,  "23",  "23"},
            {"s1",  null,  null,  null,  null,  null,  null,  null,  "s21", "s6",  null,  null,  "24",  "24"},
            {"s1",  null,  null,  null,  null,  null,  null,  null,  "s5",  "s6",  null,  "25",  null,  "4"},
            {null,  "r11", "r11", "r11", "r11", "r11", "r11", null,  null,  null,  null,  null,  null,  null},
            {null,  "r4",  null,  "s8",  "s9",  null,  "r4",  null,  null,  null,  null,  null,  null,  null},
            {null,  "r5",  null,  "s8",  "s9",  null,  "r5",  null,  null,  null,  null,  null,  null,  null},
            {null,  "r12", "s12", "r12", "r12", "s15", "r12", null,  null,  null,  null,  null,  null,  null},
            {null,  "r9",  "r9",  "r9",  "r9",  "r9",  "r9",  null,  null,  null,  null,  null,  null,  null},
            {null,  "r6",  "s12", "r6",  "r6",  "s15", "r6",  null,  null,  null,  null,  null,  null,  null},
            {null,  "r7",  "s12", "r7",  "r7",  "s15", "r7",  null,  null,  null,  null,  null,  null,  null},
            {null,  "r13", "s12", "r13", "r13", "s15", "r13", null,  null,  null,  null,  null,  null,  null},
            {null,  "r8",  null,  "s8",  "s9",  null,  "r8",  null,  null,  null,  null,  null,  null,  null},
    };

    private static final Stack<String> stack = new Stack<>(); // Stores the states and symbols


    /**
     * Parses the given input tokens.
     *
     * @param inputToken The input tokens to parse.
     * @throws SyntaxException If a syntax error is found.
     */
    public static void parse(ArrayList<String> inputToken) throws SyntaxException {

        // Stores the input tokens of the current line
        int tokenIndex = 0; // Index for iterating through the input tokens
        stack.push("0"); // Push the initial state to the stack

        // Stop only when there is an error or the input is accepted
        while (true) {
            String currentState = stack.peek();  // Get the current state
            String currentToken = inputToken.get(tokenIndex);  // Get the current token

            // Get the action from the parsing table
            String action = parsingTable[Integer.parseInt(currentState)][getTokenIndex(currentToken)];

            // There is no action for the current state and token. It's a syntax error.
            if (action == null) {
                clearStack();
                throw new SyntaxException();
            }

            if (action.equals("acc")) {
                // Accept action
                break;
            } else if (action.charAt(0) == 's') {
                // Shift action
                // Get the next state
                int nextState = Integer.parseInt(action.substring(1));
                // Push the token and the next state to the stack
                stack.push(currentToken);
                stack.push(String.valueOf(nextState));
                tokenIndex++;
            } else if (action.charAt(0) == 'r') {
                // Reduce action
                // Get the production rule
                int productionRule = Integer.parseInt(action.substring(1));
                // Get the length of the production rule
                int reduceLength = getProductionLength(productionRule);

                // Pop the symbols and states from the stack based on the length of the production rule
                if (reduceLength > 0) {
                    for (int i = 0; i < 2 * reduceLength; i++) {
                        stack.pop();
                    }
                }

                // Get the reduced symbol
                String reducedSymbol = getReducedSymbol(productionRule);
                // Get the new state from the parsing table
                String newState = parsingTable[Integer.parseInt(stack.peek())][getNonTerminalIndex(reducedSymbol)];

                // Push the reduced symbol and the new state to the stack
                stack.push(reducedSymbol);
                stack.push(newState);
            }
        }
        clearStack();
    }

    /**
     * Gets the index of the given token in the parsing table.
     *
     * @param token The token to get the index of.
     * @return The index of the token in the parsing table.
     */
    private static int getTokenIndex(String token) {
        // Define the mapping from token to column index in the parsing table
        /*String[] tokens = {"PARENTESIS_IZQ", "PARENTESIS_DER", "MULTIPLICACION", "SUMA", "RESTA", "DIVISION",
                "PUNTO_COMA", "ASIGNACION", "IDENTIFICADOR", "NUMERO", "$", "E", "P", "T"};

        // Iterate through the tokens to find the index of the given token
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals(token)) {
                return i;
            }
        }
        return -1; // Token not found*/

        switch (token) {
            case "PARENTESIS_IZQ" -> {
                return 0;
            }
            case "PARENTESIS_DER" -> {
                return 1;
            }
            case "MULTIPLICACION" -> {
                return 2;
            }
            case "SUMA" -> {
                return 3;
            }
            case "RESTA" -> {
                return 4;
            }
            case "DIVISION" -> {
                return 5;
            }
            case "PUTNO_COMA" -> {
                return 6;
            }
            case "ASIGNACION" -> {
                return 7;
            }
            case "IDENTIFICADOR" -> {
                return 8;
            }
            case "NUMERO" -> {
                return 9;
            }
            case "$" -> {
                return 10;
            }
            case "E" -> {
                return 11;
            }
            case "P" -> {
                return 12;
            }
            case "T" -> {
                return 13;
            }
            // If the token is not an operator
            default -> {
                return -1;
            }
        }


    }

    /**
     * Gets the index of the given non-terminal symbol in the parsing table.
     *
     * @param symbol The non-terminal symbol to get the index of.
     * @return The index of the non-terminal symbol in the parsing table.
     */
    private static int getNonTerminalIndex(String symbol) {
        // Defines the mapping from non-terminal symbol to column index in the parsing table
        String[] nonTerminals = {"E", "P", "T"};

        // Iterate through the non-terminal symbols to find the index of the given symbol
        for (int i = 0; i < nonTerminals.length; i++) {
            if (nonTerminals[i].equals(symbol)) {
                return i + 11; // Shift the index by 11 to match the table layout
            }
        }
        return -1; // Symbol not found
    }

    /**
     * Gets the length of the given production rule.
     *
     * @param productionRule The production rule to get the length of.
     * @return The length of the production rule.
     */
    private static int getProductionLength(int productionRule) {
        // Defines the length of each production rule
        int[] productionLengths = {0, 2, 2, 1, 3, 3, 3, 3, 3, 1, 1, 3, 3, 3};

        // Get the length of the production rule
        if (productionRule >= 0 && productionRule < productionLengths.length) {
            return productionLengths[productionRule];
        }
        return 0; // Invalid rule
    }

    /**
     * Gets the reduced symbol of the given production rule.
     *
     * @param productionRule The production rule to get the reduced symbol of.
     * @return The reduced symbol of the production rule.
     */
    private static String getReducedSymbol(int productionRule) {
        // Defines the left-hand side symbol of each production rule
        String[] reducedSymbols = {"P'", "P", "P", "E", "E", "E", "E", "E", "E", "T", "T", "T", "T", "T"};

        // Get the reduced symbol
        if (productionRule >= 0 && productionRule < reducedSymbols.length) {
            return reducedSymbols[productionRule];
        }
        return null; // Invalid rule
    }

    /**
     * Clears the stack and pushes the initial state.
     */
    private static void clearStack() {
        stack.clear();
        stack.push("0");
    }

    /**
     * Parses the given tokens.
     *
     * @param tokens The tokens to parse.
     * @return (True, Line number) if there is a syntax error, (False, Line number) otherwise.
     */
    public static Pair<Boolean, Integer> parseTokens(Map<String, Pair<String, String>> tokens) {

        ArrayList<String> inputTokens = new ArrayList<>();  // Stores the input tokens
        boolean thereIsError = false;  // Indicates if there is a syntax error
        int lineNumber = 1;  // Stores the line number of the syntax error

        // Iterate through the tokens
        for (Map.Entry<String, Pair<String, String>> entry : tokens.entrySet()) {
            try {
                if (entry.getKey().contains("lb")) {
                    // If the token is a line break, parse the input tokens
                    // Add the last token of the line to the input tokens
                    inputTokens.add(entry.getValue().getKey());
                    // Add the end of line token to the input tokens
                    inputTokens.add("$");

                    // Parse the input tokens
                    parse(inputTokens);

                    // Clear the input tokens and increase the line number
                    inputTokens.clear();
                    lineNumber++;
                } else {
                    // If the token is not a line break, add it to the input tokens
                    inputTokens.add(entry.getValue().getKey());
                }
            } catch (SyntaxException e) {
                thereIsError = true;
                break;
            }
        }
        return new Pair<>(thereIsError, lineNumber);
    }
}