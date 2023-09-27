package main;

import TextManipulatorApp.TextManipulatorApp;

public class Main {
    public static void main(String[] args) {
        String pathOfInputFile = "src/main/resources/textFiles/input.txt";
        String pathOfOutputFile = "src/main/resources/textFiles/output.txt";
        new TextManipulatorApp().run(pathOfInputFile, pathOfOutputFile);
    }
}