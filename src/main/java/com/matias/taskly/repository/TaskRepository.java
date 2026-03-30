package com.matias.taskly.repository;

import com.matias.taskly.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> getTasksByUserId(Long id);
}
