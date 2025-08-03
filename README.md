# railseAswith
 Backend Assignment Completion


Workforce Management API
A Spring Boot application for managing tasks and workforce assignments.
Project Structure


src/main/java/com/yourcompany/workforcemgmt/
├── WorkforcemgmtApplication.java
├── controller/
│   └── TaskController.java
├── service/
│   └── TaskService.java
├── model/
│   ├── Task.java
│   ├── Staff.java
│   ├── TaskActivity.java
│   ├── TaskComment.java
│   ├── TaskStatus.java
│   ├── TaskPriority.java
│   └── ActivityType.java
├── dto/
│   ├── TaskDto.java
│   ├── CreateTaskRequest.java
│   ├── AssignTaskRequest.java
│   ├── UpdateTaskPriorityRequest.java
│   ├── AddCommentRequest.java
│   ├── TaskActivityDto.java
│   └── TaskCommentDto.java
└── mapper/
    └── TaskMapper.java




Features
Bug Fixes Implemented

Task Re-assignment Creates Duplicates: Fixed the assign-by-ref endpoint to cancel the old task when reassigning to a new employee
Cancelled Tasks Clutter the View: Modified task-fetching endpoints to exclude cancelled tasks from results

New Features Implemented

Smart Daily Task View: Enhanced date-range filtering to include:

All active tasks that started within the range
All active tasks that started before the range but are still open


Task Priority Management:

Added priority field (HIGH, MEDIUM, LOW) to Task model
Endpoint to update task priority
Endpoint to fetch tasks by priority


Task Comments & Activity History:

Automatic activity logging for task events
User comments functionality
Complete history view when fetching task details



API Endpoints
Task Management

POST /api/tasks - Create a new task
GET /api/tasks - Get all tasks
GET /api/tasks/{taskId} - Get task by ID (includes full history)
POST /api/tasks/assign-by-ref - Reassign task to new employee
PUT /api/tasks/{taskId}/status - Update task status

Date-based Queries

GET /api/tasks/date-range?startDate={date}&endDate={date}&assignedTo={staffId} - Get tasks by date range (smart view)

Priority Management

PUT /api/tasks/{taskId}/priority - Update task priority
GET /api/tasks/priority/{priority} - Get tasks by priority

Comments

POST /api/tasks/{taskId}/comments - Add comment to task

Running the Application

Clone the repository
Navigate to the project directory
Run: ./gradlew bootRun
Application will start on http://localhost:8080

Sample API Calls
Create Task
bashPOST /api/tasks
{
  "title": "Follow up with client",
  "description": "Call client about proposal",
  "assignedTo": "staff1",
  "startDate": "2024-08-02T09:00:00",
  "dueDate": "2024-08-02T17:00:00",
  "createdBy": "manager1"
}
Reassign Task
bashPOST /api/tasks/assign-by-ref
{
  "taskId": "task-id-here",
  "assignedTo": "staff2",
  "assignedBy": "manager1"
}
Update Priority
bashPUT /api/tasks/{taskId}/priority
{
  "priority": "HIGH",
  "updatedBy": "manager1"
}
Add Comment
bashPOST /api/tasks/{taskId}/comments
{
  "comment": "Client responded positively",
  "commentedBy": "staff1"
}
Technology Stack

Java 17
Spring Boot 3.0.4
Gradle
Lombok
MapStruct
In-memory data storage (HashMap)

