package com.codenetworkz.taskmanager.service.impl;


import com.codenetworkz.taskmanager.entities.Status;
import com.codenetworkz.taskmanager.entities.Task;
import com.codenetworkz.taskmanager.exception.TaskAlreadyAvailable;
import com.codenetworkz.taskmanager.exception.TaskNotAvailable;
import com.codenetworkz.taskmanager.repositories.TaskRepository;
import com.codenetworkz.taskmanager.service.TaskService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {


    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> createTask(String title, String description) {
        Task saved;
        List<Task> oldTask = taskRepository.findByTitle(title);
        if(!oldTask.isEmpty()){
           throw new TaskAlreadyAvailable("Task is Already Available");
        }
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCreatedAt(LocalDateTime.now());
        task.setIsActive(Status.Yes);

        try {
             saved = taskRepository.save(task);
        }catch (Exception e) {
            throw new RuntimeException("There is a problem with database connection. please try again");
        }
        return Optional.of(saved);
    }

    @Override
    public Optional<Task>  updateTask(UUID id, String title, String description) {
        Task updated;
        Optional<Task> oldTask = getTaskbyId(id);
        oldTask.get().setTitle(title);
        oldTask.get().setDescription(description);
        oldTask.get().setUpdatedAt(LocalDateTime.now());
        try {
            updated = taskRepository.save(oldTask.get());
        }catch (Exception e) {
            throw new RuntimeException("There is a problem with database connection. please try again");
        }
        return Optional.of(updated);
    }


    @Override
    public Optional<Task>  completedTask(UUID id) {
        Task completed;
        Optional<Task> oldTask = getTaskbyId(id);
        oldTask.get().setIsCompleted(Status.Yes);
        oldTask.get().setUpdatedAt(LocalDateTime.now());
        try {
            completed = taskRepository.save(oldTask.get());
        }catch (Exception e) {
            throw new RuntimeException("There is a problem with database connection. please try again");
        }
        return Optional.of(completed);
    }


    @Override
    public List<Task> getAllTask(String filter) {
       List<Task> tasks = null;
        try {
            if(filter != null && !filter.isEmpty()){
                if(filter.equalsIgnoreCase("title")){
                    tasks = taskRepository.findAll(Sort.by(Sort.Order.asc("title")));
                }
                else if(filter.equalsIgnoreCase("date")){
                    tasks = taskRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
                }
            }else {
                tasks = taskRepository.findAll();
            }
        }catch (Exception e) {
            throw new RuntimeException("There is a problem with database connection. please try again");
        }
        if(tasks.isEmpty()){
            throw new TaskNotAvailable("While fetch Task data not Available.");
        }

        if (filter != null && !filter.isEmpty()) {
            return tasks.stream()
                    .filter(task -> task.getIsDelete().equals("N"))
                    .filter(task -> task.getTitle().contains(filter))
                    .collect(Collectors.toList());
        }

        return tasks;
    }


    @Override
    public Optional<Task>  getTaskbyId(UUID id) {
        Optional<Task> oldTask;
        try {
            oldTask = taskRepository.findByIdAndIsDelete(id, Status.No);
        }catch (Exception e) {
            throw new RuntimeException("There is a problem with database connection. please try again");
        }
        if(oldTask.isEmpty()){
            throw new TaskNotAvailable("While Update given task id not match.");
        }

        return oldTask;
    }


    @Override
    public Optional<Task> filterTaskUsingTaskName(String name) {
        List<Task> task;
          try {
               task = taskRepository.findByTitle(name);
          }catch (Exception e) {
              throw new RuntimeException("There is a problem with database connection. please try again");
          }

        return Optional.of(task.get(0));
    }


    @Override
    public Optional<Task> deleteTask(UUID id) {
        Optional<Task> deleted;
        Optional<Task> oldTask = getTaskbyId(id);
        oldTask.get().setIsActive(Status.No);
        oldTask.get().setIsCompleted(Status.Yes);
        oldTask.get().setIsDelete(Status.Yes);
        try {
            deleted = Optional.of(taskRepository.save(oldTask.get()));
        }catch (Exception e) {
            throw new RuntimeException("There is a problem with database connection. please try again");
        }
        return deleted;
    }
}
