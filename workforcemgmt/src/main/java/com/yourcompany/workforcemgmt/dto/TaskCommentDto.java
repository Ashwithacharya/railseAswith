package com.yourcompany.workforcemgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCommentDto {
    private String id;
    private String comment;
    private String commentedBy;
    private LocalDateTime timestamp;
}