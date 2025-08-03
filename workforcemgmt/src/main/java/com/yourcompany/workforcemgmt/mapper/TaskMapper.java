package com.yourcompany.workforcemgmt.mapper;

import com.yourcompany.workforcemgmt.dto.TaskActivityDto;
import com.yourcompany.workforcemgmt.dto.TaskCommentDto;
import com.yourcompany.workforcemgmt.dto.TaskDto;
import com.yourcompany.workforcemgmt.model.Task;
import com.yourcompany.workforcemgmt.model.TaskActivity;
import com.yourcompany.workforcemgmt.model.TaskComment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDto taskToTaskDto(Task task);
    List<TaskDto> tasksToTaskDtos(List<Task> tasks);

    TaskActivityDto activityToActivityDto(TaskActivity activity);
    List<TaskActivityDto> activitiesToActivityDtos(List<TaskActivity> activities);

    TaskCommentDto commentToCommentDto(TaskComment comment);
    List<TaskCommentDto> commentsToCommentDtos(List<TaskComment> comments);
}