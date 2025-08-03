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
public class TaskActivity {
    private String id;
    private String taskId;
    private ActivityType activityType;
    private String description;
    private String performedBy;
    private LocalDateTime timestamp;
    private String oldValue;
    private String newValue;
}
