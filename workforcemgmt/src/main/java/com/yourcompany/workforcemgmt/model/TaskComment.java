package com.yourcompany.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskComment {
    private String id;
    private String taskId;
    private String comment;
    private String commentedBy;
    private LocalDateTime timestamp;
}