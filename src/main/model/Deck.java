package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a deck of flashcards
public class Deck implements Writable {
    private String title;
    private List<Flashcard> flashcards;

    // REQUIRES: title is not the empty string
    // EFFECTS: creates a new empty deck of flashcards with given title
    public Deck(String title) {
        this.title = title;
        flashcards = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    // REQUIRES: prompt and answer are not the empty string
    // MODIFIES: this
    // EFFECTS: adds new flashcard with a prompt and answer to flashcards
    public void addFlashcard(String prompt, String answer) {
        flashcards.add(new Flashcard(prompt, answer));
        EventLog.getInstance().logEvent(new Event("Flashcard " + prompt + ", "
                + answer + " added to " + title));
    }

    // MODIFIES: this
    // EFFECTS: adds new flashcard to flashcards
    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
        EventLog.getInstance().logEvent(new Event("Flashcard " + flashcard.getPrompt() + ", "
                + flashcard.getAnswer() + " added to " + title));
    }

    // MODIFIES: this
    // EFFECTS: remove flashcard with given prompt from flashcards
    public boolean removeFlashcard(String prompt) {
        for (Flashcard flashcard : flashcards) {
            if (flashcard.getPrompt().equals(prompt)) {
                flashcards.remove(flashcard);
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: changes title to newTitle
    public void changeTitle(String newTitle) {
        title = newTitle;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("flashcards", flashcards);
        return json;
    }

}
