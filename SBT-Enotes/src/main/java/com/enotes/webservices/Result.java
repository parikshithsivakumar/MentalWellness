package com.enotes.webservices;

public class Result {
    private CharacterResult[] results;
    private String errorMessage;

    // Default constructor
    public Result() {
    }

    // Parameterized constructor
    public Result(CharacterResult[] results, String errorMessage) {
        this.results = results;
        this.errorMessage = errorMessage;
    }

    // Getter for results
    public CharacterResult[] getResults() {
        return results;
    }

    // Setter for results
    public void setResults(CharacterResult[] results) {
        this.results = results;
    }

    // Getter for errorMessage
    public String getErrorMessage() {
        return errorMessage;
    }

    // Setter for errorMessage
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
