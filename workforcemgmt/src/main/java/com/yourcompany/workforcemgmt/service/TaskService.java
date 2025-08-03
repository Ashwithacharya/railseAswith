package com.yourcompany.workforcemgmt.service;

import com.yourcompany.workforcemgmt.dto.*;
import com.yourcompany.workforcemgmt.mapper.TaskMapper;
import com.yourcompany.workforcemgmt.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final Map<String, Task> tasks = new HashMap<>();
    private final Map<String, Staff> staff = new HashMap<>();

    public TaskService() {
        // Initialize with some sample staff
        initializeSampleStaff();
    }

    private void initializeSampleStaff() {
        Staff staff1 = Staff.builder()
                .id("staff1")
                .name("John Doe")
                .email("john.doe@company.com")
                .department("Sales")
                .role("Sales Representative")
                .build();

        Staff staff2 = Staff.builder()
                .id("staff2")
                .name("Jane Smith")
                .email("jane.smith@company.com")
                .department("Operations")
                .role("Operations Manager")
                .build();

        staff.put(staff1.getId(), staff1);
        staff.put(staff2.getId(), staff2);
    }

    public TaskDto createTask(CreateTaskRequest request) {
        Task task = Task.createNew(
                request.getTitle(),
                request.getDescription(),
                request.getAssignedTo(),
                request.getStartDate(),
                request.getDueDate(),
                request.getCreatedBy()
        );

        tasks.put(task.getId(), task);
        return TaskMapper.INSTANCE.taskToTaskDto(task);
    }

    public TaskDto assignTaskByRef(AssignTaskRequest request) {
        Task existingTask = tasks.get(request.getTaskId());
        if (existingTask == null) {
            throw new IllegalArgumentException("Task not found: " + request.getTaskId());
        }

        // BUG FIX 1: Cancel the old task when reassigning
        String oldAssignee = existingTask.getAssignedTo();
        if (!oldAssignee.equals(request.getAssignedTo())) {
            // Mark the existing task as cancelled
            existingTask.setStatus(TaskStatus.CANCELLED);
            existingTask.setUpdatedAt(LocalDateTime.now());

            // Add activity for cancellation
            existingTask.addActivity(TaskActivity.builder()
                    .id(UUID.randomUUID().toString())
                    .taskId(existingTask.getId())
                    .activityType(ActivityType.TASK_STATUS_CHANGED)
                    .description("Task cancelled due to reassignment by " + request.getAssignedBy())
                    .performedBy(request.getAssignedBy())
                    .timestamp(LocalDateTime.now())
                    .oldValue(TaskStatus.ACTIVE.toString())
                    .newValue(TaskStatus.CANCELLED.toString())
                    .build());

            // Create a new task for the new assignee
            Task newTask = Task.builder()
                    .id(UUID.randomUUID().toString())
                    .title(existingTask.getTitle())
                    .description(existingTask.getDescription())
                    .status(TaskStatus.ACTIVE)
                    .priority(existingTask.getPriority())
                    .assignedTo(request.getAssignedTo())
                    .startDate(existingTask.getStartDate())
                    .dueDate(existingTask.getDueDate())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .createdBy(request.getAssignedBy())
                    .activities(new ArrayList<>())
                    .comments(new ArrayList<>())
                    .build();

            // Add creation activity
            newTask.addActivity(TaskActivity.builder()
                    .id(UUID.randomUUID().toString())
                    .taskId(newTask.getId())
                    .activityType(ActivityType.TASK_CREATED)
                    .description("Task created by reassignment from " + oldAssignee + " by " + request.getAssignedBy())
                    .performedBy(request.getAssignedBy())
                    .timestamp(LocalDateTime.now())
                    .build());

            tasks.put(newTask.getId(), newTask);
            return TaskMapper.INSTANCE.taskToTaskDto(newTask);
        }

        return TaskMapper.INSTANCE.taskToTaskDto(existingTask);
    }

    public List<TaskDto> getTasksByDateRange(LocalDate startDate, LocalDate endDate, String assignedTo) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return tasks.values().stream()
                .filter(task -> task.getAssignedTo().equals(assignedTo))
                .filter(task -> task.getStatus() != TaskStatus.CANCELLED) // BUG FIX 2: Exclude cancelled tasks
                .filter(task -> {
                    // FEATURE 1: Smart daily task view
                    // Include tasks that started within the range
                    boolean startedInRange = task.getStartDate().isAfter(startDateTime.minusSeconds(1))
                            && task.getStartDate().isBefore(endDateTime.plusSeconds(1));

                    // Include active tasks that started before the range but are still open
                    boolean activeAndStartedBefore = task.getStatus() == TaskStatus.ACTIVE
                            && task.getStartDate().isBefore(startDateTime);

                    return startedInRange || activeAndStartedBefore;
                })
                .map(TaskMapper.INSTANCE::taskToTaskDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getAllTasks() {
        return tasks.values().stream()
                .map(TaskMapper.INSTANCE::taskToTaskDto)
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(String taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }

        // Sort activities and comments chronologically
        task.getActivities().sort(Comparator.comparing(TaskActivity::getTimestamp));
        task.getComments().sort(Comparator.comparing(TaskComment::getTimestamp));

        return TaskMapper.INSTANCE.taskToTaskDto(task);
    }

    public TaskDto updateTaskPriority(String taskId, UpdateTaskPriorityRequest request) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }

        TaskPriority oldPriority = task.getPriority();
        task.setPriority(request.getPriority());
        task.setUpdatedAt(LocalDateTime.now());

        // Add activity for priority change
        task.addActivity(TaskActivity.builder()
                .id(UUID.randomUUID().toString())
                .taskId(taskId)
                .activityType(ActivityType.TASK_PRIORITY_CHANGED)
                .description("Priority changed from " + oldPriority + " to " + request.getPriority() + " by " + request.getUpdatedBy())
                .performedBy(request.getUpdatedBy())
                .timestamp(LocalDateTime.now())
                .oldValue(oldPriority.toString())
                .newValue(request.getPriority().toString())
                .build());

        return TaskMapper.INSTANCE.taskToTaskDto(task);
    }

    public List<TaskDto> getTasksByPriority(TaskPriority priority) {
        return tasks.values().stream()
                .filter(task -> task.getPriority() == priority)
                .filter(task -> task.getStatus() != TaskStatus.CANCELLED) // Exclude cancelled tasks
                .map(TaskMapper.INSTANCE::taskToTaskDto)
                .collect(Collectors.toList());
    }

    public TaskDto addCommentToTask(String taskId, AddCommentRequest request) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }

        TaskComment comment = TaskComment.builder()
                .id(UUID.randomUUID().toString())
                .taskId(taskId)
                .comment(request.getComment())
                .commentedBy(request.getCommentedBy())
                .timestamp(LocalDateTime.now())
                .build();

        task.addComment(comment);
        task.setUpdatedAt(LocalDateTime.now());

        // Add activity for comment
        task.addActivity(TaskActivity.builder()
                .id(UUID.randomUUID().toString())
                .taskId(taskId)
                .activityType(ActivityType.COMMENT_ADDED)
                .description("Comment added by " + request.getCommentedBy())
                .performedBy(request.getCommentedBy())
                .timestamp(LocalDateTime.now())
                .build());

        return TaskMapper.INSTANCE.taskToTaskDto(task);
    }

    public TaskDto updateTaskStatus(String taskId, TaskStatus status, String updatedBy) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }

        TaskStatus oldStatus = task.getStatus();
        task.setStatus(status);
        task.setUpdatedAt(LocalDateTime.now());

        // Add activity for status change
        task.addActivity(TaskActivity.builder()
                .id(UUID.randomUUID().toString())
                .taskId(taskId)
                .activityType(ActivityType.TASK_STATUS_CHANGED)
                .description("Status changed from " + oldStatus + " to " + status + " by " + updatedBy)
                .performedBy(updatedBy)
                .timestamp(LocalDateTime.now())
                .oldValue(oldStatus.toString())
                .newValue(status.toString())
                .build());

        return TaskMapper.INSTANCE.taskToTaskDto(task);
    }
}
