package com.matias.taskly.controller;

import com.matias.taskly.dto.task.TaskCreateRequestDTO;
import com.matias.taskly.dto.task.TaskResponseDTO;
import com.matias.taskly.mapper.TaskMapper;
import com.matias.taskly.model.Task;
import com.matias.taskly.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserTaskController {


    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public UserTaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    /**
     * Creates a new task for a user.
     *
     * Maps incoming DTO to entity, delegates creation to the service
     * and returns the persisted task as a response DTO.
     *
     * @param dto incoming request data
     * @return created task with HTTP 201 status
     */
    @PostMapping("/{userId}/tasks")
    public ResponseEntity<TaskResponseDTO> createTask(@PathVariable Long userId, @Valid @RequestBody TaskCreateRequestDTO dto) {

        Task createdTask = taskService.createTask(taskMapper.createDtoToEntity(dto), userId);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskMapper.toResponseDTO(createdTask));
    }


    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getListTasksByUser(@PathVariable Long userId) {

        List<Task> lstTasksByUser= taskService.findTasksByUserId(userId);


        return ResponseEntity.ok(taskMapper.toResponseDTOList(lstTasksByUser));
    }




}
