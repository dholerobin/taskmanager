package com.codenetworkz.taskmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status isCompleted;

    @Enumerated(EnumType.STRING)
    private Status isActive;

    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Status isDelete;
    @JsonIgnore
    private LocalDateTime deleteAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if(this.isActive == null) {
            this.isActive = Status.YES;
        }
        if(this.isCompleted == null) {
            this.isCompleted = Status.NO;
        }
        if(this.isDelete == null) {
            this.isDelete = Status.NO;
        }
    }
}
