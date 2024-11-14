package org.footballclub.footballclubmanagement.exceptions;

public class ClubNotFoundException extends RuntimeException {
    public ClubNotFoundException(String message) {
        super(message);
    }
}
