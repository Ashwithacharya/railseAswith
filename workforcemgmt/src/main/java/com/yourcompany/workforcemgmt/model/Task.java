package com.yourcompany.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private String assignedTo; // Staff ID
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;

    @Builder.Default
    private List<TaskActivity> activities = new ArrayList<>();

    @Builder.Default
    private List<TaskComment> comments = new ArrayList<>();

    public void addActivity(TaskActivity activity) {
        this.activities.add(activity);
    }

    public void addComment(TaskComment comment) {
        this.comments.add(comment);
    }

    public static Task createNew(String title, String description, String assignedTo,
                                 LocalDateTime startDate, LocalDateTime dueDate, String createdBy) {
        LocalDateTime now = LocalDateTime.now();
        String taskId = UUID.randomUUID().toString();

        Task task = Task.builder()
                .id(taskId)
                .title(title)
                .description(description)
                .status(TaskStatus.ACTIVE)
                .priority(TaskPriority.MEDIUM)
                .assignedTo(assignedTo)
                .startDate(startDate)
                .dueDate(dueDate)
                .createdAt(now)
                .updatedAt(now)
                .createdBy(createdBy)
                .build();

        // Add creation activity
        task.addActivity(TaskActivity.builder()
                .id(UUID.randomUUID().toString())
                .taskId(taskId)
                .activityType(ActivityType.TASK_CREATED)
                .description("Task created by " + createdBy)
                .performedBy(createdBy)
                .timestamp(now)
                .build());

        return task;
    }
}
