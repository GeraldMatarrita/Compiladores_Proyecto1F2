public class main {
    public static void main(String[] args) {

        String[] inputTokens = {"i", "=", "n", "*", "(", "i", "+", "n", ")", "$"};
        SLRParser parser = new SLRParser(inputTokens);
        parser.parse();
    }
}
