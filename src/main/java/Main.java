import Auxiliar.Pair;
import Auxiliar.SemanticException;
import Auxiliar.SyntaxException;
import Auxiliar.Writer;
import Lexicon.faseLexica;
import Semantic.AST;
import Semantic.faseSemantica;
import Syntax.faseSintactica;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Error message if the number of arguments is incorrect

//        if (args.length != 2) {
//            System.out.println("Uso incorrecto. Debes proporcionar un archivo de entrada y el nombre del archivo de salida.");
//            return;
//        }
//
//        String inputFileName = args[0]; // The input is taken from the first argument
        String outputFileName = "output.txt"; // The name of the output file is taken from the second argument
        String inputFileName = "prueba4.txt";
        // Read the input file
        String text = Auxiliar.Writer.getText(inputFileName);

        // Obtain the tokens from lexical phase
        Map<String, Pair<String, String>> tokens = faseLexica.tokenize(text);
        // Obtain the errors from lexical phase
        Map<Integer, String> lexErrors = faseLexica.getErrors();

        // Write the tokens to the output file
        Writer.writeTokens(tokens, outputFileName);

        // If there are lexical errors, stop the program
        if (!lexErrors.isEmpty()) {
            // Write the errors to the error file
            Writer.writeLexErrors(lexErrors);

            // Message of lexical error
            System.err.println("Error [Fase Léxica]: El archivo contiene errores léxicos, revisa el archivo error.txt");
            return;
        }

        ArrayList <Pair<String, String>> tokensList = new ArrayList<>();
        for (Map.Entry<String, Pair<String, String>> entry : tokens.entrySet()) {
            tokensList.add(new Pair<>(entry.getValue().getKey(), entry.getValue().getValue()));
        }
        tokensList.add(new Pair<>("$", null));


        Map<String, Pair<String, String>> newSymbolTable = new LinkedHashMap<>();
        // Parse the tokens and get true if there is a syntax error and the line number
        try {
            faseSintactica.parse(tokensList);
        } catch (SyntaxException e) {
            Integer errorLineNumber = faseSintactica.getLineNumber();

            // Map for updating the symbol table
            Writer.writeSyntaxError(errorLineNumber);

            // Message of syntax error
            System.err.println("Error [Fase Sintáctica]: El archivo contiene errores sintácticos. Revisa el archivo error.txt" +
                    "\nLa tabla de símbolos fue actualizada con los tokens válidos. Revisa el archivo tablaDeSimbolos.txt");

            // Create the symbol table without the tokens of the line with the error
            for (Map.Entry<String, Pair<String, String>> entry : tokens.entrySet()) {
                if (!entry.getKey().contains("l" + errorLineNumber) || !entry.getValue().getValue().equals("IDENTIFICADOR")) {
                    newSymbolTable.put(entry.getKey(), entry.getValue());
                }
            }
            System.exit(1);
        }

        for (Map.Entry<String, Pair<String, String>> entry : tokens.entrySet()) {
            if (entry.getValue().getKey().equals("IDENTIFICADOR")) {
                newSymbolTable.put(entry.getKey(), entry.getValue());
            }
        }

        AST tree = faseSintactica.getTree();

        try {
            faseSemantica.analyzeSemantics(tree.getRoot());
        } catch (SemanticException e) {
            Integer errorLineNumber = faseSemantica.getLineNumber();
            String identifierName = e.toString();
            Writer.writeSemanticError(errorLineNumber, identifierName);

            System.err.println("Error [Fase Semántica]: El archivo contiene errores semánticos. Revisa el archivo error.txt");

            System.exit(0);
        }

        // Write the tokens to the output file
        outputFileName = "tablaDeSimbolos.txt";
        Writer.writeTokens(newSymbolTable, outputFileName);

        // Message of success
        System.out.println("Input is valid.");
    }
}
