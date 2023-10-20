import java.util.Stack;

public class SLRParser {
    private String[][] parsingTable = {
            //   "(", ")", "*", "+", "-", "/", ";", "=", "i", "n", "$", "E", "P", "T",
            {"s1", null, null, null, null, null, null, null, "s5", "s6", null, "2", "3", "4"},
            {"s1", null, null, null, null, null, null, null, "s5", "s6", null, "7", null, "4"},
            {null, null, null, "s8", "s9", null, "s10", null, null, null, null, null, null, null},
            {null, null, null, null, null, null, "s11", null, null, null, "acc", null, null, null},
            {null, "r3", "s12", "s13", "s14", "s15", "r3", null, null, null, null, null, null, null},
            {null, "r9", "r9", "r9", "r9", "r9", "r9", "s16", null, null, null, null, null, null},
            {null, "r10", "r10", "r10", "r10", "r10", "r10", null, null, null, null, null, null, null},
            {null, "s17", null, "s8", "s9", null, null, null, null, null, null, null, null, null},
            {"s1", null, null, null, null, null, null, null, "s5", "s6", null, "18", null, "4"},
            {"s1", null, null, null, null, null, null, null, "s5", "s6", null, "19", null, "4"},
            {null, null, null, null, null, null, "r1", null, null, null, "r1", null, null, null},
            {null, null, null, null, null, null, "r2", null, null, null, "r2", null, null, null},
            {"s1", null, null, null, null, null, null, null, "s21", "s6", null, null, "20", null},
            {"s1", null, null, null, null, null, null, null, "s21", "s6", null, null, "22", null},
            {"s1", null, null, null, null, null, null, null, "s21", "s6", null, null, "23", null},
            {"s1", null, null, null, null, null, null, null, "s21", "s6", null, null, "24", null},
            {"s1", null, null, null, null, null, null, null, "s5", "s6", null, "25", null, "4"},
            {null, "r11", "r11", "r11", "r11", "r11", "r11", null, null, null, null, null, null, null},
            {null, "r4", null, "s8", "s9", null, "r4", null, null, null, null, null, null, null},
            {null, "r5", null, "s8", "s9", null, "r5", null, null, null, null, null, null, null},
            {null, "r12", "s12", "r12", "r12", "s15", "r12", null, null, null, null, null, null, null},
            {null, "r9", "r9", "r9", "r9", "r9", "r9", null, null, null, null, null, null, null},
            {null, "r6", "s12", "r6", "r6", "s15", "r6", null, null, null, null, null, null, null},
            {null, "r7", "s12", "r7", "r7", "s15", "r7", null, null, null, null, null, null, null},
            {null, "r13", "s12", "r13", "r13", "s15", "r13", null, null, null, null, null, null, null},
            {null, "r8", null, "s8", "s9", null, "r8", null, null, null, null, null, null, null},
    };

    private String[] inputTokens;
    private Stack<String> stack;

    public SLRParser(String[] inputTokens) {
        this.inputTokens = inputTokens;
        this.stack = new Stack<>();
        stack.push("0");
    }

    public void parse() {
        int tokenIndex = 0;

        while (true) {
            String currentState = stack.peek();
            String currentToken = inputTokens[tokenIndex];

            String action = parsingTable[Integer.parseInt(currentState)][getTokenIndex(currentToken)];

            if (action == null) {
                System.out.println("Error: No action found for state " + currentState + " and token " + currentToken);
                break;
            }

            if (action.equals("acc")) {
                System.out.println("Input is valid.");
                break;
            } else if (action.charAt(0) == 's') {
                // Shift action
                int nextState = Integer.parseInt(action.substring(1));
                stack.push(currentToken);
                stack.push(String.valueOf(nextState));
                tokenIndex++;
            } else if (action.charAt(0) == 'r') {
                // Reduce action
                int productionRule = Integer.parseInt(action.substring(1));
                int reduceLength = getProductionLength(productionRule);
                if (reduceLength > 0) {
                    for (int i = 0; i < 2 * reduceLength; i++) {
                        stack.pop();
                    }
                }
                String reducedSymbol = getReducedSymbol(productionRule);
                if (reducedSymbol.equals("P")){
                    stack.push("3");
                } else {
                    String newState = parsingTable[Integer.parseInt(stack.peek())][getNonTerminalIndex(reducedSymbol)];
                    stack.push(reducedSymbol);
                    stack.push(newState);
                }
            }
        }
    }

    private int getTokenIndex(String token) {
        // Define the mapping from token to column index in the parsing table
        // Modify this based on your actual token set
        String[] tokens = {"(", ")", "*", "+", "-", "/", ";", "=", "i", "n", "$", "E", "P", "T"};
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals(token)) {
                return i;
            }
        }
        return -1; // Token not found
    }

    private int getNonTerminalIndex(String symbol) {
        // Define the mapping from non-terminal symbol to column index in the parsing table
        // Modify this based on your actual non-terminals
        String[] nonTerminals = {"E", "P", "T"};
        for (int i = 0; i < nonTerminals.length; i++) {
            if (nonTerminals[i].equals(symbol)) {
                return i + 11; // Shift the index by 11 to match the table layout
            }
        }
        return -1; // Symbol not found
    }

    private int getProductionLength(int productionRule) {
        // Define the length of each production rule
        // Modify this based on your actual grammar
        int[] productionLengths = {0, 1, 1, 1, 3, 3, 3, 3, 3, 3, 1, 1, 1, 3, 3};
        if (productionRule >= 0 && productionRule < productionLengths.length) {
            return productionLengths[productionRule];
        }
        return 0; // Invalid rule
    }

    private String getReducedSymbol(int productionRule) {
        // Define the left-hand side symbol of each production rule
        // Modify this based on your actual grammar
        String[] reducedSymbols = {"P'", "P", "P", "E", "E", "E", "E", "E", "E", "E", "T", "T", "T", "T", "T"};
        if (productionRule >= 0 && productionRule < reducedSymbols.length) {
            return reducedSymbols[productionRule];
        }
        return null; // Invalid rule
    }
}