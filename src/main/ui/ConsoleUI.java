package ui;

import java.io.FileNotFoundException;

// Main method that runs the Flashcard Application
public class ConsoleUI {
    public static void main(String[] args) {
        try {
            new FlashcardApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
