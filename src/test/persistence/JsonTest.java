package persistence;

import model.Flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;

// jUnit test for checking flashcards
public class JsonTest {
    protected void checkFlashcard(String prompt, String answer, Flashcard flashcard) {
        assertEquals(prompt, flashcard.getPrompt());
        assertEquals(answer, flashcard.getAnswer());
    }
}
