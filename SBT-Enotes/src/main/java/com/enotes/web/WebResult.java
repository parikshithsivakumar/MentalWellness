package com.enotes.web;

import com.enotes.webservices.CharacterResult;

public class WebResult {
    private CharacterResult color;
    private char letter;

    // Default constructor
    public WebResult() {
    }

    // Parameterized constructor
    public WebResult(CharacterResult color, char letter) {
        this.color = color;
        this.letter = letter;
    }

    // Getter for color
    public CharacterResult getColor() {
        return color;
    }

    // Setter for color
    public void setColor(CharacterResult color) {
        this.color = color;
    }

    // Getter for letter
    public char getLetter() {
        return letter;
    }

    // Setter for letter
    public void setLetter(char letter) {
        this.letter = letter;
    }

    // Static method to create WebResult array
    public static WebResult[] create(CharacterResult[] colors, String text) {
        WebResult[] results = new WebResult[colors.length];
        for (int iter = 0; iter < colors.length; iter++) {
            results[iter] = new WebResult(colors[iter], text.charAt(iter));
        }
        return results;
    }
}
