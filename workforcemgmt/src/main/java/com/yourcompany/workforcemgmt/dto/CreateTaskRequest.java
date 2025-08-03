package com.yourcompany.workforcemgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequest {
    private String title;
    private String description;
    private String assignedTo;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private String createdBy;
}