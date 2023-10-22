package Syntax;

import Auxiliar.SyntaxException;

import java.util.ArrayList;
import java.util.Stack;

public class SLRParser {
    private final String[][] parsingTable = {
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

    private ArrayList<String> inputTokens;
    private final Stack<String> stack;

    public SLRParser() {
        this.inputTokens = null;
        this.stack = new Stack<>();
        stack.push("0");
    }

    public String parse(ArrayList<String> inputToken) throws SyntaxException {

        setInputTokens(inputToken);
        int tokenIndex = 0;

        while (true) {
            String currentState = stack.peek();
            String currentToken = inputTokens.get(tokenIndex);

            String action = parsingTable[Integer.parseInt(currentState)][getTokenIndex(currentToken)];

            if (action == null) {
                clearStack();
                throw new SyntaxException();
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
                String newState = parsingTable[Integer.parseInt(stack.peek())][getNonTerminalIndex(reducedSymbol)];
                stack.push(reducedSymbol);
                stack.push(newState);
            }
        }
        clearStack();
        return null;
    }

    private int getTokenIndex(String token) {
        // Define the mapping from token to column index in the parsing table
        // Modify this based on your actual token set
        String[] tokens = {"PARENTESIS_IZQ", "PARENTESIS_DER", "MULTIPLICACION", "SUMA", "RESTA", "DIVISION",
                "PUNTO_COMA", "ASIGNACION", "IDENTIFICADOR", "NUMERO", "$", "E", "P", "T"};
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
        int[] productionLengths = {0, 2, 2, 1, 3, 3, 3, 3, 3, 1, 1, 3, 3, 3};
        if (productionRule >= 0 && productionRule < productionLengths.length) {
            return productionLengths[productionRule];
        }
        return 0; // Invalid rule
    }

    private String getReducedSymbol(int productionRule) {
        // Define the left-hand side symbol of each production rule
        // Modify this based on your actual grammar
        String[] reducedSymbols = {"P'", "P", "P", "E", "E", "E", "E", "E", "E", "T", "T", "T", "T", "T"};
        if (productionRule >= 0 && productionRule < reducedSymbols.length) {
            return reducedSymbols[productionRule];
        }
        return null; // Invalid rule
    }

    public void setInputTokens(ArrayList<String> inputTokens) {
        this.inputTokens = inputTokens;
    }

    private void clearStack() {
        this.stack.clear();
        stack.push("0");
    }

//    public String getExpectedToken(String currentState, String currentToken) {
//
//        int stateNumber = Integer.parseInt(currentState);
//
//        switch (stateNumber) {
//            case 0:
//                // En el estado 0, se espera un inicio de declaración (i.e., "i" o "n")
//                return "Identificador o número";
//            case 2:
//                // En el estado 2, se espera un "="
//                return "=";
//            case 6:
//                // En el estado 6, se espera un ";" (fin de declaración)
//                return ";";
//            case 7:
//                // En el estado 7, se espera un operador binario ("+", "-", "*", "/", ")", o "$")
//                return "+, -, *, /, ), o $";
//            case 8:
//                // En el estado 8, se espera un inicio de expresión ("i", "n", o "(")
//                return "i, n, o (";
//            case 9:
//                // En el estado 9, se espera un operador binario ("+", "-", "*", "/", ")", o "$")
//                return "+, -, *, /, ), o $";
//            case 12:
//                // En el estado 12, se espera un operador binario ("+", "-", "*", "/", ")", o "$")
//                return "+, -, *, /, ), o $";
//            case 13:
//                // En el estado 13, se espera un operador binario ("+", "-", "*", "/", ")", o "$")
//                return "+, -, *, /, ), o $";
//            case 14:
//                // En el estado 14, se espera un operador binario ("+", "-", "*", "/", ")", o "$")
//                return "+, -, *, /, ), o $";
//            case 15:
//                // En el estado 15, se espera un operador binario ("+", "-", "*", "/", ")", o "$")
//                return "+, -, *, /, ), o $";
//            case 16:
//                // En el estado 16, se espera un inicio de expresión ("i", "n", o "(")
//                return "i, n, o (";
//            case 17:
//                // En el estado 17, se espera un operador binario ("+", "-", "*", "/", ")", o "$")
//                return "+, -, *, /, ), o $";
//            case 18:
//                // En el estado 18, se espera un operador binario ("+", "-", "*", "/", ")", o "$")
//                return "+, -, *, /, ), o $";
//            case 19:
//                // En el estado 19, se espera un operador binario ("+", "-", "*", "/", ")", o "$")
//                return "+, -, *, /, ), o $";
//            case 21:
//                // En el estado 21, se espera un inicio de expresión ("i", "n", o "(")
//                return "i, n, o (";
//            case 25:
//                // En el estado 25, se espera un operador binario ("+", "-", "*", "/", ")", o "$")
//                return "+, -, *, /, ), o $";
//            default:
//                // Para otros estados, no podemos determinar un token específico esperado, así que devolvemos "token desconocido".
//                return "token desconocido";
//        }
//
//    }
}