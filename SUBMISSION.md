Backend Engineer Challenge Submission
1. Link to your Public GitHub Repository
https://github.com/Ashwithacharya/railseAswith 
2. Link to your Video Demonstration
https://github.com/Ashwithacharya/railseAswith
3. Summary of Implementation
Part 0: Project Setup & Structuring 

Created a fully functional Spring Boot project using Gradle
Organized code into proper package structure:

controller/ - REST API endpoints
service/ - Business logic
model/ - Domain entities
dto/ - Data transfer objects
mapper/ - MapStruct mapping interfaces


Configured all required dependencies (Spring Web, Lombok, MapStruct)

Part 1: Bug Fixes 
Bug 1: Task Re-assignment Creates Duplicates
Problem: When reassigning tasks, old tasks weren't being cancelled, creating duplicates.
Solution: Modified assignTaskByRef() method to:

Mark the existing task as CANCELLED
Log the cancellation activity
Create a new task for the new assignee
Log the creation activity for the new task

Bug 2: Cancelled Tasks Clutter the View
Problem: Date-range queries included cancelled tasks.
Solution: Added filtering in getTasksByDateRange() and getTasksByPriority() to exclude tasks with CANCELLED status.
Part 2: New Features 
Feature 1: Smart Daily Task View
Implementation: Enhanced getTasksByDateRange() to return:

All active tasks that started within the specified date range
PLUS all active tasks that started before the range but are still open and not yet completed

Feature 2: Task Priority Management
Implementation:

Added TaskPriority enum (HIGH, MEDIUM, LOW)
Added priority field to Task model with MEDIUM as default
Created PUT /api/tasks/{taskId}/priority endpoint to update priority
Created GET /api/tasks/priority/{priority} endpoint to fetch tasks by priority
Automatic activity logging for priority changes

Feature 3: Task Comments & Activity History
Implementation:

Created TaskActivity model to track all task events automatically
Created TaskComment model for user comments
Activity logging for: task creation, assignment, reassignment, status changes, priority changes, and comments
Enhanced task details endpoint to return complete chronological history
Created POST /api/tasks/{taskId}/comments endpoint for adding comments

Technical Implementation Details

Language: Java 17
Framework: Spring Boot 3.0.4
Build Tool: Gradle
Data Storage: In-memory HashMap collections
Mapping: MapStruct for DTO conversions
Code Quality: Lombok for reducing boilerplate

API Endpoints Implemented
MethodEndpointPurposePOST/api/tasksCreate new taskGET/api/tasksGet all tasksGET/api/tasks/{id}Get task with full historyPOST/api/tasks/assign-by-refReassign task (fixed)GET/api/tasks/date-rangeSmart date-range viewPUT/api/tasks/{id}/priorityUpdate task priorityGET/api/tasks/priority/{priority}Get tasks by priorityPOST/api/tasks/{id}/commentsAdd commentPUT/api/tasks/{id}/statusUpdate task status
Key Design Decisions

Immutable Activity Log: All task activities are automatically logged and cannot be modified
Chronological Ordering: Activities and comments are sorted by timestamp when retrieving task details
Comprehensive Status Tracking: Every significant task change generates an activity record
Clean Separation: Clear separation between models, DTOs, services, and controllers
Error Handling: Proper HTTP status codes and exception handling throughout the API

The implementation successfully addresses all requirements while maintaining clean, maintainable code structure.

 
