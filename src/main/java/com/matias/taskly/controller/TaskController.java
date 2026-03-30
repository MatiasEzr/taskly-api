package com.matias.taskly.controller;

import com.matias.taskly.dto.task.TaskCreateRequestDTO;
import com.matias.taskly.dto.task.TaskUpdateRequestDTO;
import com.matias.taskly.mapper.TaskMapper;
import com.matias.taskly.model.Task;
import com.matias.taskly.service.TaskService;
import com.matias.taskly.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.matias.taskly.dto.task.TaskResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {


    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }


    /**
     * Updates an existing task.
     *
     * Retrieves the task, applies partial updates using the mapper
     * and persists the changes.
     *
     * @param dto fields to update
     * @param taskId task identifier
     * @return updated task with HTTP 200 status
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(@Valid @RequestBody TaskUpdateRequestDTO dto, @PathVariable Long taskId) {

        // Retrieve existing task (ensures it exists)
        Task existingTask = taskService.findTaskById(taskId);

        // Apply partial update (avoids overwriting missing fields)
        taskMapper.updateEntityFromDto(dto, existingTask);

        // Persist updated entity
        Task updatedTask = taskService.updateTask(existingTask);

        return ResponseEntity.ok(taskMapper.toResponseDTO(updatedTask));
    }




    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable("taskId") Long taskId) {
        Task task = taskService.findTaskById(taskId);
        return ResponseEntity.ok(taskMapper.toResponseDTO(task));
    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {

        taskService.deleteTask(taskId);

        return ResponseEntity.noContent().build(); // HTTP 204
    }



}