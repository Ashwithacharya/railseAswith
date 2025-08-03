package com.yourcompany.workforcemgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignTaskRequest {
    private String taskId;
    private String assignedTo;
    private String assignedBy;
}
