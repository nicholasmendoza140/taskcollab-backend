package com.nmen.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PreAuthorize("@teamSecurity.isTeamMemberByProject(#projectId, principal.id)")
    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<Task> createTask(@PathVariable Integer projectId, CreateTaskRequest request) {
        return ResponseEntity.ok(taskService.createTask(request, projectId));
    }

    @PreAuthorize("@teamSecurity.isTeamMemberByProject(#projectId, principal.id)")
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<Task>> getTasks(@PathVariable Integer projectId) {
        return ResponseEntity.ok(taskService.getTasks(projectId));
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable Integer taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer taskId, UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request));
    }
}
