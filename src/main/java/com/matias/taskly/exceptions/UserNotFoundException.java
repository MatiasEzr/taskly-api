package com.matias.taskly.exceptions;


public class UserNotFoundException extends RuntimeException {

    // Cuando no se encuentra por email — usado en login
    public UserNotFoundException(String email) {
        super("User with email " + email + " not found");
    }

    // Cuando no se encuentra por id — usado en createTask
    public UserNotFoundException(Long id) {
        super("User with id " + id + " not found");
    }
}