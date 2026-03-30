package com.matias.taskly.service;

import com.matias.taskly.exceptions.TaskNotFoundException;
import com.matias.taskly.exceptions.UserNotFoundException;
import com.matias.taskly.model.Task;
import com.matias.taskly.model.User;
import com.matias.taskly.repository.TaskRepository;
import com.matias.taskly.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new task.
     *
     * Business rules:
     * - The associated user must exist.
     * - We should associate a task with the user
     *
     * @param task entity to persist
     * @return saved task
     * @throws TaskNotFoundException if user does not exist
     */
    public Task createTask(Task task, Long userId) {

        // Validate that the user exists before creating the task
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        task.setUser(user);
        return taskRepository.save(task);
    }

    /**
     * Updates an existing task.
     *
     * Assumption:
     * - The task was already validated (exists) before calling this method.
     * - The entity is already modified (e.g., via mapper).
     *
     * @param task entity to update
     * @return updated task
     */
    public Task updateTask(Task task){
        return taskRepository.save(task);
    }

    /**
     * Deletes a task by id.
     *
     * Note:
     * - This method validate task existence.
     *
     * @param id task identifier
     */
    public void deleteTask(Long id) {
        // Validate task existence before deleting
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }

        taskRepository.deleteById(id);
    }

    /**
     * Retrieves a task by id.
     *
     * Business rule:
     * - The task must exist.
     *
     * @param id task identifier
     * @return found task
     * @throws TaskNotFoundException if task does not exist
     */
    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }


    /**
     * Get a List of task by user id.
     *
     * Business rules:
     * - The associated user must exist.
     *
     * @param id user identifier
     * @return found list task from user id
     * @throws UserNotFoundException if user does not exist
     */
    public List<Task> findTasksByUserId(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return taskRepository.getTasksByUserId(id);
    }

}