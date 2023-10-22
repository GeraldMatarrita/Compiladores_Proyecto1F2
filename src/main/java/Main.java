import Lexicon.Lexer;
import Auxiliar.Pair;
import Syntax.SLRParser;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        String inputFileName = "al = 1 ;\n" +
                "a = 87 ;\n" +
                "ba = al * ( 71 - 45 ) ;\n" +
                "c = ( 5 + ba * b ) ;"; //args[0]; // The input is taken from the first argument
//        String outputFileName = args[1]; // The name of the output file is taken from the second argument
//        String errorFileName = "error.txt"; // Name of the error file

//        String text = Auxiliar.Writer.getText(inputFileName);

        Map<String, Pair<String, String>> tokens = Lexer.tokenize(inputFileName);
        Map<Integer, String> lexErrors = Lexer.getErrors();

        ArrayList<String> inputTokens = new ArrayList<>();
        SLRParser parser = new SLRParser();
        int lineNumber = 1;

        for (Map.Entry<String, Pair<String, String>> entry : tokens.entrySet()) {
            try {
                if (entry.getKey().contains("lb")) {
                    inputTokens.add(entry.getValue().getKey());
                    inputTokens.add("$");
                    Optional<String> error = Optional.ofNullable(parser.parse(inputTokens));
                    inputTokens.clear();
                    if (error.isPresent()) {
                        System.err.println("Error [Fase Sintáctica]: La línea " + lineNumber + " contiene un error en su gramática, falta(n) token(s) después de: " + error.get());
                    }
                    lineNumber++;
                } else {
                    inputTokens.add(entry.getValue().getKey());
                }
            } catch (Exception e) {
                System.err.println("Error [Fase Sintáctica]: La línea " + lineNumber + " contiene un error en su gramática");
                break;
            }
        }
    }
}
