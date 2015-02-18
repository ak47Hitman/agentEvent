import java.io.*;
import java.util.ArrayList;

/**
 * Created by xhplogvievg_ on 04.02.2015.
 */

public class TextFile {
    //Read text from file and return string Text
    public static String read(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader( new File(fileName).getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            System.out.println("File does not exist: " + e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    //Take text from file and write in the output file
    //!!!!!Not consider /n
    public static void write(String fileName, String text) {
        try {
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Split input text and return string massive
    public String[] splitToString(String inputText, String splitType) {
        String[] massString = inputText.split(splitType);
        return massString;
    }
    //Проверить работоспособность метки незабыть
    //Find word in text
    public ArrayList<String> findWordInMyText(String textFromFile, String findError) {
        final String textSpliteType = "\n";
        final String stringFindError = " ";
        String[] splitText = splitToString(textFromFile, textSpliteType);
        ArrayList<String> errorList = new ArrayList<String>();
        for (int i = 0; i < splitText.length; i++) {
            String[] splitString = splitToString(splitText[i], stringFindError);
        output:    for (int k = 0; k < splitString.length; k++) {
                        if (splitString[k].toLowerCase().equals(findError.toLowerCase())) {
                            errorList.add(splitText[i-1] + "\n" + splitText[i]);
                            break  output;
                        }
                    }
        }
        return errorList;
    }
}