package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Task>> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new TaskNotFoundException("Такой задачи нет")));
    }

    @PostMapping
    public Mono<Task> createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskRepository.findAll().onBackpressureLatest();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id) {
        return taskRepository.deleteById(id);
    }

    @GetMapping("/filtered")
    public Flux<Task> getTasksByStatus(@RequestParam TaskStatus status) {
        return taskRepository.findAll()
                .filter(task -> task.getStatus().equals(status))
                .map(task -> {
                    task.setTitle(task.getTitle().toUpperCase());
                    return task;
                });
    }

}
