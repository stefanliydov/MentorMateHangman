package com.mentormate.game;

import com.mentormate.io.ConsoleIO;
import com.mentormate.io.IO;
import com.mentormate.word_loader.FileWordLoader;
import com.mentormate.word_loader.WordLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Hangman implements Game {

    private final WordLoader wordReader;
    private final IO io;
    private int score;

    public Hangman() {
        this(new ConsoleIO());
    }

    public Hangman(IO io) {
        this.wordReader = new FileWordLoader();
        this.io = io;
    }

    @Override
    public void start() {
        showAvailableCategories();

        final String word = getRandomWord();

        final Map<Character, Integer> letterCount = new HashMap<>();
        int totalLetters = findLettersCounts(word, letterCount);

        playWord(word, letterCount, totalLetters);

        io.write("Current score: " + score + System.lineSeparator());
        this.start();
    }

    private String getRandomWord() {
        String word = wordReader.getRandomWordByCategory(io.read());
        if (Objects.isNull(word)) {
            io.write("Category not found!");
            this.start();
        }
        return word;
    }

    private void playWord(String word, Map<Character, Integer> letterCount, int totalLetters) {
        final String[] result = word.replaceAll("\\w", "_").split("");
        int attemptsLeft = 10;
        while (true) {
            io.write("Attempts left: " + attemptsLeft);
            io.write("Current word/phrase: " + joinWord(result));
            io.write("Please enter a letter:");
            final String input = io.read().trim();
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                io.write("Invalid Character!");
                continue;
            }

            final char letter = Character.toLowerCase(input.charAt(0));

            if (letterCount.containsKey(letter)) {
                replaceFoundLetter(word, result, letter);
                totalLetters -= letterCount.remove(letter);

                if (totalLetters == 0) {
                    io.write("Congratulations you have revealed the word/phrase:");
                    io.write(joinWord(result));
                    score++;
                    break;
                }
            } else {
                io.write("The word/phrase doesn't have this letter.");

                if (--attemptsLeft == 0) {
                    io.write("You have lost, please try again!");
                    break;
                }
            }
        }
    }

    private void showAvailableCategories() {
        io.write("Please choose a category:");
        for (String category : wordReader.getCategories()) {
            io.write(category);
        }
    }

    private String joinWord(String[] result) {
        return String.join(" ", result);
    }

    private void replaceFoundLetter(String word, String[] result, char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (Character.toLowerCase(word.charAt(i)) == letter) {
                result[i] = String.valueOf(word.charAt(i));
            }
        }
    }

    private int findLettersCounts(String word, Map<Character, Integer> letterCount) {
        int totalLetters = 0;
        for (int i = 0; i < word.length(); i++) {
            final char letter = Character.toLowerCase(word.charAt(i));
            if (letter != ' ') {
                totalLetters++;
                if (!letterCount.containsKey(letter)) {
                    letterCount.put(letter, 1);
                } else {
                    letterCount.put(letter, letterCount.get(letter) + 1);
                }
            }
        }
        return totalLetters;
    }


}
