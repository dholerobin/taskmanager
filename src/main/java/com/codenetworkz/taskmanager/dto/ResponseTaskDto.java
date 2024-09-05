package com.codenetworkz.taskmanager.dto;

import com.codenetworkz.taskmanager.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTaskDto extends Response{

    private Task  data;
}
