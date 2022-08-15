package persistence;

import model.Deck;
import model.Flashcard;
import model.WorkRoom;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for JsonWriter
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.numDecks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("Jerry's workroom");
            Deck testDeck1 = new Deck("asdf");
            Deck testDeck2 = new Deck("qwerty");
            wr.addNewDeck(testDeck1);
            wr.addNewDeck("qwerty");
            wr.addFlashcard(testDeck1, new Flashcard("vfs", "vrgf"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            assertEquals("Jerry's workroom", wr.getName());
            List<Deck> decks = wr.getDeckList();
            assertEquals(2, decks.size());

            assertEquals(1, decks.get(0).getFlashcards().size());
            checkFlashcard("vfs", "vrgf", decks.get(0).getFlashcards().get(0));

            assertTrue(decks.get(1).getFlashcards().isEmpty());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
