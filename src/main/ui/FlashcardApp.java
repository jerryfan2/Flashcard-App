package ui;

import model.Deck;
import model.Flashcard;
import model.WorkRoom;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents the console in which the Flashcard Application will appear
public class FlashcardApp {
    private static final String JSON_STORE = "./data/workroom.json";
    private Scanner input;
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs workroom and runs the flashcard application
    public FlashcardApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        workRoom = new WorkRoom("Jerry's workroom");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runFlashcard();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runFlashcard() {
        boolean keepGoing = true;
        String homepageCommand;

        init();

        while (keepGoing) {
            displayHomepageMenu();
            homepageCommand = input.next();
            homepageCommand = homepageCommand.toLowerCase();

            if (homepageCommand.equals("q")) {
                keepGoing = false;
            } else {
                processHomepageCommand(homepageCommand);
            }
        }
    }

    // EFFECTS: displays home page menu of options to user
    private void displayHomepageMenu() {
        System.out.println("\nWelcome to Jerry's flashcard application!");
        System.out.println("Select from:");
        System.out.println("c -> choose decks");
        System.out.println("d -> edit decks");
        System.out.println("s -> save work room to file");
        System.out.println("l -> load work room from file");
        System.out.println("q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processHomepageCommand(String command) {
        switch (command) {
            case "c":
                chooseDeckMenu();
                break;
            case "d":
                enterDeckSettings();
                break;
            case "s":
                saveWorkRoom();
                break;
            case "l":
                loadWorkRoom();
                break;
            default:
                System.out.println("Please select a valid command");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes decks and input scanner
    private void init() {
        //decks = new Decks();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: enters into flashcard mode with the given deck
    private void enterFlashcardMode(Deck deck) {
        for (Flashcard flashcard: deck.getFlashcards()) {
            System.out.println(flashcard.getPrompt());
            System.out.println("Press any button to reveal answer");
            input.next();
            System.out.println(flashcard.getAnswer());
        }
        System.out.println("Flashcards completed!");
    }

    // EFFECTS: runs menu for choosing deck
    private void chooseDeckMenu() {
        boolean keepGoing = true;
        String selectDecksCommand;

        while (keepGoing) {
            displayChooseDeckMenu();
            selectDecksCommand = input.next();
            selectDecksCommand = selectDecksCommand.toLowerCase();

            if (selectDecksCommand.equals("b")) {
                keepGoing = false;
            } else {
                for (Deck deck: workRoom.getDeckList()) {
                    if (selectDecksCommand.equals(deck.getTitle())) {
                        enterFlashcardMode(deck);
                        break;
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: enters deck settings mode
    private void enterDeckSettings() {
        boolean keepGoing = true;
        String deckSettingsCommand;

        while (keepGoing) {
            displayDeckSettingsMenu();
            deckSettingsCommand = input.next();
            deckSettingsCommand = deckSettingsCommand.toLowerCase();

            if (deckSettingsCommand.equals("b")) {
                keepGoing = false;
            } else {
                processDeckSettingsCommand(deckSettingsCommand);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes deck settings command
    private void processDeckSettingsCommand(String command) {
        switch (command) {
            case ("c"): {
                displayCreateDeckMenu();
                break;
            } case ("e"): {
                displayEditDeckMenu();
                break;
            } case ("d"): {
                displayDeleteDeckMenu();
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays menu for creating new deck
    private void displayCreateDeckMenu() {
        System.out.println("\nEnter name of new deck: ");
        String name = input.next();
        workRoom.addNewDeck(new Deck(name));
        //work.addNewDeck(name);
        System.out.println("Deck created");
    }

    // MODIFIES: this
    // EFFECTS: displays menu for editing deck
    private void displayEditDeckMenu() {
        boolean keepGoing = true;

        if (workRoom.getDeckList().isEmpty()) {
            System.out.println("There are no decks to edit");
            keepGoing = false;
        }

        while (keepGoing) {
            System.out.println("\nEnter name of deck you want to edit: ");

            for (Deck deck: workRoom.getDeckList()) {
                System.out.println(deck.getTitle());
            }
            String name = input.next();

            for (Deck deck : workRoom.getDeckList()) {
                if (name.equals(deck.getTitle())) {
                    editingMenu(deck);
                    keepGoing = false;
                }
            }
            if (keepGoing) {
                System.out.println("Please enter valid deck name");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays menu for deleting deck
    private void displayDeleteDeckMenu() {
        boolean keepGoing = true;

        if (workRoom.getDeckList().isEmpty()) {
            System.out.println("There are no decks to edit");
            keepGoing = false;
        }

        while (keepGoing) {
            System.out.println("\nEnter name of deck you want to delete: ");

            for (Deck deck : workRoom.getDeckList()) {
                System.out.println(deck.getTitle());
            }
            String name = input.next();

            boolean isRemoved = workRoom.removeDeck(name);
            if (isRemoved) {
                keepGoing = false;
                System.out.println("Deck removed");
            } else {
                System.out.println("Please enter valid deck name");
            }
        }
    }

    // MODIFIES: deck
    // EFFECTS: enters menu for editing deck
    private void editingMenu(Deck deck) {
        System.out.println("\na -> add flashcard");
        System.out.println("r -> remove flashcard");
        System.out.println("c -> change deck name");
        System.out.println("e -> edit flashcards");

        String command = input.next().toLowerCase();

        switch (command) {
            case ("a"): {
                addFlashcardMenu(deck);
                break;
            } case ("r"): {
                removeFlashcardMenu(deck);
                break;
            } case ("c"): {
                changeDeckNameMenu(deck);
                break;
            } case ("e"): {
                editFlashcardsMenu(deck);
                break;
            }
        }
    }

    // MODIFIES: deck
    // EFFECTS: displays menu to add flashcard to deck
    private void addFlashcardMenu(Deck deck) {
        System.out.println("\nEnter flashcard prompt: ");
        String prompt = input.next();
        System.out.println("\nEnter flashcard answer: ");
        String answer = input.next();
        workRoom.addFlashcard(deck, new Flashcard(prompt, answer));
        System.out.println("Flashcard added");
    }

    // MODIFIES: deck
    // displays menu for removing flashcard
    private void removeFlashcardMenu(Deck deck) {
        boolean keepGoing = true;
        if (deck.getFlashcards().isEmpty()) {
            System.out.println("There are no flashcards to remove");
            keepGoing = false;
        }

        while (keepGoing) {
            System.out.println("\nEnter prompt of flashcard to remove: ");
            for (Flashcard flashcard: deck.getFlashcards()) {
                System.out.println(flashcard.getPrompt());
            }

            String prompt = input.next();

            boolean isRemoved = deck.removeFlashcard(prompt);
            if (isRemoved) {
                System.out.println("Flashcard removed");
                keepGoing = false;
            } else {
                System.out.println("Please enter a valid flashcard prompt");
            }
        }
    }

    // MODIFIES: deck
    // EFFECTS: displays menu for changing deck name
    private void changeDeckNameMenu(Deck deck) {
        System.out.println("Enter new deck name: ");
        String newTitle = input.next();

        deck.changeTitle(newTitle);
        System.out.println("Deck name has been changed to " + newTitle);
    }

    // MODIFIES: deck
    // EFFECTS: displays menu for editing flashcards
    private void editFlashcardsMenu(Deck deck) {
        boolean keepGoing = true;
        if (deck.getFlashcards().isEmpty()) {
            System.out.println("There are no flashcards to remove");
            keepGoing = false;
        }

        while (keepGoing) {
            System.out.println("Enter prompt of flashcard you would like to edit: ");
            for (Flashcard flashcard: deck.getFlashcards()) {
                System.out.println(flashcard.getPrompt());
            }

            String prompt = input.next();

            for (Flashcard flashcard: deck.getFlashcards()) {
                if (flashcard.getPrompt().equals(prompt)) {
                    changingFlashcardPrompt(flashcard);
                    changingFlashcardAnswer(flashcard);
                    keepGoing = false;
                }
            }
            if (keepGoing) {
                System.out.println("Please enter a valid flashcard prompt");
            }
        }
    }

    // EFFECTS: displays menu to choose deck
    private void displayChooseDeckMenu() {
        System.out.println("\nEnter a deck name: ");
        System.out.println("b -> go back");
        for (Deck deck: workRoom.getDeckList()) {
            System.out.println(deck.getTitle());
        }
    }

    // EFFECTS: display menu for deck settings
    private void displayDeckSettingsMenu() {
        System.out.println("\nc -> create new deck");
        System.out.println("e -> edit existing deck");
        System.out.println("d -> delete existing deck");
        System.out.println("b -> go back");
    }

    // MODIFIES: flashcard
    // EFFECTS: displays menu for changing the prompt of a flashcard
    private void changingFlashcardPrompt(Flashcard flashcard) {
        System.out.println("c -> change flashcard prompt");
        System.out.println("enter any other key to keep flashcard prompt");

        String command = input.next().toLowerCase();

        if (command.equals("c")) {
            System.out.println("Enter new flashcard prompt: ");
            String newPrompt = input.next();
            flashcard.editPrompt(newPrompt);
            System.out.println("Prompt has been changed to " + newPrompt);
        }

    }

    // MODIFIES: flashcard
    // EFFECTS: displays menu for changing the answer of a flashcard
    private void changingFlashcardAnswer(Flashcard flashcard) {
        System.out.println("c -> change flashcard answer");
        System.out.println("enter any other key to keep flashcard answer");

        String command = input.next().toLowerCase();

        if (command.equals("c")) {
            System.out.println("Enter new flashcard prompt: ");
            String newAnswer = input.next();
            flashcard.editAnswer(newAnswer);
            System.out.println("Answer has been changed to " + newAnswer);
        }
    }


    // EFFECTS: saves the workroom to file
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
            System.out.println("Saved " + workRoom.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void loadWorkRoom() {
        try {
            workRoom = jsonReader.read();
            System.out.println("Loaded " + workRoom.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
