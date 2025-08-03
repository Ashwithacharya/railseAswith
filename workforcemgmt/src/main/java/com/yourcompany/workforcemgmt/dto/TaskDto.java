package com.yourcompany.workforcemgmt.dto;

import com.yourcompany.workforcemgmt.model.TaskPriority;
import com.yourcompany.workforcemgmt.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private String assignedTo;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private List<TaskActivityDto> activities;
    private List<TaskCommentDto> comments;
}
