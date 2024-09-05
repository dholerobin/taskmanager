package com.codenetworkz.taskmanager.dto;

import com.codenetworkz.taskmanager.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTaskListDto  extends Response{

    List<Task> data;
}
