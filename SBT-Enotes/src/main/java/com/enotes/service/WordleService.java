package com.enotes.service;

import com.enotes.webservices.CharacterResult;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WordleService {

    private static final List<String> DICTIONARY = new ArrayList<>();
    private static final String WORD;

    static {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("words.txt").getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 5) {
                    DICTIONARY.add(line.trim().toUpperCase());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading words from file.", e);
        }

        if (DICTIONARY.isEmpty()) {
            throw new RuntimeException("No 5-letter words found in the file.");
        }

        // Choose a random word from the DICTIONARY
        Random random = new Random();
        WORD = DICTIONARY.get(random.nextInt(DICTIONARY.size()));
    }

    public String validate(String word) {
        if (word.length() != 5) {
            return "Bad Length!";
        }
        word = word.toUpperCase();
        if (!DICTIONARY.contains(word)) {
            return "Not a word!";
        }
        return null;
    }

    public CharacterResult[] calculateResults(String word) {
        word = word.toUpperCase();

        CharacterResult[] result = new CharacterResult[WORD.length()];
        for (int iter = 0; iter < word.length(); iter++) {
            char currentChar = word.charAt(iter);
            if (currentChar == WORD.charAt(iter)) {
                result[iter] = CharacterResult.GREEN;
            } else if (WORD.indexOf(currentChar) > -1) {
                result[iter] = CharacterResult.YELLOW;
            } else {
                result[iter] = CharacterResult.BLACK;
            }
        }
        return result;
    }
}
