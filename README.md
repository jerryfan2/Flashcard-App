# Flashcard Application

## Overview
This is a *flashcard application* where students will be able
to easily create their own flashcards. You can also create 
separate flashcard decks, making it easy to properly
organize material from different subjects. There are many different
things you can do:

- **Create and Edit Your Own Flashcard Decks**
- **Create and Edit Your Own Flashcards**
- **Use Your Flashcards**

As a student, studying many things at once and 
keeping track of all material can be quite taxing,
at least in my experience, so I wanted to create 
an application that could facilitate the learning 
process.

## User Stories

Phase 1:
- As a user, I want to be able to make a new deck of flashcards
- As a user, I want to be able to delete decks of flashcards
- As a user, I want to be able to edit the name of a deck of flashcards
- As a user, I want to be able to make new flashcards and add them to a deck
- As a user, I want to be able to delete flashcards
- As a user, I want to be able to edit the prompt and answer of flashcards
- As a user, I want to be able to use the flashcards as normal flashcards

Phase 2:
- As a user, I want to be able to save my flashcards and flashcard decks that I have created
- As a user, I want to be able to load and use my flashcards and flashcard decks that I have created

Phase 4: Task 2:

Fri Apr 01 03:31:12 PDT 2022

New deck april fools created

Fri Apr 01 03:31:25 PDT 2022

Flashcard what day is it today, april 1st added to april fools

Fri Apr 01 03:31:28 PDT 2022

Flashcard vfd, sfg added to fev

Fri Apr 01 03:31:28 PDT 2022

Flashcard htthdbg, rbthrnj  added to fev

Fri Apr 01 03:31:28 PDT 2022

Flashcard bgf, yjhdg added to fev

Fri Apr 01 03:31:28 PDT 2022

New deck fev created

Fri Apr 01 03:31:28 PDT 2022

New deck bgfdv created

Fri Apr 01 03:31:28 PDT 2022

Flashcard vfd, sfg added to Test1

Fri Apr 01 03:31:28 PDT 2022

New deck Test1 created

Fri Apr 01 03:31:28 PDT 2022

Flashcard 321, 123 added to yes

Fri Apr 01 03:31:28 PDT 2022

New deck yes created

Fri Apr 01 03:31:28 PDT 2022

New deck asfgdn created

Fri Apr 01 03:31:40 PDT 2022

New deck what time is it created


Phase 4: Task 3

Based on the UML class design diagram, it seems there actually isn't that much
unnecessary coupling. For example, Decks uses Deck which uses Flashcard. This
is a very linear relationship. If I had more time, I definitely would like to
make my main three design classes more complicated and extensive. The Decks
class is virtually a class just for holding multiple Deck instances, and the
Deck class is a class just for holding multiple Flashcard instances. The methods
inside these classes are also extremely simple, such as adding or removing Flashcards
or Decks, and changing the name of them. The design was so simple that in Phase 1, 
I was having trouble thinking up how to implement a for loop or if statement
clause into my model package code. In the GUI and ConsoleUI code, I felt like my
code was very poorly organized and could have been separated into more methods.
A lot of code had repetition, such as in the GUI, when the application is 
displaying a new screen for each menu, a lot of that code is duplicated. This made
it a lot harder to read, maintain, and made some methods much larger than they
needed to be. This also didn't really adhere to the single point of control 
principle, as I sometimes found myself going between methods to change something
since they were identical parts. I completely forgot about adding a graphical
portion to my GUI, so if I had more time, I'd definitely do that as well.