package com.matias.taskly.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long taskId) {
        super("Task with ID: " + taskId + " not found");
    }
}
