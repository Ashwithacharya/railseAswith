package com.yourcompany.workforcemgmt.mapper;

import com.yourcompany.workforcemgmt.dto.TaskActivityDto;
import com.yourcompany.workforcemgmt.dto.TaskCommentDto;
import com.yourcompany.workforcemgmt.dto.TaskDto;
import com.yourcompany.workforcemgmt.model.Task;
import com.yourcompany.workforcemgmt.model.TaskActivity;
import com.yourcompany.workforcemgmt.model.TaskComment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-02T22:32:06+0530",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto taskToTaskDto(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDto taskDto = new TaskDto();

        return taskDto;
    }

    @Override
    public List<TaskDto> tasksToTaskDtos(List<Task> tasks) {
        if ( tasks == null ) {
            return null;
        }

        List<TaskDto> list = new ArrayList<TaskDto>( tasks.size() );
        for ( Task task : tasks ) {
            list.add( taskToTaskDto( task ) );
        }

        return list;
    }

    @Override
    public TaskActivityDto activityToActivityDto(TaskActivity activity) {
        if ( activity == null ) {
            return null;
        }

        TaskActivityDto taskActivityDto = new TaskActivityDto();

        return taskActivityDto;
    }

    @Override
    public List<TaskActivityDto> activitiesToActivityDtos(List<TaskActivity> activities) {
        if ( activities == null ) {
            return null;
        }

        List<TaskActivityDto> list = new ArrayList<TaskActivityDto>( activities.size() );
        for ( TaskActivity taskActivity : activities ) {
            list.add( activityToActivityDto( taskActivity ) );
        }

        return list;
    }

    @Override
    public TaskCommentDto commentToCommentDto(TaskComment comment) {
        if ( comment == null ) {
            return null;
        }

        TaskCommentDto taskCommentDto = new TaskCommentDto();

        return taskCommentDto;
    }

    @Override
    public List<TaskCommentDto> commentsToCommentDtos(List<TaskComment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<TaskCommentDto> list = new ArrayList<TaskCommentDto>( comments.size() );
        for ( TaskComment taskComment : comments ) {
            list.add( commentToCommentDto( taskComment ) );
        }

        return list;
    }
}
