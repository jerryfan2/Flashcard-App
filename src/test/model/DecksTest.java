package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for Decks class
public class DecksTest {
    private Decks testDecks;

    @BeforeEach
    void runBefore() {
        testDecks = new Decks();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testDecks.getDeckList().size());
    }

    @Test
    void testAddRemoveDeck() {
        testDecks.addNewDeck("Music History");
        assertEquals(1, testDecks.getDeckList().size());
        assertEquals("Music History", testDecks.getDeckList().get(0).getTitle());

        testDecks.addNewDeck("Biology");
        assertEquals(2, testDecks.getDeckList().size());
        assertEquals("Biology", testDecks.getDeckList().get(1).getTitle());
        assertEquals("Music History", testDecks.getDeckList().get(0).getTitle());

        assertTrue(testDecks.removeDeck("Music History"));
        assertEquals(1, testDecks.getDeckList().size());
        assertEquals("Biology", testDecks.getDeckList().get(0).getTitle());

        assertFalse(testDecks.removeDeck("Not exist"));
        assertEquals(1, testDecks.getDeckList().size());
        assertEquals("Biology", testDecks.getDeckList().get(0).getTitle());

        assertTrue(testDecks.removeDeck("Biology"));
        assertEquals(0, testDecks.getDeckList().size());
    }

}
