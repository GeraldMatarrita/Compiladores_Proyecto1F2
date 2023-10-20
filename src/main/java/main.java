public class main {
    public static void main(String[] args) {
        String[] inputTokens = {"i", "=", "i", "-", "(", "i", ")", ";", "$"};
        SLRParser parser = new SLRParser(inputTokens);
        parser.parse();
    }
}
