package com.example.mortageplan.entity;

public class InvalidInputException extends Exception {
    /**
     * Invalid input data exception
     *
     * @param message Error text
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
