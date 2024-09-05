package com.codenetworkz.taskmanager.exception;

public class TaskAlreadyAvailable extends RuntimeException {

    public TaskAlreadyAvailable(String message) {
        super(message);
    }
}
