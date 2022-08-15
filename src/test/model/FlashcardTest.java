package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for Flashcard Class
class FlashcardTest {
    private Flashcard testFlashcard;

    @BeforeEach
    void runBefore() {
        testFlashcard = new Flashcard("Year Canada became independent", "1867");
    }

    @Test
    void testConstructor() {
        assertEquals("Year Canada became independent", testFlashcard.getPrompt());
        assertEquals("1867", testFlashcard.getAnswer());
    }

    @Test
    void testEditPrompt() {
        testFlashcard.editPrompt("Year the US purchased Alaska");
        assertEquals("Year the US purchased Alaska", testFlashcard.getPrompt());
    }

    @Test
    void testEditAnswer() {
        testFlashcard.editAnswer("2021");
        assertEquals("2021", testFlashcard.getAnswer());
    }

}