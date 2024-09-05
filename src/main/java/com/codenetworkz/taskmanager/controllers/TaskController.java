package com.codenetworkz.taskmanager.controllers;

import com.codenetworkz.taskmanager.dto.RequestTaskDto;
import com.codenetworkz.taskmanager.dto.ResponseTaskDto;
import com.codenetworkz.taskmanager.dto.ResponseTaskListDto;
import com.codenetworkz.taskmanager.entities.Task;
import com.codenetworkz.taskmanager.exception.TaskNotAvailable;
import com.codenetworkz.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody RequestTaskDto requestTaskDto) {
        Optional<Task> task = taskService.createTask(requestTaskDto.getTitle(), requestTaskDto.getDesc());
        ResponseTaskDto responseTaskDto=new ResponseTaskDto();
        if (task.isPresent()) {
            responseTaskDto.setData(task.get());
            responseTaskDto.setMessage("Task created successfully");
            responseTaskDto.setStatus("Created");
            return new ResponseEntity<>(responseTaskDto, HttpStatus.OK);
        }
        throw new TaskNotAvailable("find task id is not available");
    }

    @GetMapping
    public ResponseEntity<?>  getAllTasks(@RequestParam(value = "filter", required = false) String filter) {
        List<Task> allTask = taskService.getAllTask(filter);

        ResponseTaskDto responseTaskDto=new ResponseTaskDto();
        if (allTask==null) {
            responseTaskDto.setData(new Task());
            responseTaskDto.setMessage("No Data Available");
            responseTaskDto.setStatus("Fetch");
            return new ResponseEntity<>(responseTaskDto, HttpStatus.NO_CONTENT);
        }else if (allTask.size()==1) {
            responseTaskDto.setData(allTask.get(0));
            responseTaskDto.setMessage("fetch  Task successfully");
            responseTaskDto.setStatus("Fetch");
            return new ResponseEntity<>(responseTaskDto, HttpStatus.OK);
        }else if (allTask.size()>1) {
            ResponseTaskListDto responseTaskListDto=new ResponseTaskListDto();
            responseTaskListDto.setData(allTask);
            responseTaskListDto.setMessage(" fetch All Task successfully");
            responseTaskListDto.setStatus("Fetch All");
            return new ResponseEntity<>(responseTaskListDto, HttpStatus.OK);
        }
        throw new TaskNotAvailable("find task is not available");

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?>  getTaskByID(@PathVariable UUID id) {
        Optional<Task> task = taskService.getTaskbyId(id);
        ResponseTaskDto responseTaskDto=new ResponseTaskDto();
        if (task.isPresent()) {
            responseTaskDto.setData(task.get());
            responseTaskDto.setMessage("Fetch task details successfully");
            responseTaskDto.setStatus("Found");
            return new ResponseEntity<>(responseTaskDto, HttpStatus.OK);
        }
         throw new TaskNotAvailable("find task id is not available");
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?>  updateTask(@PathVariable UUID id, @Valid @RequestBody RequestTaskDto requestTaskDto) {
        Optional<Task> task = taskService.updateTask(id, requestTaskDto.getTitle(), requestTaskDto.getDesc());
        ResponseTaskDto responseTaskDto=new ResponseTaskDto();
        if (task.isPresent()) {
            responseTaskDto.setData(task.get());
            responseTaskDto.setMessage("Task update successfully");
            responseTaskDto.setStatus("Updated");
            return new ResponseEntity<>(responseTaskDto, HttpStatus.OK);
        }
        throw new TaskNotAvailable("find task id is not available");
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id) {
        Optional<Task> task = taskService.deleteTask(id);
        ResponseTaskDto responseTaskDto=new ResponseTaskDto();
        if (task.isPresent()) {
            responseTaskDto.setData(task.get());
            responseTaskDto.setMessage("Task Deleted successfully");
            responseTaskDto.setStatus("Deleted");
            return new ResponseEntity<>(responseTaskDto, HttpStatus.OK);
        }
        throw new TaskNotAvailable("find task id is not available");
    }

    @PatchMapping(value = "/{id}/complete")
    public ResponseEntity<?> completeTask(@PathVariable UUID id) {
        Optional<Task> task = taskService.completedTask(id);
        ResponseTaskDto responseTaskDto=new ResponseTaskDto();
        if (task.isPresent()) {
            responseTaskDto.setData(task.get());
            responseTaskDto.setMessage("Task complete successfully");
            responseTaskDto.setStatus("Complete");
            return new ResponseEntity<>(responseTaskDto, HttpStatus.OK);
        }
        throw new TaskNotAvailable("find task id is not available");
    }
}