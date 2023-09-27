package TextManipulatorApp;

import TextManipulator.TextManipulator;

import java.util.Scanner;

public class TextManipulatorApp {
    public void run(String pathOfInputFile, String pathOfOutputFile){
        String word1, word2;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите слова для обмена.");
        System.out.print("Первое слово: "); word1 = scanner.next();
        System.out.print("Второе слово: "); word2 = scanner.next();
        TextManipulator textManipulator = new TextManipulator(pathOfInputFile, pathOfOutputFile, word1, word2);
        while (true) {
            System.out.println("\nИсходный текст:");
            textManipulator.printSourceTextList();
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить элемент(ы) в начало текста");
            System.out.println("2. Добавить элемент(ы) в конец текста");
            System.out.println("3. Обменять местами два слова в соседних предложениях");
            System.out.println("4. Выйти");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("Введите элемент(ы) для добавления в начало текста: ");
                    String itemToAddStart = scanner.nextLine();
                    textManipulator.addToStart(itemToAddStart);
                }
                case 2 -> {
                    System.out.print("Введите элемент(ы) для добавления в конец текста: ");
                    String itemToAddEnd = scanner.nextLine();
                    textManipulator.addToEnd(itemToAddEnd);
                }
                case 3 -> {
                    System.out.println("\nИсходный текст:");
                    textManipulator.printSourceTextList();
                    System.out.println("\nМодифицированный текст:");
                    textManipulator.printModifiedTextList();
                    System.out.println("\nПрограмма завершена.");
                    System.exit(0);
                }
                case 4 -> System.exit(0);
                default -> System.out.println("Некорректный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }
}