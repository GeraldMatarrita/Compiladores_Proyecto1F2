import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String inputFileName = "al = 1 ;\n" +
                "a = 87 ;\n" +
                "ba = al * ( 71 - 45 ) ;\n" +
                "c = ( 5 + ba * b ;"; //args[0]; // The input is taken from the first argument
//        String outputFileName = args[1]; // The name of the output file is taken from the second argument
//        String errorFileName = "error.txt"; // Name of the error file

//        String text = Writer.getText(inputFileName);

        Map<Integer, Pair<String, String>> tokens = faseLexica.tokenize(inputFileName);
        Map<Integer, String> lexErrors = faseLexica.getErrors();

        ArrayList<String> inputTokens = new ArrayList<>();
        SLRParser parser = new SLRParser();

        for (Map.Entry<Integer, Pair<String, String>> entry : tokens.entrySet()) {
            if (entry.getValue().getA().equals("PUNTO_COMA")) {
                inputTokens.add(entry.getValue().getA());
                inputTokens.add("$");
                parser.parse(inputTokens);
                inputTokens.clear();
            } else {
                inputTokens.add(entry.getValue().getA());
            }
        }
    }
}
