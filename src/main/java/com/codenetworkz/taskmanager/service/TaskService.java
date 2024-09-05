package com.codenetworkz.taskmanager.service;

import com.codenetworkz.taskmanager.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    Optional<Task> createTask(String title, String description);
    Optional<Task>  updateTask(UUID id, String title, String description);
    Optional<Task>  completedTask(UUID id);
    List<Task> getAllTask(String filter);
    Optional<Task>  getTaskbyId(UUID id);
    Optional<Task> filterTaskUsingTaskName(String name);
    Optional<Task> deleteTask(UUID id);
}
