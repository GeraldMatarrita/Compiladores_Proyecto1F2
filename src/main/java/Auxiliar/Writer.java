package Auxiliar;

import java.io.*;
import java.util.Map;

public class Writer {

    /**
     * Reads the text from the input file and returns it as a string.
     *
     * @param inputFileName The name of the input file.
     * @return The text from the input file.
     */
    public static String getText(String inputFileName) {

        // Stores the text from the input file
        StringBuilder sb = new StringBuilder();

        // Open the input file
        try (FileInputStream fis = new FileInputStream(inputFileName); BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {

            // Read the input file line by line
            String line;

            // Write the line to the string builder
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }


    /**
     * Writes the lexical errors to the error file.
     *
     * @param lexErrors The lexical errors to write.
     */
    public static void writeLexErrors(Map<Integer, String> lexErrors) {

        // Name of the error file
        String errorFileName = "error.txt";

        // Open the error file
        try (FileOutputStream fosErrors = new FileOutputStream(errorFileName);
             BufferedWriter errorWriter = new BufferedWriter(new OutputStreamWriter(fosErrors))) {

            // Write the errors to the error file
            for (Map.Entry<Integer, String> errorEntry : lexErrors.entrySet()) {

                int errorLineNumber = errorEntry.getKey(); // The line number of the error
                String errorToken = errorEntry.getValue(); // The token that caused the error

                // Write the error to the error file with the format
                // "Error [Fase Léxica]: La línea <line number> contiene un error, lexema no reconocido: <token>"
                errorWriter.write("Error [Fase Léxica]: La línea " + errorLineNumber +
                        " contiene un error, lexema no reconocido: " + errorToken);
                errorWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes the tokens to the output file.
     *
     * @param tokens The tokens to write.
     * @param outputFileName The name of the output file.
     */
    public static void writeTokens(Map<String, Pair<String, String>> tokens, String outputFileName){

        // Open the output file
        try (FileOutputStream fos = new FileOutputStream(outputFileName);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {

            // Write the tokens to the output file
            for (Map.Entry<String, Pair<String, String>> tokenEntry : tokens.entrySet()) {
                String token = tokenEntry.getValue().getValue(); // The token
                String tokenType = tokenEntry.getValue().getKey(); // The type of the token

                // Write the token to the output file with the format "Token: <token>, Type: <type>"
                writer.write("Token: " + token + ", Tipo: " + tokenType);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes the syntax error to the error file.
     *
     * @param lineNumber The line number of the syntax error.
     */
    public static void writeSyntaxError(Integer lineNumber){

        // Name of the error file
        String errorFileName = "error.txt";

        // Open the error file
        try (FileOutputStream fosErrors = new FileOutputStream(errorFileName);
             BufferedWriter errorWriter = new BufferedWriter(new OutputStreamWriter(fosErrors))) {

            // Write the error to the error file with the format
            // "Error [Fase Sintáctica]: La línea <line number> contiene un error sintáctico"
            errorWriter.write("Error [Fase Sintáctica]: La línea " + lineNumber + " contiene un error en su gramática");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
