package gui;

import model.Deck;
import model.Event;
import model.EventLog;
import model.Flashcard;
import model.WorkRoom;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the GUI in which the Flashcard Application will appear
public class Gui implements MouseListener {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final String JSON_STORE = "./data/workroom.json";
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame frame;
    private JPanel panel;
    private JButton flashcardButton;
    private JButton decksButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton backButton;
    private JButton enterButton;
    private JButton submitButton;
    private JButton selectButton;
    private JButton createDeckButton;
    private JButton editDeckButton;
    private JButton addFlashcardButton;
    private JButton enterPromptButton;
    private JButton enterAnswerButton;

    private JTextField textField;
    private JTextField secondTextField;
    private JTextField thirdTextField;

    // EFFECTS: constructs workroom and runs the flashcard application
    public Gui() {
        workRoom = new WorkRoom("Jerry's workroom");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeGraphics();
        initializeButtons();
        intializeTextFields();
        displayHomeMenu();
    }

    private void printLog(EventLog el) {
        for (Event event : el) {
            System.out.println(event.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes all buttons
    private void initializeButtons() {
        flashcardButton = new JButton("Flashcard Mode");
        decksButton = new JButton("Decks");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        backButton = new JButton("Back");
        enterButton = new JButton("Enter");
        submitButton = new JButton("Submit");
        selectButton = new JButton("Select");
        createDeckButton = new JButton("Create New Deck");
        editDeckButton = new JButton("Edit Deck");
        addFlashcardButton = new JButton("Add Flashcard");
        enterPromptButton = new JButton("Enter Prompt");
        enterAnswerButton = new JButton("Enter Answer");

        buttonsToMouseListener();
    }

    // MODIFIES: this
    // EFFECTS: initializes all text field
    private void intializeTextFields() {
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 40));

        secondTextField = new JTextField();
        secondTextField.setPreferredSize(new Dimension(200, 40));

        thirdTextField = new JTextField();
        thirdTextField.setPreferredSize(new Dimension(200, 40));
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to mouse listener
    private void buttonsToMouseListener() {
        flashcardButton.addMouseListener(this);
        decksButton.addMouseListener(this);
        saveButton.addMouseListener(this);
        loadButton.addMouseListener(this);
        backButton.addMouseListener(this);
        enterButton.addMouseListener(this);
        submitButton.addMouseListener(this);
        selectButton.addMouseListener(this);
        createDeckButton.addMouseListener(this);
        editDeckButton.addMouseListener(this);
        addFlashcardButton.addMouseListener(this);
        enterPromptButton.addMouseListener(this);
        enterAnswerButton.addMouseListener(this);
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where this flashcard application will operate
    private void initializeGraphics() {
        frame = new JFrame("Flashcard Application");
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        frame.add(panel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: displays home menu
    private void displayHomeMenu() {
        panel.removeAll();

        panel.setLayout(new GridLayout(0, 1));
        panel.add(flashcardButton);
        panel.add(decksButton);
        panel.add(saveButton);
        panel.add(loadButton);

        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: handles step after a button is pressed
    private void handleMousePressed(MouseEvent e) {
        if (e.getSource() == flashcardButton) {
            displayChooseDeck();
        } else if (e.getSource() == decksButton) {
            displayDecksMenu();
        } else if (e.getSource() == saveButton) {
            saveWorkRoom();
        } else if (e.getSource() == loadButton) {
            loadWorkRoom();
        } else if (e.getSource() == backButton) {
            displayHomeMenu();
        } else if (e.getSource() == createDeckButton) {
            displayCreateDeckMenu();
        } else if (e.getSource() == editDeckButton) {
            displayEditDeckMenu();
        } else if (e.getSource() == addFlashcardButton) {
            displayAddFlashcardPromptMenu();
        }
        handleTextButtons(e);
    }

    // EFFECTS: handles step after a text field button is pressed
    private void handleTextButtons(MouseEvent e) {
        if (e.getSource() == enterButton) {
            processDeckEntered();
        } else if (e.getSource() == submitButton) {
            processNewDeck();
        } else if (e.getSource() == selectButton) {
            processDeckEditSelected();
        } else if (e.getSource() == enterPromptButton) {
            displayAddFlashcardAnswerMenu();
        } else if (e.getSource() == enterAnswerButton) {
            processNewAnswer();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes new entered answer
    private void processNewAnswer() {
        for (Deck deck : workRoom.getDeckList()) {
            if (textField.getText().equals(deck.getTitle())) {
                deck.addFlashcard(secondTextField.getText(), thirdTextField.getText());
            }
        }
        displayHomeMenu();
    }

    // EFFECTS: display menu for adding new flashcard answer
    private void displayAddFlashcardAnswerMenu() {
        panel.removeAll();
        thirdTextField.setText("");
        panel.setLayout(new GridLayout(0, 1));

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.add(thirdTextField);
        textFieldPanel.add(enterAnswerButton, BorderLayout.EAST);
        panel.add(textFieldPanel, BorderLayout.NORTH);

        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: display menu for adding new flashcard prompt
    private void displayAddFlashcardPromptMenu() {
        panel.removeAll();
        secondTextField.setText("");
        panel.setLayout(new GridLayout(0, 1));

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.add(secondTextField);
        textFieldPanel.add(enterPromptButton, BorderLayout.EAST);
        panel.add(textFieldPanel, BorderLayout.NORTH);

        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: display menu for editing deck
    private void processDeckEditSelected() {
        panel.removeAll();
        panel.setLayout(new GridLayout(0, 1));

        panel.add(addFlashcardButton);

        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: adds new flashcard with new prompt and answer to previously selected deck
    private void processNewDeck() {
        String deckName = textField.getText();
        workRoom.addNewDeck(deckName);
        displayHomeMenu();
    }

    // EFFECTS: display selection of decks that can be edited
    private void displayEditDeckMenu() {
        panel.removeAll();
        textField.setText("");

        JPanel deckLabelPanel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(deckLabelPanel);
        JLabel chooseDeckLabel = new JLabel("Enter Deck Name");
        deckLabelPanel.add(chooseDeckLabel);

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.add(textField);
        textFieldPanel.add(selectButton, BorderLayout.EAST);
        panel.add(textFieldPanel, BorderLayout.NORTH);

        for (Deck deck : workRoom.getDeckList()) {
            JPanel deckDisplayPanel = new JPanel();
            JLabel deckDisplayLabel = new JLabel(deck.getTitle());
            panel.add(deckDisplayPanel);
            deckDisplayPanel.add(deckDisplayLabel);
        }
        panel.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: display menu for creating new deck
    private void displayCreateDeckMenu() {
        panel.removeAll();
        textField.setText("");

        JPanel deckLabelPanel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(deckLabelPanel);
        JLabel chooseDeckLabel = new JLabel("Enter Deck Name");
        deckLabelPanel.add(chooseDeckLabel);

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.add(textField);
        textFieldPanel.add(submitButton, BorderLayout.EAST);
        panel.add(textFieldPanel, BorderLayout.NORTH);

        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: reads text field and matches text to a deck title from the workroom
    private void processDeckEntered() {
        String deckName = textField.getText();
        for (Deck deck : workRoom.getDeckList()) {
            if (deckName.equals(deck.getTitle())) {
                enterFlashcardMode(deck);
            }
        }
    }

    // EFFECTS: enters flashcard mode with given deck
    private void enterFlashcardMode(Deck deck) {
        panel.removeAll();
        panel.setLayout(new GridLayout(0, 2));
        JLabel promptLabel = new JLabel("Prompts:");
        JLabel answerLabel = new JLabel("Answers:");
        panel.add(promptLabel);
        panel.add(answerLabel);
        for (Flashcard flashcard : deck.getFlashcards()) {
            JButton fcPrompt = new JButton(flashcard.getPrompt());
            JButton fcAnswer = new JButton(flashcard.getAnswer());
            panel.add(fcPrompt);
            panel.add(fcAnswer);
        }
        panel.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: displays menu for altering or creating decks
    private void displayDecksMenu() {
        panel.removeAll();
        panel.setLayout(new GridLayout(0, 1));

        panel.add(createDeckButton);
        panel.add(editDeckButton);
        panel.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: display menu for choosing deck used in flashcard mode
    private void displayChooseDeck() {
        panel.removeAll();
        textField.setText("");

        JPanel deckLabelPanel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(deckLabelPanel);
        JLabel chooseDeckLabel = new JLabel("Enter Deck Name");
        deckLabelPanel.add(chooseDeckLabel);

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.add(textField);
        textFieldPanel.add(enterButton, BorderLayout.EAST);
        panel.add(textFieldPanel, BorderLayout.NORTH);

        for (Deck deck : workRoom.getDeckList()) {
            JPanel deckDisplayPanel = new JPanel();
            JLabel deckDisplayLabel = new JLabel(deck.getTitle());
            panel.add(deckDisplayPanel);
            deckDisplayPanel.add(deckDisplayLabel);
        }
        panel.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void loadWorkRoom() {
        try {
            workRoom = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the workroom to file
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // EFFECTS: called when there is a mouse click on a component that is being listened to
    @Override
    public void mouseClicked(MouseEvent e) {
        handleMousePressed(e);
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }


    @Override
    public void mouseExited(MouseEvent e) {

    }


    public static void main(String[] args) {
        new Gui();
    }
}
