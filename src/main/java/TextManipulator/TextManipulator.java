package TextManipulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TextManipulator {
    private final List<String> sourceTextList = new ArrayList<>();
    private List<String> modifiedTextList = new ArrayList<>();

    private final String inputFilePath;
    private final String outputFilePath;

    private final String firstWord;
    private final String secondWord;

    public TextManipulator(String inputFilePath, String outputFilePath, String firstWord, String secondWord) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
        this.readSourceTextOfFile();
        this.writeModifiedTextInFile();
    }

    public void printSourceTextList(){
        printTextList(sourceTextList);
    }

    public void printModifiedTextList(){
        printTextList(modifiedTextList);
    }

    public void addToStart(String items) {
        ArrayList<String> buffModifiedTextList = new ArrayList<>();
        StringTokenizer tokenizer = tokenize(items);
        while(tokenizer.hasMoreTokens()) {
            buffModifiedTextList.add(tokenizer.nextToken());
        }
        buffModifiedTextList.add(" ");
        buffModifiedTextList.addAll(modifiedTextList);
        modifiedTextList = buffModifiedTextList;
    }
    public void addToEnd(String items) {
        StringTokenizer tokenizer = tokenize(items);
        modifiedTextList.add(" ");
        while(tokenizer.hasMoreTokens()) {
            modifiedTextList.add(tokenizer.nextToken());
        }
    }

    private void readSourceTextOfFile() {
        String sourceText = readSourceText();
        StringTokenizer tokenizer = tokenize(sourceText);
        while (tokenizer.hasMoreTokens()) {
            sourceTextList.add(tokenizer.nextToken());
        }
    }

    private void writeModifiedTextInFile(){
        modifiedTextList.addAll(sourceTextList);
        boolean isSwapped = false;
        for (int i = 0; i < modifiedTextList.size(); i++) {
            if (isFirstWord(modifiedTextList.get(i))) {
                int j = i;
                while (!isEndOfSentence(modifiedTextList.get(j))) j++;
                for (j = j+1; !isEndOfSentence(modifiedTextList.get(j)); j++) {
                    if (isSecondWord(modifiedTextList.get(j))) {
                        swap(i, j);
                        isSwapped = true;
                        break;
                    }
                }
            } else if (isSecondWord(modifiedTextList.get(i))) {
                int j = i;
                while (!isEndOfSentence(modifiedTextList.get(j))) j++;
                for (j = j+1; !isEndOfSentence(modifiedTextList.get(j)); j++) {
                    if (isFirstWord(modifiedTextList.get(j))) {
                        swap(i, j);
                        isSwapped = true;
                        break;
                    }
                }
            }
            if (isSwapped) break;
        }
        try {
            writeToFile(modifiedTextList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readSourceText() throws RuntimeException {
        String sourceText = null;
        try {
            sourceText = this.readFile();
        } catch (IOException e){
            throw new RuntimeException(e);
        } finally {
            return sourceText;
        }
    }

    private String readFile() throws IOException {
        List<String> lines = Files.readAllLines(Path.of(inputFilePath));
        return String.join(System.lineSeparator(), lines);
    }

    private StringTokenizer tokenize(String sourceText) {
        try {
            return new StringTokenizer(
                    sourceText,
                    " \t\n\r\f!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~",
                    true
            );
        } catch (NullPointerException e) {
            return new StringTokenizer("");
        }
    }

    private boolean isEndOfSentence(String token) {
        return token.matches("[.!?]");
    }

    private void printTextList(List<String> textList) {
        for (String word : textList) {
            System.out.print(word);
        }
        System.out.println();
    }

    private boolean isFirstWord(String token) {
        return this.firstWord.equalsIgnoreCase(token);
    }

    private boolean isSecondWord(String token) {
        return this.secondWord.equalsIgnoreCase(token);
    }

    private void swap(int firstIndex, int secondIndex) {
        String temp = modifiedTextList.get(firstIndex);
        modifiedTextList.set(firstIndex, modifiedTextList.get(secondIndex));
        modifiedTextList.set(secondIndex, temp);
    }

    private void writeToFile(List<String> tokens) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.outputFilePath));
        for (String token : tokens) {
            writer.write(token);
        }
        writer.close();
    }
}