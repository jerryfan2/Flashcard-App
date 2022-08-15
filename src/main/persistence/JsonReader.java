package persistence;

import model.Deck;
import model.Flashcard;
import model.WorkRoom;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and return it;
    // throws IOException if an error occurs reading from file
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public WorkRoom read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private WorkRoom parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WorkRoom wr = new WorkRoom(name);
        addDecks(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses decks from JSON object and adds them to workroom
    private void addDecks(WorkRoom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("decks");
        for (Object json : jsonArray) {
            JSONObject nextDeck = (JSONObject) json;
            addDeck(wr, nextDeck);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses deck from JSON object and adds it to workroom
    private void addDeck(WorkRoom wr, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        JSONArray flashcardsJson = jsonObject.getJSONArray("flashcards");
        Deck deck = new Deck(title);
        for (Object flashcardJson : flashcardsJson) {
            JSONObject nextFlashcard = (JSONObject) flashcardJson;
            addFlashcard(deck, nextFlashcard);
        }
        wr.addNewDeck(deck);
    }

    // MODIFIES: deck
    // EFFECTS: parses flashcard from JSON object and adds it to workroom
    private void addFlashcard(Deck deck, JSONObject jsonObject) {
        String prompt = jsonObject.getString("prompt");
        String answer = jsonObject.getString("answer");
        Flashcard flashcard = new Flashcard(prompt, answer);
        deck.addFlashcard(flashcard);
    }

}
