package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for Deck class
public class DeckTest {
    private Deck testDeck;

    @BeforeEach
    void runBefore() {
        testDeck = new Deck("Countries");
    }

    @Test
    void testConstructor() {
        assertEquals("Countries", testDeck.getTitle());
        assertEquals(0, testDeck.getFlashcards().size());
    }

    @Test
    void testAddRemoveFlashcard() {
        testDeck.addFlashcard("Country Directly West of the UK", "Ireland");
        assertEquals(1, testDeck.getFlashcards().size());
        assertEquals("Country Directly West of the UK", testDeck.getFlashcards().get(0).getPrompt());
        assertEquals("Ireland", testDeck.getFlashcards().get(0).getAnswer());

        testDeck.addFlashcard("National Language of China", "Mandarin");
        assertEquals(2, testDeck.getFlashcards().size());
        assertEquals("National Language of China", testDeck.getFlashcards().get(1).getPrompt());
        assertEquals("Mandarin", testDeck.getFlashcards().get(1).getAnswer());
        assertEquals("Country Directly West of the UK", testDeck.getFlashcards().get(0).getPrompt());
        assertEquals("Ireland", testDeck.getFlashcards().get(0).getAnswer());

        assertTrue(testDeck.removeFlashcard("Country Directly West of the UK"));
        assertEquals(1, testDeck.getFlashcards().size());
        assertEquals("National Language of China", testDeck.getFlashcards().get(0).getPrompt());
        assertEquals("Mandarin", testDeck.getFlashcards().get(0).getAnswer());

        assertFalse(testDeck.removeFlashcard("Not exist"));
        assertEquals(1, testDeck.getFlashcards().size());
        assertEquals("National Language of China", testDeck.getFlashcards().get(0).getPrompt());
        assertEquals("Mandarin", testDeck.getFlashcards().get(0).getAnswer());

        assertTrue(testDeck.removeFlashcard("National Language of China"));
        assertEquals(0, testDeck.getFlashcards().size());
    }

    @Test
    void testChangeTitle() {
        testDeck.changeTitle("Geography");
        assertEquals("Geography", testDeck.getTitle());
    }

}
