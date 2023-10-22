package Auxiliar;

import java.io.*;

public class Writer {

    public static String getText(String inputFileName) {
        StringBuilder sb = new StringBuilder();

        try {
            // Open the input file
            FileInputStream fis = new FileInputStream(inputFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            // Create a new lexical phase
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public static int countLines(String text){
        String[] lines = text.split("\n");
        return lines.length-1;
    }
}
