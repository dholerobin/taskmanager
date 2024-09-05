package com.codenetworkz.taskmanager.exception;

public class TaskNotAvailable  extends RuntimeException{
    public TaskNotAvailable(String message) {
        super(message);
    }
}
