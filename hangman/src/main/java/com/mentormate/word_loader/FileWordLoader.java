package com.mentormate.word_loader;

import static com.mentormate.random_number_generator.RandomNumberGenerator.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class FileWordLoader implements WordLoader {
    private static final String FILE_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "words.txt";
    private final Map<String, List<String>> categoriesWords;

    public FileWordLoader() {
        this.categoriesWords = new HashMap<>();
        this.readWords();
    }

    @Override
    public Set<String> getCategories() {
        return categoriesWords.keySet();
    }

    @Override
    public String getRandomWordByCategory(String category) {

        final List<String> words = categoriesWords.get(getCategories().stream()
                .filter(e -> e.equalsIgnoreCase(category)).findFirst().orElse(null));
        if (Objects.isNull(words)) {
            return null;
        }
        return words.get(generate(words.size()));
    }

    private void readWords() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH
        ))) {
            String sCurrentLine;
            String currentCategory = null;

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.charAt(0) == '_') {
                    categoriesWords.put(sCurrentLine.substring(1), new ArrayList<>());
                    currentCategory = sCurrentLine.substring(1);
                } else {
                    categoriesWords.get(currentCategory).add(sCurrentLine);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
