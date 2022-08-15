package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


// Represents a workroom having a list of decks
public class WorkRoom extends Decks implements Writable {
    private String name;

    // EFFECTS: constructs workroom with a name and empty list of decks
    public WorkRoom(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // EFFECTS: returns number of decks in this workroom
    public int numDecks() {
        return getDeckList().size();
    }


    // MODIFIES: deck
    // EFFECTS: adds flashcard to this deck
    public void addFlashcard(Deck deck, Flashcard flashcard) {
        Deck workRoomDeck = deck;
        for (Deck nextDeck : getDeckList()) {
            if (nextDeck.getTitle().equals(deck.getTitle())) {
                workRoomDeck = nextDeck;
            }
        }
        workRoomDeck.addFlashcard(flashcard);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("decks", decksToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray decksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Deck d : getDeckList()) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }

}
