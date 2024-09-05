package com.codenetworkz.taskmanager.controllers;

import com.codenetworkz.taskmanager.dto.ResponseTaskDto;
import com.codenetworkz.taskmanager.exception.TaskAlreadyAvailable;
import com.codenetworkz.taskmanager.exception.TaskNotAvailable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(TaskAlreadyAvailable.class)
        public ResponseEntity<?> handleTaskAlreadyAvailable(TaskAlreadyAvailable exception) {
            ResponseTaskDto responseTaskDto=new ResponseTaskDto();
            responseTaskDto.setData(null);
            responseTaskDto.setMessage(exception.getMessage());
            responseTaskDto.setStatus(HttpStatus.BAD_REQUEST.name());
            return new ResponseEntity<>(responseTaskDto, HttpStatus.BAD_REQUEST);

        }

    @ExceptionHandler(TaskNotAvailable.class)
    public ResponseEntity<?> handleTaskNotAvailable(TaskNotAvailable exception) {
        ResponseTaskDto responseTaskDto=new ResponseTaskDto();
        responseTaskDto.setData(null);
        responseTaskDto.setMessage(exception.getMessage());
        responseTaskDto.setStatus(HttpStatus.NOT_FOUND.name());
        return new ResponseEntity<>(responseTaskDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSQLException(Exception exception) {
        ResponseTaskDto responseTaskDto=new ResponseTaskDto();
        responseTaskDto.setData(null);
        responseTaskDto.setMessage(exception.getMessage());
        responseTaskDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        return new ResponseEntity<>(responseTaskDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
