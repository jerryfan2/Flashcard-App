package model;

import java.util.ArrayList;
import java.util.List;

// Represents a collection of flashcard decks
public class Decks {
    protected List<Deck> deckList;

    // EFFECTS: creates empty list of flashcard decks
    public Decks() {
        deckList = new ArrayList<>();
    }

    public List<Deck> getDeckList() {
        return deckList;
    }

    // REQUIRES: title is not the empty string
    // MODIFIES: this
    // EFFECTS: adds empty deck to deckList
    public void addNewDeck(String title) {
        deckList.add(new Deck(title));
        EventLog.getInstance().logEvent(new Event("New deck " + title + " created"));
    }

    // MODIFIES: this
    // EFFECTS: adds deck to deckList
    public void addNewDeck(Deck deck) {
        deckList.add(deck);
        EventLog.getInstance().logEvent(new Event("New deck " + deck.getTitle() + " created"));
    }

    // MODIFIES: this
    // EFFECTS: remove deck with given title from deckList
    public boolean removeDeck(String title) {
        for (Deck deck: deckList) {
            if (deck.getTitle().equals(title)) {
                deckList.remove(deck);
                return true;
            }
        }
        return false;
    }

}
