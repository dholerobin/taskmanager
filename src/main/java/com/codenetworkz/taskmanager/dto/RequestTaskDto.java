package com.codenetworkz.taskmanager.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestTaskDto {

    @Size(min = 5, max = 100, message = "task title should not be less than or greater then 25")
    private String title;

    @Size(min = 5, max = 100, message = "task description should not be less than or greater then 100")
    private String desc;

}
