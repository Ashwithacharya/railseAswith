package com.yourcompany.workforcemgmt.dto;

import com.yourcompany.workforcemgmt.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskActivityDto {
    private String id;
    private ActivityType activityType;
    private String description;
    private String performedBy;
    private LocalDateTime timestamp;
    private String oldValue;
    private String newValue;
}