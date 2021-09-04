package com.cinema.project.seance;

public class SeanceNotFoundException extends RuntimeException {
    public SeanceNotFoundException(String message) {
        super(message);
    }
}
