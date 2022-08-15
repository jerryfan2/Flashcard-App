package persistence;

import model.Deck;
import model.WorkRoom;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for JsonReader
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile/json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            WorkRoom wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.numDecks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            WorkRoom wr = reader.read();
            assertEquals("Jerry's workroom", wr.getName());
            List<Deck> decks = wr.getDeckList();
            assertEquals(2, decks.size());

            assertEquals(3, decks.get(0).getFlashcards().size());
            checkFlashcard("vfd", "sfg", decks.get(0).getFlashcards().get(0));
            checkFlashcard("htthdbg", "rbthrnj ", decks.get(0).getFlashcards().get(1));
            checkFlashcard("bgf", "yjhdg", decks.get(0).getFlashcards().get(2));

            assertTrue(decks.get(1).getFlashcards().isEmpty());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
