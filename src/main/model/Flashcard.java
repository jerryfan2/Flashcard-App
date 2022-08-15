package model;

// Represents a flashcard having a prompt and answer
public class Flashcard {
    private String prompt;
    private String answer;

    // REQUIRES: prompt and answer are not the empty string
    // EFFECTS: creates flashcard with a prompt and answer
    public Flashcard(String prompt, String answer) {
        this.prompt = prompt;
        this.answer = answer;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getAnswer() {
        return answer;
    }

    // REQUIRES: newPrompt is not the empty string
    // MODIFIES: this
    // EFFECTS: changes prompt to newPrompt
    public void editPrompt(String newPrompt) {
        prompt = newPrompt;
    }

    // REQUIRES: newAnswer is not the empty string
    // MODIFIES: this
    // EFFECTS: changes answer to newAnswer
    public void editAnswer(String newAnswer) {
        answer = newAnswer;
    }

}
