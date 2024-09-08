package com.codenetworkz.taskmanager.repositories;

import com.codenetworkz.taskmanager.entities.Status;
import com.codenetworkz.taskmanager.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {



    Optional<Task> findByIdAndIsDelete(UUID id, Status isDelete);

    @Query(value ="select t from  Task t where t.isDelete =:status" )
    List<Task> findAllAndIsDelete(Status status);

    List<Task> findByTitle(String title);

}
