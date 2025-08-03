package com.yourcompany.workforcemgmt.controller;

import com.yourcompany.workforcemgmt.dto.*;
import com.yourcompany.workforcemgmt.model.TaskPriority;
import com.yourcompany.workforcemgmt.model.TaskStatus;
import com.yourcompany.workforcemgmt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskRequest request) {
        try {
            TaskDto task = taskService.createTask(request);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/assign-by-ref")
    public ResponseEntity<TaskDto> assignTaskByRef(@RequestBody AssignTaskRequest request) {
        try {
            TaskDto task = taskService.assignTaskByRef(request);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable String taskId) {
        try {
            TaskDto task = taskService.getTaskById(taskId);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TaskDto>> getTasksByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String assignedTo) {
        List<TaskDto> tasks = taskService.getTasksByDateRange(startDate, endDate, assignedTo);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}/priority")
    public ResponseEntity<TaskDto> updateTaskPriority(
            @PathVariable String taskId,
            @RequestBody UpdateTaskPriorityRequest request) {
        try {
            TaskDto task = taskService.updateTaskPriority(taskId, request);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskDto>> getTasksByPriority(@PathVariable TaskPriority priority) {
        List<TaskDto> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<TaskDto> addCommentToTask(
            @PathVariable String taskId,
            @RequestBody AddCommentRequest request) {
        try {
            TaskDto task = taskService.addCommentToTask(taskId, request);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(
            @PathVariable String taskId,
            @RequestParam TaskStatus status,
            @RequestParam String updatedBy) {
        try {
            TaskDto task = taskService.updateTaskStatus(taskId, status, updatedBy);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
