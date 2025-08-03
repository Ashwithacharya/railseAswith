package com.yourcompany.workforcemgmt.dto;

import com.yourcompany.workforcemgmt.model.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskPriorityRequest {
    private TaskPriority priority;
    private String updatedBy;
}

